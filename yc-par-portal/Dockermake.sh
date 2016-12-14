#!/bin/bash
pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
version=$pomver-${BUILD_NUMBER}

docker build -t docker.aist.io/partner:${BUILD_NUMBER} .

docker tag docker.aist.io/partner:${BUILD_NUMBER} docker.aist.io/partner:$version
docker tag docker.aist.io/partner:${BUILD_NUMBER} docker.aist.io/partner:latest

docker push docker.aist.io/partner:${BUILD_NUMBER}
docker push docker.aist.io/partner:$version
docker push docker.aist.io/partner:latest

docker rmi docker.aist.io/partner:${BUILD_NUMBER}
docker rmi docker.aist.io/partner:$version
docker rmi docker.aist.io/partner:latest