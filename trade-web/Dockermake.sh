#!/bin/bash
pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
version=$pomver

docker build -t docker.aist.io/trade-web:$version .

docker tag docker.aist.io/trade-web:$version docker.aist.io/trade-web:latest

docker push docker.aist.io/trade-web:$version
docker push docker.aist.io/trade-web:latest

docker rmi docker.aist.io/trade-web:$version
docker rmi docker.aist.io/trade-web:latest