# CloudFormation Template

A template is a JSON or YAML formatted text file that describes the AWS infrastructure. We will use the JSON format for building our infrastructure.

A template provides the following advantages:

- Supports a wide range of AWS Resources, allowing you to build a highly available, reliable, and scalable AWS infrastructure for your application needs.
- Makes it easy to organize and deploy a collection of AWS resources.
- Customized via parameters. For example, you can pass the RDS database size, EC2 instance types, database, and web server port numbers to AWS CloudFormation when you create a stack.
- Can be used repeatedly to create identical copies of the same stack.
- Manageable over the cli or the webinterface.

## Overview

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
Specifies values that you can pass into the template at runtime (when you create or update a stack).

#### Metadata
Contains objects that provide additional information about the template. 

#### Mappings
Matches a key to a corresponding set of named values. These keys well be referenced on other parts of the template to get their stored values.

#### Resources
The resources you want to allocate for your cloud like load balancers, EC2 instances, databases, VPCs, Security groups, etc.

#### Outputs

Information you want to display after the creation of the stack. For instance the public DNS of a EC2 instance.


## Description
Step by Step description of what happens in the template and where we achieve what.

```json
{
	"AWSTemplateFormatVersion": "2010-09-09",
	"Description": "Sample Infrastructure",
...
```

- **AWSTemplateFormatVersion:** Version of the template. This is the most recent one.
- **Description:** A custom description for your project. Doesn't matter what you type here.<br/><br/>


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
	
- **Keyname:** The name of the key. This is what we specified as "keyToSuccess". This key is used to connect to your EC2 instances by SSH.
- **DBName/DBUser/DBPwd:** The name, admin and password of the MySQL Database that will be created (Siehe Abschnitt Resources). We will use these values in *./src/Hi/src/main/resources/application.properties*. This makes sure, that our application connects to the database when it is started.<br/><br/>

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

**ParameterGroups:** The AWS::CloudFormation::Interface metadata key defines how parameters are grouped and sorted in the AWS CloudFormation web interface. We create the group *Database Configuration* and specifies that the parameters  *DBName*, *DBUser*, *DBPwd* should be displayed in that order. Finally we define de group *EC2 Key Pair*.<br/><br/>


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

- **EC2RegionMap:** A map that contains different keys (region names). Each key contains a name-value pair representing the [AMI](http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/AMIs.html) ID for the AMI Name in the region represented by the key. If we create our stack in the region eu-central-1 (Frankfurt), the AMI ID of this region will be used to create our EC2 intances.<br/><br/>

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

- **VPC:** Creates a *Virtual Private Cloud* that covers the CIDR Block 172.31.0.0/16.
- **InternetGateway:** Lets the resources in the VPC access the internet. Routing tables have to be defined therefore.
- **VPCGatewayAttachment:** Attaches the InternetGateway to the VPC.<br/><br/> 

```json
"SubnetA": {
	"Type": "AWS::EC2::Subnet",
	"Properties": {
		"AvailabilityZone": {"Fn::Select": ["0", {"Fn::GetAZs": ""}]},
		"CidrBlock": "172.31.38.0/24",
		"VpcId": {"Ref": "VPC"}
	}
},
"SubnetB": {
	"Type": "AWS::EC2::Subnet",
	"Properties": {
		"AvailabilityZone": {"Fn::Select": ["1", {"Fn::GetAZs": ""}]},
		"CidrBlock": "172.31.37.0/24",
		"VpcId": {"Ref": "VPC"}
	}
},
...
```

- **Subnets:** Creates two subnets in the VPC.<br/><br/> 


```json
"RouteTable": {
	"Type": "AWS::EC2::RouteTable",
	"Properties": {
		"VpcId": {"Ref": "VPC"}
	}
},
"RouteTableAssociationA": {
	"Type": "AWS::EC2::SubnetRouteTableAssociation",
	"Properties": {
		"SubnetId": {"Ref": "SubnetA"},
		"RouteTableId": {"Ref": "RouteTable"}
	}
},
"RouteTableAssociationB": {
	"Type": "AWS::EC2::SubnetRouteTableAssociation",
	"Properties": {
		"SubnetId": {"Ref": "SubnetB"},
		"RouteTableId": {"Ref": "RouteTable"}
	}
},
"RoutePublicNATToInternet": {
	"Type": "AWS::EC2::Route",
	"Properties": {
		"RouteTableId": {"Ref": "RouteTable"},
		"DestinationCidrBlock": "0.0.0.0/0",
		"GatewayId": {"Ref": "InternetGateway"}
	},
	"DependsOn": "VPCGatewayAttachment"
},
```

- **RouteTable:** RouteTables determine, where network traffic is directed.
- **RouteTableAssociation:** Connects a RouteTable to a Subnet
- **RoutePublicNATToInternet:** Creates a new route in a route table within the VPC and routes everthing to the InternetGateway.<br/><br/> 


