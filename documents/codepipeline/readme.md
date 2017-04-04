## Infrastructure with Jenkins for continuous- integration and delivery

![simple_mockup](../../documents/images/infrastructure_codepipeline.png)

## Description
For building the infrastructure we will use among others the following AWS services:
- *Elastic Load Balancing (ELB)* used to distribute the traffic to the web servers behind it.
- *Elastic Compute Cloud (EC2)*, two virtual Linux servers called Amazon Linux. The SpringBoot application with the embedded Tomcat Server will be installed on each virtual server whit help of AWS CodePipeline.
- *Relational Database Service (RDS)* providing a MySQL database. The Spring Boot application relies on this database.
- *CodePipeline* a pipe line used for continuous- integration and delivery. It retrieves the last commit from the Git repository, tests, builds the application and after that deploys the application to the EC2 instances.