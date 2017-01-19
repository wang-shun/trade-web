node {
	stage('Build') { 
	 	sh('mvn clean install deploy')
	 	sh('docker login -u admin -p admin123 docker.aist.io')
	 	sh('workpath=`pwd`)
	 	sh('cd $workpath/trade-web')
		sh('sh Dockermake.sh')
		sh('cd $workpath/yc-par-portal')
		sh('sh Dockermake.sh')
	}
}