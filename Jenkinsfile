pipeline {
	agent any

	environment {
      CFAPI = 'https://api.run.pivotal.io'
      CFUSERNAME = credentials('PCFUSER')
      CFPASS = credentials('PCFPASS')
    }

	stages{
	    stage('Build'){
	        steps {
	            sh './gradlew build -x test'
	        }
	    }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'cf login -a $CFAPI -o solstice-org -s cklenke-cnt -u $CFUSERNAME -p $CFPASS'
                sh 'cf push account-service -t 180 -p applications/account/build/libs/applications/account-0.0.1-SNAPSHOT.jar --random-route'
                sh 'cf push order-service -t 180 -p applications/order/build/libs/applications/order-0.0.1-SNAPSHOT.jar --random-route'
                sh 'cf push product-service -t 180 -p applications/product/build/libs/applications/product-0.0.1-SNAPSHOT.jar --random-route'
                sh 'cf push shipment-service -t 180 -p applications/shipment/build/libs/applications/shipment-0.0.1-SNAPSHOT.jar --random-route'
            }
        }
	}
}
			
