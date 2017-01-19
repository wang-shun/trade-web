node('docker-agent'){
	stage("checkout"){
		checkout([$class: 'GitSCM', branches: [[name: '*/develop']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'd545777a-09ce-4018-b4d4-a25eb41a752d', url: 'http://git.centaline.com/trade-group/trade-web.git']]])
	}
	stage('build'){
		sh "mvn clean install deploy"	
	}
}