def call() {
    runPipeline()
}

def runPipeline() {
    pipeline {
        agent any

        stages {
            stage('Checkout') {
                steps {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "${params.BRANCH}"]],
                        userRemoteConfigs: scm.userRemoteConfigs
                    ])
                }
            }

            stage('Build') {
                steps {
                    echo "Building ${appName}"
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