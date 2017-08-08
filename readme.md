## Introduction

The goal of this project is to give you a rocket start into AWS CloudFormation. It should lead you through the process of setting up a basic infrastructure in AWS in a few minutes, while covering the most common services.

The infrastructure focuses on the following advantages AWS provides:

- Scalable resources (virtual servers, relational databases)
- Security (Firewall, IAM Roles, SSL)
- Continuous Integration and Delivery (only a push to Github will update our servers)

## Infrastructure
The infrastructure contains a load balancer, virtual servers, a database and a Continuous Integration and Delivery environment. A Spring Boot RESTful sample application will be deployed and managed by our environment.

![simple_mockup](documentation/images/infrastructure.png)

## Table of Contents
1. [The basic infrastructure in only five minutes](documentation/basic_stack/basic_stack.md)
1. [Description of the basic template](documentation/basic_stack/template_desc.md)
1. [Infrastructure with AWS CodePipeline for Continuous Integration and Delivery](documentation/codepipeline_stack/codepipeline.md)
1. [Infrastructure with Jenkins for Continuous Integration and Delivery](documentation/jenkins_stack/jenkins.md)
<br/><br/>

## Feedback

If you have any feedback/questions, we'd love to get in touch with you! :)
- yandy.piedra@zuehlke.com
- raphael.lueckl@zuehlke.com

Or join our telegram group:
@awsCloudformationTutorial

**Disclaimer:** This project is WIP. We continually seek to improve it!

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