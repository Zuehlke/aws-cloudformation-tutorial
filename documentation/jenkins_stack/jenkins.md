## Infrastructure with Jenkins for continuous- integration and delivery

![simple_mockup](../images/infrastructure_jenkins_codedeploy.png)

## Description
For building the infrastructure we will use among others the following AWS services:

- *Elastic Load Balancing (ELB)* used to distribute the traffic to the web servers behind it.
- *Elastic Compute Cloud (EC2)*, two virtual Linux servers called Amazon Linux. The SpringBoot application with the embedded Tomcat Server will be installed on each virtual server whit help of Jenkins and AWS CodeDeploy.
- *Relational Database Service (RDS)* providing a MySQL database. The Spring Boot application relies on this database.
- *Amazon S3 Bucket (S3)* used to save the built application.
- *CodeDeploy* used to deploy the application in the EC2 instances.