package com.sti.rest.auth;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sti.rest.heartbeat.Message;
import com.sti.rest.heartbeat.MessageCode;
import com.sti.services.CoreServices;
import com.sti.services.auth.TokenInfo;

public class SecurityProtectionHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
	
	@Autowired(required=true)
	private AuthContextManager authContextManager;
	
	@Autowired(required=true)
	private CoreServices coreServices;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//protect from double dipping
		if(!this.authContextManager.hasAuthContext()) {//first time invoked
			final String paramAccessToken = request.getHeader("authorization");
			boolean authorized = false;
			
			if(StringUtils.isNotBlank(paramAccessToken)) { // validate the token
				final TokenInfo tokenInfo = this.coreServices.findAccessToken(paramAccessToken);
				if(tokenInfo != null && tokenInfo.isActive()) {
					final AuthContext authContext = new AuthContext(tokenInfo.getPrincipal());
					this.authContextManager.setAuthContext(authContext);
					authorized = true;
				}
			}
			
			if(!authorized) { //ok you shouldn't be here
				//forbidden
				response.setStatus(403);
				final ObjectMapper mapper = new ObjectMapper();
				final Message message = new Message();
				message.setCode(MessageCode.Forbidden);
				message.setDevMessage("request is not authenticated!");
				final String result = mapper.writeValueAsString(message);
				try (final PrintWriter out = response.getWriter()) {
					out.println(result);
				}
				return false;
			}
		}
				
		return super.preHandle(request, response, handler);
	}
}
