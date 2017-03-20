# How to properly setup a Spring Boot Application using CloudFormation
## Intro
The goal of this project is to give you a rocket start into AWS CloudFormation. It should lead you through the process of setting up a backend for yourself in only minutes. Adapting the template to other stacks/technologies should be as easy as possible. The focus lays on scalability and security. 

We are building a Spring Boot RESTful backend here using only AWS CloudFormation. We include a MySql database, to which our application automatically connects to.

We are always happy to get your support. If you find anything that could be better, we highly appreciate your feedback. :)

## Infrastructure
![simple_mockup](images/simple_overview.png)

## How to start asap?
1. [Create an IAM User and setup the aws cli](http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-set-up.html)
1. Create a key called "keyToSuccess" in the [webinterface](https://console.aws.amazon.com/console/home) -> EC2 -> Key Pairs -> *Create Key Pair*
1. Open a terminal and insert: 
```bash
aws cloudformation create-stack --region us-east-1 --stack-name theStackIsBack --template-body file:///Users/PATH_TO_TEMPLATE/template.json --parameters ParameterKey=KeyName,ParameterValue=keyToSuccess ParameterKey=DBName,ParameterValue=TheDbName ParameterKey=DBPwd,ParameterValue=Th3P455w0rd ParameterKey=DBUser,ParameterValue=TheDbUser
```
1. Done

## Description
For building the infrastructure we will use four different AWS services:
- *Elastic Load Balancing (ELB)* used to distribute the traffic to the web servers behind it.- *Elastic Compute Cloud (EC2)*, two virtual Linux servers called Amazon Linux. The Tomcat web server and Spring Boot application will be installed on each virtual server.- *Relational Database Service (RDS)* providing a MySQL database. The Spring Boot application relies on this database. - *Security groups* used to control the network traffic like firewall. With security groups, we will configure the load balancer so that it only accepts request on port 443 from the internet, the virtual servers accept connections from outside on port 22 (SSH) and connections on port 8080 only from the load balancer. MySQL only accepts connections on port 3306 from the virtual servers.
- *VPC* to keep instances accessible only by each other or by the load balancer.

## CloudFormation Template
A template is a JSON or YAML formatted text file that describes the AWS infrastructure. We will use  the JSON format for building our infrastructure.
A template provides the following advantages:

- Create a complete stack
- Keep everything in a single template
- Replace components/resources as updates without losing data (auto-migration)
- Custom parameters (environment variables)
- Manage deployments
- Manageable over the cli or the webinterface


## Main sections

The template is divided into several sections.

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

#### Format Version
Specifies the AWS CloudFormation template version that the template conforms to.

#### Description
A custom description you can write for your project.

#### Parameters
Specifies values that you can pass in to the template at runtime (when you create or update a stack). Those can be handed over as json file or over the console.

#### Metadata
Contains objects that provide additional information about the template. 

Here we use the AWS::CloudFormation::Interface metadata key that defines how parameters are grouped and sorted in the AWS CloudFormation console. We create the group *Database Configuration* and specifies that the parameters  *DBName*, *DBUser*, *DBPwd* should be displayed in that order. Finally we define de group *EC2 Key Pair*.

```json
{
	...
	"Metadata" : {
      "AWS::CloudFormation::Interface" : {
        "ParameterGroups" : [
          {
            "Label" : { "default" : "Database Configuration" },
            "Parameters" : [ "DBName", "DBUser", "DBPwd"]
          }
          ,{
            "Label" : { "default" : "EC2 Key Pair" },
            "Parameters" : ["KeyName"]
          }
        ]
      }
    },
	...
}
```

#### Mappings
Matches a key to a corresponding set of named values.

Here we define a map EC2RegionMap, which contains different keys (region names). Each key contains a name-value pair representing the [AMI](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AMIs.html) ID for the AMI Name in the region represented by the key.

```json
{
	...
	"Mappings": {
		"EC2RegionMap": {
			"ap-northeast-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-cbf90ecb"},
			"ap-southeast-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-68d8e93a"},
			"ap-southeast-2": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-fd9cecc7"},
			"eu-central-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-a8221fb5"},
			"eu-west-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-a10897d6"},
			"sa-east-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-b52890a8"},
			"us-east-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-1ecae776"},
			"us-west-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-d114f295"},
			"us-west-2": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-e7527ed7"}
		}
	},
	...
}
```

#### Resources
The resources/instances you want to allocate for your cloud. Here you define services like load balancers, EC2 instances, databases, VPCs, Security groups and connect them to each other.

```json
{
	...
	TODO
	...
}
```

#### Outputs
[After creation, this returns something from your template such as the public name of an EC2 server.](http://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/outputs-section-structure.html)

Here we return the URL of the deployed Rest Webservice after the infrastructure was created.

```json
{
	...
	"Outputs": {
    	"URL": {
      		"Value": {"Fn::Join": ["", ["http://", {"Fn::GetAtt": ["LoadBalancer", "DNSName"]}, "/hi"]]},
      		"Description": "Rest Webservice URL"
    }
  }
}
```

## How to define custom parameters?

Here we define that the name of an existing EC2 KeyPair to enable SSH access to the instances, the database's name, database's username and database's password should be passed as parameter when we create the stack.

Those variables are either handed over by JSON (separate file) or just like this over the command line.

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

## Template description

```json
{
	"AWSTemplateFormatVersion": "2010-09-09",
	"Description": "Sample Infrastructure",
...
```

- **AWSTemplateFormatVersion:** Version of the template. This is the most recent one.
- **Description:** A custom description for your project. Doesn't matter what you type here.

```json
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
	
- **Keyname:** The name of the key. This is what we specified as "keyToSuccess". This key is used to encrypt login data and needed to launch instances. The private key is used to connect to your instance by SSH, the public key is used to launch instances.
- **DBName/DBUser/DBPwd:** Those are custom parameters that we except. We will use those and replace the received values in *./src/Hi/src/main/resources/application.properties*. This makes sure, that our application connects to the database we instantiate in our example.

```json
"Metadata" : {
	"AWS::CloudFormation::Interface" : {
		"ParameterGroups" : [
			{
				"Label" : { "default" : "Database Configuration" },
				"Parameters" : [ "DBName", "DBUser", "DBPwd"]
			}
			,{
				"Label" : { "default" : "EC2 Key Pair" },
				"Parameters" : ["KeyName"]
			}
		]
	}
},
...
```

**ParameterGroups:** Those tell AWS which parameter we expect. One cannot start the stack without providing those values.

```json
"Mappings": {
	"EC2RegionMap": {
		"ap-northeast-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-cbf90ecb"},
		"ap-southeast-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-68d8e93a"},
		"ap-southeast-2": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-fd9cecc7"},
		"eu-central-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-a8221fb5"},
		"eu-west-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-a10897d6"},
		"sa-east-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-b52890a8"},
		"us-east-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-1ecae776"},
		"us-west-1": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-d114f295"},
		"us-west-2": {"AmazonLinuxAMIHVMEBSBacked64bit": "ami-e7527ed7"}
	}
},
...
```
Those mappings map regions to the excpected AMIs. If we start our application for eu-central-1 (Frankfurt), we will the linked AMI for our EC2.

```json
"Resources": {
	"VPC": {
		"Type": "AWS::EC2::VPC",
		"Properties": {
			"CidrBlock": "172.31.0.0/16",
			"EnableDnsHostnames": "true"
		}
	},
	"InternetGateway": {
		"Type": "AWS::EC2::InternetGateway",
		"Properties": {}
	},
	"VPCGatewayAttachment": {
		"Type": "AWS::EC2::VPCGatewayAttachment",
		"Properties": {
			"VpcId": {"Ref": "VPC"},
			"InternetGatewayId": {"Ref": "InternetGateway"}
		}
	},
	...
```

In this block, we define all the resources we want to allocate.

- **VPC:** In the *Virtual Private Cloud* you can put your data like in any other network. The VPC is not accessible from the outside without explicitly allowing it through a security group. TODO (better description).
- **InternetGateway:** Lets you access the internet from your components. Routing tables have to be defined therefore.
