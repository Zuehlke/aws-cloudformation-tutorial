#!/bin/bash -ex
[ -e appspec.yml ] && rm appspec.yml
[ -e deployment_scripts/AppStart.sh ] && rm deployment_scripts/AppStart.sh
[ -e deployment_scripts/AppStop.sh ] && rm deployment_scripts/AppStop.sh
[ -e src/Hi/build/libs/hi-1.0-SNAPSHOT.jar ] && rm src/Hi/build/libs/hi-1.0-SNAPSHOT.jar
