package org.lib

class ArtifactPipelineDelegate {
    Map buildConfig = [:]
    Map unitTestConfig = [:]

    void build(Closure c) {
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c.delegate = buildConfig
        c()
    }

    void unitTests(Closure c) {
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c.delegate = unitTestConfig
        c()
    }
}