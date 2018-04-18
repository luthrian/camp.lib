# camp.lib 

## Goals
A Java framework geared towards developing "process driven" c2b/b2b applications. 

In its current form it consists of: 
- business/system entities that allow 
+ the representation and definition of products/services, (external/internal/business/consumer) customer orders and business processes which handle the various end2end business cases 
+ as well as basic life-cycle states of these entities 
- together with: 
+ core functionality for JSON transformation, 
+ persistence towards a MariaDB/MySQL database, 
+ communication towards a process engine via a JSON REST API (for example the fabulous Camunda Engine or other JBPM/Activiti derivatives). 

The framework roadmap envisions (modular) extensions for 
- authorization
- encryption
- deep-learning-based-(process)-automation-plugins
- replacing the current test APIServicePoint jetty server with a tomcat servlet
- more configuration possibilities
- visualization of business/system objects and processes
- product/system/process management UI's
- and more...


## Configuration

To use the framework in its current form you will need to edit the jars config.properties file to set the database user and password setting. The database user must be able to create/delete schema/database tables. You will also setup/modify the various server urls config settings to reflect your own setup. NOTE: certain PATH prefixes are currently not used.

## NOTICE

This is work in progress ...  
 
