node {
	stage('Checkout') {
		git 'file:///home/andrey/Development/service-registration'
	}
	
	stage('Build') {
		sh './gradlew clean'
	}
}
