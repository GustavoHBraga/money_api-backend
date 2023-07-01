pipeline {
    agent any
    stages {
        stage ('Build maven'){
            steps {
                bat 'mvn clean -Dmaven.test.skip=true install'
            }
        }
        stage('SonarQube Analysis') {
            environment {
                sonarscanner = tool 'SONAR_SCANNER'
            }
            withSonarQubeEnv('SONAR_LOCAL') {
                bat "${sonarscanner}/bin/sonar-scanner -e -Dsonar.projectKey=DeployBackend -Dsonar.projectName='DeployBackend' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=sqp_5044a0212917cb95366ed4c5f2890ec9a04473cf -Dsonar.java.binaries=target -Dsonar.coverage.exclusions=**/.mvn/**,**/model/**"
            }
        }
    }
}