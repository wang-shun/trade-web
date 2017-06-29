node('docker-agent'){
	stage("checkout"){
		git branch: '${BRANCH_NAME}', credentialsId: 'jenkins', url: 'http://gitlab.centaline.com.cn/centaline-trade/trade-web.git'
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
		    sh "sh Dockermake_swagger_start.sh"
		    sh "sh Dockermake_swagger_edit.sh"
		}
		dir('trade-mobile') {
		    sh "sh Dockermake.sh"
		    sh "sh Dockermake_swagger_start.sh"
		    sh "sh Dockermake_swagger_edit.sh"
		}
	}
}

def version() {
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    matcher ? matcher[0][1] : null
}
