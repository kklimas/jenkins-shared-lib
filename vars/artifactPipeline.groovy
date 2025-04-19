import org.lib.ArtifactPipelineDelegate

static def call(Closure body) {
    def pipeline = new ArtifactPipelineDelegate()
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipeline
    body()

    pipeline.run()
}