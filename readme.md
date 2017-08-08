## Introduction

The goal of this project is to give you a rocket start into AWS CloudFormation. It should lead you through the process of setting up an infrastructure in AWS in a few minutes.

The infrastructure provides the following advantages:

- Scalable resources (Virtuelle Serves, Relationale Database)
- Security (Firewall, IAM Roles, SSL)
- Continuous- integration and delivery.

If you have any feedback/questions, we'd love to get in touch with you! :)
- yandy.piedra@zuehlke.com
- raphael.lueckl@zuehlke.com

Or join our telegram group:
@awsCloudformationTutorial

**Disclaimer:** This project is WIP. We continuely seek to improve it!

## Infrastructure
The infrastructure contains a load balancer, virtual servers, a database and a continuous-intragation and -delivery environment. A Spring Boot RESTful sample application will be deployed to the infrastructure.

![simple_mockup](documentation/images/infrastructure.png)

## Table of Contents
1. [The basic infrastructure in only five minutes](documentation/basic_stack/basic_stack.md)
1. [Description of the basic template](documentation/basic_stack/template_desc.md)
1. [Infrastructure with AWS CodePipeline for Continuous Integration and Delivery](documentation/codepipeline_stack/codepipeline.md)
1. [Infrastructure with Jenkins for Continuous Integration and Delivery](documentation/jenkins_stack/jenkins.md)
<br/><br/>

## TODO
- [x] Setup basic cloud environment
- [x] Setup CodePipeline
- [x] How to CodePipeline
- [x] Setup Jenkins
- [ ] How to Jenkins
- [x] Minimalize/optimize application
- [x] Output the name of the AutoScalingGroup
- [x] Explain Github-Tokena and how to obtain them
- [ ] Tighten SecurityGroup rules
- [ ] On destroy stack: S3 bucket needs to be cleaned up automatically for the CodePipeline template
- [ ] Avoid 1-line YAML-strings within the templates
- [ ] Console commands for CloudFormation are way to big. Separate parts to JSON file.