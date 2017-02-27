#!/bin/bash
pomver=`mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec`
version=$pomver
#sudo
sudo docker build -t docker.aist.io/trade-web:$version .
sudo docker push docker.aist.io/trade-web:$version
sudo docker rmi docker.aist.io/trade-web:$version
