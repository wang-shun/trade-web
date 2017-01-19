#!/bin/bash
pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
version=$pomver

docker build -t docker.aist.io/trade-web:$version .
docker push docker.aist.io/trade-web:$version
docker rmi docker.aist.io/trade-web:$version
