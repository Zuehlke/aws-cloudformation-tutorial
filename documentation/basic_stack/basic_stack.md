## The basic infrastructure in only five minutes
As a first step, we will set up the basic infrastructure. 


## Infrastructure 
![simple_mockup](../../documentation/images/infrastructure_basic.png)

## Description
The basic infrastructure contains the following AWS resources:
- *Elastic Load Balancing (ELB)* used to distribute the traffic to the web servers behind it.
- *Elastic Compute Cloud (EC2)*, two virtual Linux servers called Amazon Linux.
- *Relational Database Service (RDS)* providing a MySQL database.

## Quick Start
1. [Create an IAM User and setup the aws cli](http://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-set-up.html)
1. Create a key called "keyToSuccess" in the [webinterface](https://console.aws.amazon.com/console/home) -> EC2 -> Key Pairs -> *Create Key Pair*
1. [Download the template](../../templates/stack_basic/template_basic_stack.json)
1. Open a terminal and insert the following command: 
```
aws cloudformation create-stack --region us-east-1 --stack-name theStackIsBack --tags Key=theStackKey,Value=theStackValue --template-body file:///pathToTemplate/template_basic_stack.json --parameters ParameterKey=KeyName,ParameterValue=keyToSuccess ParameterKey=DBName,ParameterValue=TheDbName ParameterKey=DBPwd,ParameterValue=Th3P455w0rd ParameterKey=DBUser,ParameterValue=TheDbUser --capabilities CAPABILITY_IAM
```

## Exploring the infrastructure in the AWS Managment Console

1. Go to [https://aws.amazon.com/console/](https://aws.amazon.com/console/) and login.
1. Create a *Reource Group*. A *Reource Group* is a collection of AWS resources. Resource is an abstract term for something in AWS like an EC2 server, a load balancer, or a RDS database.
picture here
1. 





**Next: [Description of the basic template](../../documentation/basic_stack/template_desc.md)**