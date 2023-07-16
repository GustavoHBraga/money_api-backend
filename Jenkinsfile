pipeline {
    agent any
    stages {
        stage ('Build maven'){
            steps {
                bat 'mvn clean -DskipTests=true install'
            }
        }
        stage ('Test Unit'){
            steps {
                bat 'mvn clean test install'
            }
        }
        stage ('SonarQube Analysis'){
            environment {
                sonarscanner = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${sonarscanner}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBackend -Dsonar.projectName='DeployBackend' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_5044a0212917cb95366ed4c5f2890ec9a04473cf -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/model/**,**/exceptionHandler/**,**/event/**,**/src/test/java/com/moneyapi/**,**Application.java"
                }
            }
        }
        stage('Quality Gate'){
            steps{
                sleep(5)
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Deploy backend - Staging'){
            steps {
                bat 'docker rmi -f deployfull-pipeline-money-backend'
                sleep(10)
                bat 'docker-compose --env-file ".env.test" up mysql -d'
                sleep(20)
                bat 'docker-compose --env-file ".env.test" up money-backend -d --force-recreate'
            }
        }
        stage('Test API Staging with Rest Assured') {
            steps{
                dir('api-test'){
                    git branch: 'main', url: 'https://github.com/GustavoHBraga/money-api-rest-assured.git'
                    bat 'mvn clean test'
                }
            }
        }
        stage('Deploy backend - Live'){
            steps {
                bat 'docker-compose --env-file ".env.live" up mysql -d'
                sleep(20)
                bat 'docker-compose --env-file ".env.live" up money-backend -d --force-recreate'
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml'
        }
    }
}