node {
	stage('Checkout') {
		git 'file:///home/andrey/Development/service-registration'
	}
	
	stage('Clean') {
		sh './gradlew clean build -p account-service'
	}
	
	stage('Image') {
		dir ('account-service') {
			sh 'docker build -t asatnin/account-service:latest .'
		}
	}
	
	stage('Run') {
		sh 'docker run -p 8082:8082 asatnin/account-service'
	}
}
