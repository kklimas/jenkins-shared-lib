package org.lib

class ArtifactPipelineDelegate {
    Map buildConfig = [:]
    Map nexusConfig = [:]

    void build(Closure c) {
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c.delegate = buildConfig
        c()
    }

    void pushToNexus(Closure c) {
        c.resolveStrategy = Closure.DELEGATE_FIRST
        c.delegate = nexusConfig
        c()
    }
}