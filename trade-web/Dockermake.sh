#!/bin/bash
pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
version=$pomver-${BUILD_NUMBER}

docker build -t docker.aist.io/trade-web:${BUILD_NUMBER} .

docker tag docker.aist.io/trade-web:${BUILD_NUMBER} docker.aist.io/trade-web:$version
docker tag docker.aist.io/trade-web:${BUILD_NUMBER} docker.aist.io/trade-web:latest

docker push docker.aist.io/trade-web:${BUILD_NUMBER}
docker push docker.aist.io/trade-web:$version
docker push docker.aist.io/trade-web:latest

docker rmi docker.aist.io/trade-web:${BUILD_NUMBER}
docker rmi docker.aist.io/trade-web:$version
docker rmi docker.aist.io/trade-web:latest