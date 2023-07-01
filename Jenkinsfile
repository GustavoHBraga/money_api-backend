pipeline {
    agent any
    stages {
        stage ('Build maven'){
            steps {
                bat 'mvn clean -Dmaven.test.skip=true install'
            }
        }
    }
}