```json
"NetworkAcl": {
	"Type": "AWS::EC2::NetworkAcl",
	"Properties": {
		"VpcId": {"Ref": "VPC"}
	}
},
"SubnetNetworkAclAssociationA": {
	"Type": "AWS::EC2::SubnetNetworkAclAssociation",
	"Properties": {
		"SubnetId": {"Ref": "SubnetA"},
		"NetworkAclId": {"Ref": "NetworkAcl"}
	}
},
"SubnetNetworkAclAssociationB": {
	"Type": "AWS::EC2::SubnetNetworkAclAssociation",
	"Properties": {
		"SubnetId": {"Ref": "SubnetB"},
		"NetworkAclId": {"Ref": "NetworkAcl"}
	}
},
"NetworkAclEntryIngress": {
	"Type": "AWS::EC2::NetworkAclEntry",
	"Properties": {
		"NetworkAclId": {"Ref": "NetworkAcl"},
		"RuleNumber": "100",
		"Protocol": "-1",
		"RuleAction": "allow",
		"Egress": "false",
		"CidrBlock": "0.0.0.0/0"
	}
},
"NetworkAclEntryEgress": {
	"Type": "AWS::EC2::NetworkAclEntry",
	"Properties": {
		"NetworkAclId": {"Ref": "NetworkAcl"},
		"RuleNumber": "100",
		"Protocol": "-1",
		"RuleAction": "allow",
		"Egress": "true",
		"CidrBlock": "0.0.0.0/0"
	}
},
...
```

- **NetworkAcl:** Layer of security for the VPC that acts as a firewall for controlling traffic in and out of one or more subnets.
- **SubnetNetworkAclAssociation:** Associates the NetworkAcl to a subnet.
- **NetworkAclEntryIngress or Egress:** Each network ACL has a set of numbered ingress rules and a separate set of numbered egress rules.
- **RuleNumber:** The entries are processed in the ascending order of the rule number.
- **ProtocolNumner:** The IP protocol that the rule applies to, -1 for all protocols.
- **RuleAction:** Whether to allow or deny traffic that matches the rule.
- **Egress:** Whether this rule applies to egress traffic from the subnet (true) or ingress traffic to the subnet (false).
- **CidrBlock:** The IPv4 CIDR range to allow or deny. Here we allows all ranges.<br/><br/>

```json
"LoadBalancer": {
	"Type": "AWS::ElasticLoadBalancing::LoadBalancer",
	"Properties": {
		"Subnets": [{"Ref": "SubnetA"}, {"Ref": "SubnetB"}],
		"LoadBalancerName": "school-elb",
		"Listeners": [{
			"InstancePort": "8080",
			"InstanceProtocol": "HTTP",
			"LoadBalancerPort": "80",
			"Protocol": "HTTP"
		}],
		"HealthCheck": {
			"HealthyThreshold": "2",
			"Interval": "5",
			"Target": "TCP:8080",
			"Timeout": "3",
			"UnhealthyThreshold": "2"
		},
		"SecurityGroups": [{"Ref": "LoadBalancerSecurityGroup"}],
		"Scheme": "internet-facing"
	},
	"DependsOn": "VPCGatewayAttachment"
},
...
```

Here we instantiate a load balancer. It will be used to forward the traffic from the internet to the EC2 instances.

- **Subnets:** Attach the load balancer to the subnets A and B.
- **LoadBalancerName:** Give a name to the load balancer.
- **Listeners:** Define the port (InstancePort:8080) and protocol (InstanceProtocol:HTTP) your application in the EC2 instances uses. Define the port (LoadBalancerPort:80) and protocol (Protocol:HTTP) the LoadBalancer is listening 👂. 
- **HealthCheck:** A healthcheck makes sure, that your application is up and running. You can configure alarms in CloudWatch to get notified if an instance gets "degraded". Those checks are being done by doing a *ping*.
	- **HealthyThreshold**: Number of successful checks before instance gets back to *Healhty*.
	- **Interval**: Interval in seconds between each health check.
	- **Timeout**: Timeout until a check reports "failed" back.
	- **UnhealthyThreshold**: Numner of unsuccessful checks before instance gets *Unhealthy*.
- **SecurityGroups:** Here you can link the SecurityGroups defined for this loadbalancer.
- **Scheme:** If the load balancer is attached to a VPC, it can be specified, how it can be accessed. *internal* or *internet-facing*.<br/><br/>


```json
"LoadBalancerSecurityGroup": {
	"Type": "AWS::EC2::SecurityGroup",
	"Properties": {
		"GroupDescription": "school-elb-sg",
		"VpcId": {"Ref": "VPC"},
		"SecurityGroupIngress": [{
			"CidrIp": "0.0.0.0/0",
			"FromPort": "80",
			"IpProtocol": "tcp",
			"ToPort": "80"
		}]
	}
},
...
```

