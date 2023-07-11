pipeline {
    agent any
    stages {
        stage ('Build maven'){
            steps {
                bat 'mvn clean -Dmaven.test.skip=true install'
            }
        }
        stage ('Test Unit Junit'){
            steps {
                bat 'mvn test'
            }
        }
        stage ('SonarQube Analysis'){
            environment {
                sonarscanner = tool 'SONAR_SCANNER'
            }
            steps {
                withSonarQubeEnv('SONAR_LOCAL') {
                    bat "${sonarscanner}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBackend -Dsonar.projectName='DeployBackend' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_5044a0212917cb95366ed4c5f2890ec9a04473cf -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/model/**,**Test.java,**Application.java"
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
                bat 'docker-compose --env-file ".env.test" up mysql -d'
                sleep(20)
                bat 'docker-compose --env-file ".env.test" up money-backend -d'
            }
        }
        stage('Test API Staging with Rest Assured') {
            steps{
                dir('api-test'){
                    git branch: 'main', url: 'https://github.com/GustavoHBraga/money-api-rest-assured.git'
                    bat 'mvn test'
                }
            }
        }
        stage('Deploy backend - Live'){
            steps {
                bat 'docker-compose --env-file ".env.live" up mysql -d'
                sleep(20)
                bat 'docker-compose --env-file ".env.live" up money-backend -d'
            }
        }
    }
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, api-test/target/surefire-reports/*.xml'
        }
    }
}