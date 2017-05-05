#!/bin/bash
#pomver=`mvn help:evaluate -Dexpression=project.version | sed -n -e '/^\[.*\]/ !{ /^[0-9]/ { p; q } }'`
pomver=`mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec`

version=$pomver
commit=`git rev-parse --verify --short=8 HEAD`
branch=`git branch | grep "^\*" | sed -e "s/^\*\ //"`
#sudo
sudo docker build -t docker.aist.io/trade-web:$version . \
     --label commit=$commit \
	 --label branch=$branch \
	 --label version=$version \
	 --label vendor=AIST \
	 --label name=trade-web
sudo docker push docker.aist.io/trade-web:$version
sudo docker rmi docker.aist.io/trade-web:$version
