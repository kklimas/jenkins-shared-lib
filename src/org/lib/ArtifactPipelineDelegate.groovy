package org.lib

class ArtifactPipelineDelegate {
    Map buildConfig = [:]
    Map nexusConfig = [:]

    void build(Closure configClosure) {
        configClosure.resolveStrategy = Closure.DELEGATE_FIRST
        configClosure.delegate = buildConfig
        configClosure()
    }

    void pushToNexus(Closure configClosure) {
        configClosure.resolveStrategy = Closure.DELEGATE_FIRST
        configClosure.delegate = nexusConfig
        configClosure()
    }

    void run() {
        pipeline {
            agent any
            stages {
                stage('Build') {
                    steps {
                        echo "Building ${buildConfig.artifactId}:${buildConfig.version}"
                        sh "./gradlew clean build"
                    }
                }
            }
        }
    }
}