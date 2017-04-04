#!/bin/bash -ex
nohup java -jar /home/ec2-user/hi-1.0-SNAPSHOT.jar >> /home/ec2-user/log.txt 2>&1 &
