node('docker-agent'){
	stage("checkout"){
		git branch: '${BRANCH_NAME}', credentialsId: 'd545777a-09ce-4018-b4d4-a25eb41a752d', url: 'http://git.centaline.com/trade-group/trade-web.git'
	}
	stage('build'){
		sh "mvn clean install deploy"	
	}
	stage('ship'){
		sh "sudo docker login -u admin -p admin123 docker.aist.io"
		dir('trade-web') {
		    sh "sh Dockermake.sh"
		}
		dir('yc-par-portal') {
		    sh "sh Dockermake.sh"
		}
	}
}

def version() {
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    matcher ? matcher[0][1] : null
}