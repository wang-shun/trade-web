#!/bin/bash
pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
version=$pomver

docker build -t docker.aist.io/yc-par-web:$version .

docker tag docker.aist.io/yc-par-web:$version docker.aist.io/yc-par-web:latest

docker push docker.aist.io/yc-par-web:$version
docker push docker.aist.io/yc-par-web:latest

docker rmi docker.aist.io/yc-par-web:$version
docker rmi docker.aist.io/yc-par-web:latest