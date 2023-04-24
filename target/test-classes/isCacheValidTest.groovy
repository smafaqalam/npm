import com.hcl.icontrol.jenkins.support.JenkinsPipelineSharedLibTemplateTest

class isCacheValidTest extends JenkinsPipelineSharedLibTemplateTest {

    @Override
    String getScriptName() {
        return "vars/isCacheValid.groovy"
    }

    @Override
    Map prepareParams() {
        addEnvVar("WORKSPACE", "test-workspace")
        addEnvVar("JENKINS_GCS_BUCKET", "test-bucket-npmcache")
        addEnvVar("JOB_NAME", "icontrol")
        return [checksum: "12345"]
    }
}