#!/bin/bash
pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
version=$pomver
commit=`git rev-parse --verify --short=8 HEAD`
branch=`git branch | grep "^\*" | sed -e "s/^\*\ //"`
#sudo
sudo docker build -t docker.aist.io/trade-web:$version . \
     --label commit=$commit \
	 --label branch=$branch \
	 --label version=$version 
sudo docker push docker.aist.io/trade-web:$version
sudo docker rmi docker.aist.io/trade-web:$version
