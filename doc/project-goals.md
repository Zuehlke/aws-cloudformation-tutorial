## The goals of this project
We want to show everyone how to setup an AWS Cloud Project properly using a CloudFormation template. The full proccess from a repository push to a running application in the cloud needs to be adressed.

## Requirements
For the development of our project, we need:

1. A database to save our users
1. A backend to let our app communicate with
1. A firewall, so that everything can be access restricted
1. High availability to keep our business running smooth
1. Cost efficiency: I only want as much as I need
1. Fault tolerance: When a failure happens, we still need
1. Backups of the database to not lose everything in case of any failure
1. Continuous integration to keep our development team fast and motivated
1. Multiple stages of deployment: Dev, Staging, Prod
1. We want to be alarmed if anything necessary does not work as expected

## How we achieve our needs with AWS

1. RDS gives us the possibility to easily setup, operate and scale relational databases. It also provides us with automated backup and migration features.
1. We build a Spring Boot RESTful backend for all of our communicational needs.
2. AWS provides us with SecurityGroups which acts as a virtual firewall to restrict access on ports and resources.
3. AutoScalingGroups allow us to maintain instances in different regions, which upper and lower limits of instances. If one instance goes down for whatever reason, another will automatically be started by the AutoScalingGroup.
4. The AutoScalingGroup only keeps as much instances as needed and scales the amount of them up or down, based on the incoming load. This makes sure that we run as cost efficient as possible.
5. Fault tolerance is provided by LoadBalancers, that will redirect the load to instanced that are healthy, while unhealhty will automatically be terminated and newly instantiated by the autoscaling group.
6. RDS can be ordered to automatically create backups within a custom timespan and keep those for a custom amount of days. In case of failure, we can then just load an older version.
7. We use TODO CodeDeploy or CodePipelines to maintain a stable route our code takes. The full process will be automated.
8. TODO
9. CloudWatch gives us to tools to get notified about unexpected events.