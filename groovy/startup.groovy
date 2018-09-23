import jenkins.model.Jenkins
import jenkins.model.GlobalConfiguration
import hudson.model.*
import java.util.logging.Logger
import javaposse.jobdsl.plugin.GlobalJobDslSecurityConfiguration

Logger.global.info("[Running] startup script")

disableDslScriptApproval()

Jenkins.instance.save()

buildJob('dsl-seed-job')

Logger.global.info("[Done] startup script")

private void disableDslScriptApproval() {
    // From https://stackoverflow.com/a/49372857/5509239
    def jobDslConfig = GlobalConfiguration.all().get(GlobalJobDslSecurityConfiguration.class)
    jobDslConfig.useScriptSecurity=false
    jobDslConfig.save()
}

private def buildJob(String jobName) {
    Logger.global.info("Building job '$jobName")
    def job = Jenkins.instance.getJob(jobName)
    Jenkins.instance.queue.schedule(job, 0, new CauseAction(
        new hudson.model.Cause.RemoteCause("localhost", "Jenkins startup script")
    ))
}
