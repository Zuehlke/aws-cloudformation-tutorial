# How to properly setup a Spring Boot Application using CloudFormation

We are building a Spring Boot RESTful backend here using only AWS CloudFormation. The focus lays on scalability and security. We include a mysql database to which our application automatically connects to.

## Infrastructure

![simple_mockup](images/simple_overview.png)

### Description
For building the infrastructure we will use four different AWS services:
- *Elastic Load Balancing (ELB)* used to distribute the traffic to the web servers behind it.-	*Elastic Compute Cloud (EC2)*, two virtual Linux servers called Amazon Linux. The Tomcat web server and Spring Boot application will be installed on each virtual server.-	*Relational Database Service (RDS)* providing a MySQL database. The Spring Boot application relies on this database. -	*Security groups* used to control the network traffic like firewall. With security groups, we will configure the load balancer so that it only accepts request on port 443 from the internet, the virtual servers accept connections from outside on port 22 (SSH) and connections on port 80 only from the load balancer. MySQL only accepts connections on port 3306 from the virtual servers.

## Template
A template is a JSON or YAML formatted text file that describes the AWS infrastructure. We will use  the JSON format for building our infrastructure.

The template is divided in several sections.

```json
{
	"AWSTemplateFormatVersion": "2010-09-09",
	"Description": "Sample Infrastructure",
	"Parameters": {...},
	"Metadata" : {...},
	"Mappings": {...},
	"Resources": {...},
	"Outputs": {...}
}
```

### Description

#### Format Version
Specifies the AWS CloudFormation template version that the template conforms to.

```json
{
	"AWSTemplateFormatVersion": "2010-09-09",
	...
}
```

#### Description
A text string that describes the template.

```json
{
	...
	"Description": "Sample Infrastructure",
	...
}
```

#### Parameters
Specifies values that you can pass in to the template at runtime (when you create or update a stack).

Here we have to enter the name of an existing EC2 KeyPair to enable SSH access to the instances, the database's name, database's username and database's password. 

```json
{
	...
	"Parameters": {
		"KeyName": {
			"Description": "Name of an existing EC2 KeyPair to enable SSH access to the instances",
			"Type": "AWS::EC2::KeyPair::KeyName"
		},
       "DBName": {
        "Description": "Enter the name of the database",
        "Type": "String",
        "AllowedPattern": "[a-zA-Z]*"
      },
      "DBUser" : {
        "Description" : "The database admin username",
        "Type" : "String",
        "MinLength" : "1",
        "MaxLength" : "41",
        "AllowedPattern" : "[a-zA-Z0-9]*"
      },
      "DBPwd" : {
        "NoEcho" : "true",
        "Description" : "The database admin password",
        "Type" : "String",
        "MinLength" : "8",
        "MaxLength" : "41",
        "AllowedPattern" : "[a-zA-Z0-9]*"
      }
	},
	...
}
```