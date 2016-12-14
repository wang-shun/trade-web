#!/bin/bash
pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
version=$pomver-${BUILD_NUMBER}

docker build -t docker.aist.io/yc-par-web:${BUILD_NUMBER} .

docker tag docker.aist.io/yc-par-web:${BUILD_NUMBER} docker.aist.io/yc-par-web:$version
docker tag docker.aist.io/yc-par-web:${BUILD_NUMBER} docker.aist.io/yc-par-web:latest

docker push docker.aist.io/yc-par-web:${BUILD_NUMBER}
docker push docker.aist.io/yc-par-web:$version
docker push docker.aist.io/yc-par-web:latest

docker rmi docker.aist.io/yc-par-web:${BUILD_NUMBER}
docker rmi docker.aist.io/yc-par-web:$version
docker rmi docker.aist.io/yc-par-web:latest