- **LoadBalancerSecurityGroup:** security group for the load balancer used to control the network traffic like firewall. Allows request from every IP Address on port 80.<br/><br/>

```json
"WebServerSecurityGroup": {
	"Type": "AWS::EC2::SecurityGroup",
	"Properties": {
		"GroupDescription": "school-sg",
		"VpcId": {"Ref": "VPC"},
		"SecurityGroupIngress": [{
			"CidrIp": "0.0.0.0/0",
			"FromPort": "22",
			"IpProtocol": "tcp",
			"ToPort": "22"
		}, {
			"FromPort": "8080",
			"ToPort": "8080",
			"IpProtocol": "tcp",
			"SourceSecurityGroupId": {"Ref": "LoadBalancerSecurityGroup"}
		}]
	}
},
...
```

- **WebServerSecurityGroup:** security group for the EC2 instances. Allow connections on port 22 (SSH) from every IP address and on port 8080 (TCP) only from the load balancer.<br/><br/>


```json
"DatabaseSecurityGroup": {
	"Type": "AWS::EC2::SecurityGroup",
	"Properties": {
		"GroupDescription": "awsinaction-db-sg",
		"VpcId": {"Ref": "VPC"},
		"SecurityGroupIngress": [{
			"IpProtocol": "tcp",
			"FromPort": "3306",
			"ToPort": "3306",
			"SourceSecurityGroupId": {"Ref": "WebServerSecurityGroup"}
		}]
	}
},
...
```

- **DatabaseSecurityGroup:** security group for the database. Allow connections on port 3006 (TCP) only from the EC2 instances.<br/><br/>


```json
"Database": {
	"Type": "AWS::RDS::DBInstance",
	"Properties": {
		"AllocatedStorage": "5",
		"BackupRetentionPeriod": "0",
		"DBInstanceClass": "db.t2.micro",
		"DBInstanceIdentifier": "school-db",
		"DBName": {"Ref": "DBName"},
		"Engine": "MySQL",
		"MasterUsername": {"Ref": "DBUser"},
		"MasterUserPassword": {"Ref": "DBPwd"},
		"VPCSecurityGroups": [{"Fn::GetAtt": ["DatabaseSecurityGroup", "GroupId"]}],
		"DBSubnetGroupName": {"Ref": "DBSubnetGroup"}
	},
	"DependsOn": "VPCGatewayAttachment"
},
...
```

This block creates the RDS database.

- **AllocatedStorage:** DB storage in GiB.
- **BackupRetentionPeriod:** Delete DB snapshots after X days.
- **DBInstanceClass:** The name of the compute and memory capacity classes of the DB instance.
- **DBInstanceIdentifier:** An identifier for the DB instance.
- **DBName:** The name of the database being created. We hand this one over as parameter.
- **Engine:** The type of database you want, we use mysql in our example.
- **MasterUsername:** The username for our DB instance. We hand this one over as parameter.
- **MasterUserPassword:** The password for our DB instance. We hand this one over as parameter.
- **VPCSecurityGroups:** The ID of the SecurityGroup our instance is attached to.
- **DBSubnetGroupName:** The Subnet group to associate with the DB.<br/><br/>

```json
"DBSubnetGroup" : {
	"Type" : "AWS::RDS::DBSubnetGroup",
	"Properties" : {
		"DBSubnetGroupDescription" : "DB subnet group",
		"SubnetIds": [{"Ref": "SubnetA"}, {"Ref": "SubnetB"}]
	}
},
...
```

- **DBSubnetGroup:** The subnet group of the database. It contains the subnet A and B, specified by *SubnetIds*.<br/><br/>

```json
"LaunchConfiguration": {
	"Type": "AWS::AutoScaling::LaunchConfiguration",
	"Metadata": {
		"AWS::CloudFormation::Init": {
			"config": {
				"packages": {
					"yum": {
						"java-1.8.0-openjdk-devel": []
					}
				},
				"files": {
						"/tmp/java_setup": {
							"content": {
								"Fn::Join": [
									"\n",
									[
										"#!/bin/bash -ex",
										"yum install -y java-1.8.0\n",
										"yum remove -y java-1.7.0-openjdk\n"
									]
								]
							},
							"mode": "000500",
							"owner": "root",
							"group": "root"
						}
				 },
				 "commands": {
					"01_config": {
					"command": "/tmp/java_setup",
					"cwd": "/opt"
						}
					}
				}
			}
		},
				...
```

The *LaunchConfiguration* block defines, what should be executed in the EC2 instances when they are created. 

