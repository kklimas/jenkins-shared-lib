import org.lib.ArtifactPipelineDelegate

def call(Closure body) {
    def delegate = new ArtifactPipelineDelegate()
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = delegate
    body()

    def buildConfig = delegate.buildConfig
    def unitTestConfig = delegate.unitTestConfig

    pipeline {
        agent any

        stages {
            stage('Build') {
                steps {
                    echo "Building ${buildConfig.artifactId}:${buildConfig.version}"
                    sh "./gradlew clean build bootJar"
                }
            }
            stage('Unit Test') {
                when {
                    expression { "${unitTestConfig.enabled}" == "true" }
                }

                steps {
                    echo "Running unit tests..."
                    sh "./gradlew test"
                }
            }
        }
    }
}