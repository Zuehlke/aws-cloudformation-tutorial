## Infrastructure with AWS CodePipeline for continuous- integration and delivery

![simple_mockup](../../documentation/images/infrastructure_codepipeline.png)

## Description
For building the infrastructure we will use among others the following AWS services:

- *Elastic Load Balancing (ELB)* used to distribute the traffic to the web servers behind it.
- *Elastic Compute Cloud (EC2)*, two virtual Linux servers called Amazon Linux. The SpringBoot application with the embedded Tomcat Server will be installed on each virtual server whit help of AWS CodePipeline.
- *Relational Database Service (RDS)* providing a MySQL database. The Spring Boot application relies on this database.
- *CodePipeline* a pipe line used for continuous- integration and delivery. It retrieves the last commit from the Git repository, tests, builds the application and after that deploys the application to the EC2 instances.

## Quick Start
1. Make sure that you've started the ![first stack](../basic_stack/basic_stack.md) and it is successfully running. Status: *CREATE_COMPLETE* when you type: aws cloudformation describe-stacks --stack-name theStackIsBack --region us-east-1 | grep StackStatus
1. You need the outputs from the basic stack you created before. Using the console, you can type the following command to obtain the necessary values:
```
aws cloudformation describe-stacks --stack-name theStackIsBack --region eu-central-1 | grep -C 1 "OutputValue"
```
We need the "OutputValue" of the AutoScalingGroupName and the the DBLink
1. [Download the CodePipeline-Template](../../templates/stack_with_codepipeline/template_codepipeline.json)
1. You need to have your GitHub-Token ready, which AWS will use to interact with GitHub. ![Read up here](https://help.github.com/articles/creating-a-personal-access-token-for-the-command-line/) on how to get one, if you don't have one already. The token must have full permissions for *repo* and *admin:repo_hook*.
1. Open a terminal and move to the path to the where the template has been downloaded. Execute the command with your custom "OutputValues":
```
aws cloudformation create-stack --region us-east-1 --stack-name cool-pipeline-staging --template-body file://template_codepipeline.json --parameters ParameterKey=KeyName,ParameterValue=KeyToSuccess ParameterKey=DBName,ParameterValue=TheDbName ParameterKey=DBPwd,ParameterValue=Th3P455w0rd ParameterKey=DBUser,ParameterValue=TheDbUser ParameterKey=DBLink,ParameterValue=<DB_ENDPOINT_WITHOUT_PORT> ParameterKey=AutoScalingGroup,ParameterValue=<AUTO_SCALING_GROUP_NAME> ParameterKey=GitHubBranch,ParameterValue=master ParameterKey=GitHubRepo,ParameterValue=aws-cloudformation-tutorial ParameterKey=GitHubToken,ParameterValue=<GITHUB_TOKEN> ParameterKey=GitHubUser,ParameterValue=Zuehlke --capabilities CAPABILITY_NAMED_IAM
```
1. Done