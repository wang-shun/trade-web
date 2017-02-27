#!/bin/bash
pomver=`mvn -q -Dexec.executable="echo" -Dexec.args='${project.version}' --non-recursive exec:exec`
version=$pomver

sudo docker build -t docker.aist.io/yc-par-web:$version .
sudo docker push docker.aist.io/yc-par-web:$version
sudo docker rmi docker.aist.io/yc-par-web:$version
