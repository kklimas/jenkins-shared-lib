def call(Closure body) {
    def config = [:]

    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = config
    body()

    runPipeline(config)
}

def runPipeline(Map config) {
    def appName = config.appName ?: 'unknown'
    def branch = config.branch ?: params.BRANCH ?: 'master'

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