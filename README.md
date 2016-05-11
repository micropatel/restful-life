# restful-life

Restful life is an API that exposes the functionality to maintain contacts (similar to those on your phone). The following libraries are being used:

1. Hibernate
2. Spring IoC
3. Spring Web
4. Spring MVC
5. Spring REST
6. Spring hibernate integration.
3. Spring Security/OAuth
3. Log4j
4. Apache commons

Restful life uses a maven build system. A mysql instance is required to host the application data. The database is created during application bootup as outlined in the hibernate configuration (hibernate.hbm2ddl.auto=update) located in src/main/resources/global.properties. The account used to connect to the database is located in the same global.properties file.

To run the application you must have maven installed and configurated as outlined here https://maven.apache.org/install.html

To build the application run:
$mvn clean package

To import the project in eclipse run:
$mvn eclipse:eclipse -Dwtpversion=2.0

The source code is shipped with a Vagrant file that is fully configured with tomcat and mysql instances to run the application, follow below instructions to run the instance:

1. Install and configure maven if not already as outlined above
2. Install Vagrant depedencies (VirtualBox and Vagrant)
3. Run the following commands:
$mvn clean install    #generate the WAR file
$vagrant up           #to run the instance, this might take few minutes

