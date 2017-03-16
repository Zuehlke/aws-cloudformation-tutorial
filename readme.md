# How to properly setup a Spring Boot Application using CloudFormation

We are building a Spring Boot RESTful backend here using only AWS CloudFormation. The focus lays on scalability and security. We include a mysql database to which our application automatically connects to.

## Infrastructure

![simple_mockup](images/simple_overview.png)

### Description
For building the infrastructure we will use four different AWS services:
- *Elastic Load Balancing (ELB)* used to distribute the traffic to the web servers behind it.-	*Elastic Compute Cloud (EC2)*, two virtual Linux servers called Amazon Linux. The Tomcat web server and Spring Boot application will be installed on each virtual server.-	*Relational Database Service (RDS)* providing a MySQL database. The Spring Boot application relies on this database. -	*Security groups* used to control the network traffic like firewall. With security groups, we will configure the load balancer so that it only accepts request on port 443 from the internet, the virtual servers accept connections from outside on port 22 (SSH) and connections on port 80 only from the load balancer. MySQL only accepts connections on port 3306 from the virtual servers.

## Template

