import helper.JobHelperMock
import javaposse.jobdsl.dsl.*
import spock.lang.Specification

class TestEditorXtextSpec extends Specification {

    def 'jobExecutionWorks'() {
        given:
        JobManagement jm = new MemoryJobManagement()
        String script = new File('jobs/test_editor_xtext.groovy').text.with {
            return replaceFirst('import static helper\\.JobHelper', "import static ${JobHelperMock.name}")
        }

        when:
        GeneratedItems generatedItems = DslScriptLoader.runDslEngine(script, jm)

        then:
        noExceptionThrown()

        and: 'check for jobs that should be there'
        generatedItems.jobs.any { GeneratedJob job -> job.jobName == "test-editor-xtext_develop" }
        generatedItems.jobs.any { GeneratedJob job -> job.jobName == "test-editor-xtext_feature_myBranch" }
    }

}
