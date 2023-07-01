pipeline {
    agent any
    stages {
        stage ('Build maven'){
            steps {
                bat 'mvn clean -Dmaven.test.skip=true install'
            }
        }
        stage ('SonarQube Analysis'){
            environment {
                sonarscanner = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${sonarscanner}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBackend -Dsonar.projectName='DeployBackend' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_5044a0212917cb95366ed4c5f2890ec9a04473cf -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/model/**"
                }
            }
        }
        stage('Quality Gate'){
            steps{
                sleep(5)
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Deploy backend'){
            steps {
                deploy adapters: [tomcat9(credentialsId: 'de45c837-4455-4bfb-878d-72508a227886', path: '', url: 'http://localhost:8888')], contextPath: 'money-backend', war: 'target/money-api-1.0.0.war'
            }
        }
        stage('Test API with Rest Assured') {
            steps{
                git branch: 'main', url: 'https://github.com/GustavoHBraga/money-api-rest-assured.git'
                bat 'mvn test'
            }
        }
    }
}