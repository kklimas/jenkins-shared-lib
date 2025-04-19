def call(appName: String) {
    runPipeline(appName)
}

def runPipeline(appName: String) {
    def branch = params.BRANCH ?: 'master'

    pipeline {
        agent any

        stages {
            stage('Checkout') {
                steps {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: "${branch}"]],
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