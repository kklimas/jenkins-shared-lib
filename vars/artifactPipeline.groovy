def call() {
    pipeline {
        agent any

        stages {
            stage('Build') {
                steps {
                    sh "./gradlew build"
                }
            }

            stage('Unit Tests') {
                steps {
                    sh './gradlew test'
                    junit '**/build/test-results/test/*.xml'
                }
            }
        }
    }
}
