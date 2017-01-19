#!/bin/bash
pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
version=$pomver

sudo docker build -t docker.aist.io/trade-web:$version .
sudo docker push docker.aist.io/trade-web:$version
sudo docker rmi docker.aist.io/trade-web:$version
