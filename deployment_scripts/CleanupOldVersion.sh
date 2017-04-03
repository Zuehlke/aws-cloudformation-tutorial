#!/bin/bash -ex
[ -e appspec.yml ] && rm appspec.yml || echo "appspec.yml does not exist."
[ -e deployment_scripts/AppStart.sh ] && rm deployment_scripts/AppStart.sh || echo "AppStart.sh does not exist."
[ -e deployment_scripts/AppStop.sh ] && rm deployment_scripts/AppStop.sh || echo "AppStop.sh does not exist."
[ -e src/Hi/build/libs/hi-1.0-SNAPSHOT.jar ] && rm src/Hi/build/libs/hi-1.0-SNAPSHOT.jar || echo "hi-1.0-SNAPSHOT.jar does not exist."