- **Metadata:** This attribute contains an *AWS::CloudFormation::Init* section that contains multiple attributes. The general idea is, that you can define scripts, that run after your EC2 instances booted up.
	- **packages:** This key is used to download and install pre-packaged applications and components in the EC2 instances. Here we install the java-1.8.0-openjdk.
	- **files:** In this section, we can define some files that get created into our instance at a specific location. The key of each block within the file is also it's absolute path. The key */tmp/java_setup* points to a file called *java_setup* within the folder */tmp*.
		- **Content:** The content of a file. You might have stepped over *Fn::Join* already. All this function does is concatenate multiple strings together. You can add a "Delimiter" between the strings.
		- **mode:** First three digits are to create a symlink and the last 3 digits represent the permissions of a file.
		- **owner:** The owner of the file
		- **group:** The group for the file
	- **commands:** are used to execute the above defined file (script). If you have defined more than one file you can explicitly specify the order those are run. You can just name them alphabetically in the order they should run.
		- **command:** The files to execute.
		- **cwd:** The place where those scripts are run.<br/><br/>


```json
"Properties": {
				"EbsOptimized": false,
				"ImageId": {"Fn::FindInMap": ["EC2RegionMap", {"Ref": "AWS::Region"}, "AmazonLinuxAMIHVMEBSBacked64bit"]},
				"InstanceType": "t2.micro",
				"SecurityGroups": [{"Ref": "WebServerSecurityGroup"}],
				"KeyName": {"Ref": "KeyName"},
				"IamInstanceProfile": {"Ref": "InstanceRoleInstanceProfile"},
				"AssociatePublicIpAddress": true,
				"UserData": {"Fn::Base64": {"Fn::Join": ["", [
					"#!/bin/bash -ex\n",
					"yum update -y aws-cfn-bootstrap\n",
					"/opt/aws/bin/cfn-init -v --stack ", {"Ref": "AWS::StackName"}, " --resource LaunchConfiguration --region ", {"Ref": "AWS::Region"}, "\n",
					"yum update -y\n",
                    "yum install -y ruby\n",
                    "yum install -y wget\n",
                    "cd /home/ec2-user\n",
                    "wget https://aws-codedeploy-",{"Ref": "AWS::Region"},".s3.amazonaws.com/latest/install\n",
                    "chmod +x ./install\n",
                    "./install auto\n",
                    "/opt/aws/bin/cfn-signal -e $? --stack ", {"Ref": "AWS::StackName"}, " --resource AutoScalingGroup --region ", {"Ref": "AWS::Region"}, "\n"
                ]]}}
			}
		},
...
```

- **ImageId:** Gets the id of the AMI for the selected region, that is located in the EC2RegionMap.
- **InstanceType:** The instance type of the EC2 instances that will be created.
- **KeyName:** The KeyName you specified in the web interface before: EC2 -> Key Pairs.
- **IamInstanceProfile:** Name of the instance profile containing the IAM Role, who is allow to access S3 Buckets.
- **AssociatePublicIpAddress:** Specifies wether an EC2 instance should get a public IP address or not.
- **UserData:** Script that will be executed after a successful launch of an EC2 instance. He we install the code-deploy-agent, that will be used later by the CodeDeploy Service for automatic deployment.<br/><br/>

```json
"AutoScalingGroup": {
	"Type": "AWS::AutoScaling::AutoScalingGroup",
	"Properties": {
		"LoadBalancerNames": [{"Ref": "LoadBalancer"}],
		"LaunchConfigurationName": {"Ref": "LaunchConfiguration"},
		"MinSize": "1",
		"MaxSize": "1",
		"DesiredCapacity": "1",
		"VPCZoneIdentifier": [{"Ref": "SubnetA"}, {"Ref": "SubnetB"}]
	},
	"CreationPolicy": {
		"ResourceSignal": {
			"Timeout": "PT10M"
		}
	},
	"DependsOn": "VPCGatewayAttachment"
}
...
```

The *AutoScalingGroup* block creates an auto scaling group.

- **LoadBalancerNames:** the load balancers associated with this auto scaling group.
- **LaunchConfigurationName:** The launch configuration associated with this auto scaling group.
- **MinSize:** The minimum number of EC2 instances we want to keep alive in our auto scaling group.
- **MaxSize:** The maximum number of EC2 instances we want to keep alive in our auto scaling group.
- **DesiredCapacity:** The desire number of EC2 instances we want to keep alive in our auto scaling group.<br/><br/>

```json
"Outputs": {
	"URL": {"Value": {"Fn::Join": ["", ["http://", {"Fn::GetAtt": ["LoadBalancer", "DNSName"]}, "/hi"]]},
	"Description": "Rest Webservice URL"
	}
}
```

- **Outputs:** After successful stack creation, everything you define in the output-block will be printed out. In our example, we print the *URL* to our Restful Webservice. We also print a *Description* which describes our URL.

