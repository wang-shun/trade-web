#!/bin/bash
#pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
pomver=`mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec`
version=pomver
commit=`git rev-parse --verify --short=8 HEAD`
branch=`git branch | grep "^\*" | sed -e "s/^\*\ //"`
#sudo
sudo docker build -f Dockerfile_swagger_start -t docker.aist.io/yc-par-swagger-start:$version . \
     --label commit=$commit \
	 --label branch=$branch \
	 --label version=$version \
	 --label vendor=AIST \
	 --label name=yc-par-swagger-start
sudo docker push docker.aist.io/yc-par-swagger-start:$version
sudo docker rmi docker.aist.io/yc-par-swagger-start:$version
