## Introduction [WIP]

The goal of this project is to give you a rocket start into AWS CloudFormation. It should lead you through the process of setting up an infrastructure in AWS in a few minutes.

The infrastructure provides the following advantages:

- Scalable resources (Virtuelle Serves, Relationale Database)
- Security (Firewall, IAM Roles, SSL)
- Continuous- integration and delivery.

We are always happy to get your support. If you find anything that could be done better, we highly appreciate your feedback. :)

## Infrastructure
The infrastructure contains a load balancer, virtual servers, a database and a continuous-intragation and -delivery environment. A Spring Boot RESTful sample application will be deployed to the infrastructure.

![simple_mockup](documentation/images/infrastructure.png)


## Table of Contents
1. [The basic infrastructure in only five minutes](documentation/basic_stack/basic_stack.md)
1. [Description of the basic template](documentation/basic_stack/template_desc.md)
1. [Infrastructure with AWS CodePipeline for continuous- integration and delivery](documentation/codepipeline_stack/cdoepipeline.md)
1. [Infrastructure with Jenkins for continuous- integration and delivery](documentation/jenkins_stack/jenkins.md)
<br/><br/>