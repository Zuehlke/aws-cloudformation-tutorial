## Infrastructure
![simple_mockup](images/simple_overview.png)


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

