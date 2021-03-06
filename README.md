### restful-life

## About the application
Restful life is an API that exposes the functionality to maintain contacts (similar to those on your phone). The following libraries are being used:

1. Hibernate
2. Spring IoC
3. Spring Web
4. Spring MVC
5. Spring REST
6. Spring hibernate integration.
7. Log4j
8. Apache commons
9. JodaTime

## Building the source code
Restful life uses a maven build system. A mysql instance is required to host the application data. The database is created during application bootup as outlined in the hibernate configuration **hibernate.hbm2ddl.auto=update** located in **src/main/resources/global.properties**. The account used to connect to the database is located in the same **global.properties** file.

To run the application you must have maven installed and configurated as outlined here https://maven.apache.org/install.html

To build the application run:
```
mvn clean package
```

To import the project in eclipse run:
```
mvn eclipse:eclipse -Dwtpversion=2.0
```
## The vagrant instance
The source code is shipped with a Vagrant file that is fully configured with tomcat and mysql instances to run the application, follow below instructions to run the instance:

1. Install and configure maven if not already as outlined above
2. Install Vagrant depedencies (VirtualBox and Vagrant)
3. Run the following commands:
```
mvn clean install    #generate the WAR file
vagrant up           #to run the instance, this might take few minutes
```

Testing the api requires the following tasks
1. create a test account through the provided test api
```
curl -v -X POST -H "Content-Type: application/json" -d '{"username": "me", "password": "mypassword"}' 'http://localhost:8080/sti_services/api/test/accounts'
```

Sample response is 
```
{
	"id":3,
	"created":1462939945897,
	"credentials":null,
	"contacts":null
}
```
2. perform api authentication using client_id and client_secret (default values are specified in **src/main/webapp/WEB-INF/securityContext.xml** as **testClient** and **testSecret** respectively as well as account authentication using the username and password using in step 1). A sample commands is below
```
curl -v -X GET   -H "Content-Type: application/json"   'http://localhost:8080/sti_services/api/auth/token?client_id=testClient&client_secret=testSecret&username=me&password=mypassword'
```

Sample response is 
```
{
	"token":"c1f816c6-720a-4c5f-95e2-19edd7f2269b",
	"expirationDate":"2016-07-10T04:10:44.391Z"
}
```

3. Now we are ready to use the api, every request to the api must have an access token which was generated from the /auth/token step. a sample request is as follows

```
curl -v -X GET   -H "Content-Type: application/json"  -H "authorization: c1f816c6-720a-4c5f-95e2-19edd7f2269b" 'http://localhost:8080/sti_services/api/v1/accounts/3/contacts'
```

##API Specification

######View all contacts

URL: **/api/v1/accounts/{accountId}/contacts**

Method: **GET**

######view a specific contact 

URL: **/api/v1/accounts/{accountId}/contacts/{contactId}**

Method: **GET**

######Create a new contact    
URL: **/api/v1/accounts/{accountId}/contacts**				

Method: **POST**

Body:
```Json
{
	"firstName"			: "Ashraf",
	"lastName"			: "Ahmed",
	"email" 			: "email@example.com",
	"address"			: "some address",
	"cellphoneNumber" 	: "some phone",
	"homeNumber" 		: "some other phone"
}
```

######Update a specific contact 

URL: **/api/v1/accounts/{accountId}/contacts/{contactId}**

Method: **PUT**

Body:
```Json
{
	"email" 			: "new_value"
}
```

######Search for a contact 	

URL: **/api/v1/accounts/{accountId}/contacts?email=&firstName=&address=&cellphoneNumber=&lastName=&homeNumber=**

Method: **GET**

Comments:
The api allows for searching contacts based on any attribute of the contact object, the search only works for string matching similar to contains, for instance **/api/v1/accounts/1/contacts/1?email=@test.com** will retrieve a list of contacts that have an email that contains the text __"@test.com"__

### Runtime Exception Handling
> Because the way spring mvc works, I've had to provide a custom error resolution that is fully compatible with JSON and matches the HTTP REST best practices. The error resolution code is a property of Stormpath, Inc. which is available for reuse under an open source license. This code is only for educational purposes and shouldn't be used in a production type environment. 
