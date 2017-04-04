## Intro
**TODO rewrite**

The goal of this project is to give you a rocket start into AWS CloudFormation. It should lead you through the process of setting up a backend for yourself in only minutes. Adapting the template to other stacks/technologies should be as easy as possible. The focus lays on scalability and security. 

We are building a Spring Boot RESTful backend here using only AWS CloudFormation. We include a MySql database, to which our application automatically connects to.

We are always happy to get your support. If you find anything that could be better, we highly appreciate your feedback. :)

## Infrastructure with AWS CodePipeline or Jenkins for continuous- integration and delivery.
![simple_mockup](documents/images/infrastructure.png)


## Description
For building the infrastructure we will use among others the following AWS services:
- *Elastic Load Balancing (ELB)* used to distribute the traffic to the web servers behind it.- *Elastic Compute Cloud (EC2)*, two virtual Linux servers called Amazon Linux. The SpringBoot application with the embedded Tomcat Server will be installed on each virtual server whit help of AWS CodePipeline or Jenkins.- *Relational Database Service (RDS)* providing a MySQL database. The Spring Boot application relies on this database.
- *CodePipeline* **or** *Jenkins* used for continuous- integration and delivery.


### How to start asap?
1. [Create an IAM User and setup the aws cli](http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-set-up.html)
1. Create a key called "keyToSuccess" in the [webinterface](https://console.aws.amazon.com/console/home) -> EC2 -> Key Pairs -> *Create Key Pair*
1. [Download the template](cf_template/template.json)
1. Open a terminal and insert:
```bash
aws cloudformation create-stack --region us-east-1 --stack-name theStackIsBack --template-body file:///Users/PATH_TO_TEMPLATE/template.json --parameters ParameterKey=KeyName,ParameterValue=keyToSuccess ParameterKey=DBName,ParameterValue=TheDbName ParameterKey=DBPwd,ParameterValue=Th3P455w0rd ParameterKey=DBUser,ParameterValue=TheDbUser
```

## Table of Contents
1. [Step-By-Step description of the template](documents/template-desc.md)
1. [The raw template](templates/template.json)
1. [Infrastructure with AWS CodePipeline for continuous- integration and delivery.](#v1)
1. [Infrastructure with Jenkins for continuous- integration and delivery.](documents/jenkins/readme.md)
<br/><br/>