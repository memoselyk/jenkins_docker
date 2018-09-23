#!groovy

import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule

// I would rather use "/run/secrets" (https://technologyconversations.com/2017/06/16/automating-jenkins-docker-setup/)
// but this need to work with Docker 1.7

Properties properties = new Properties()
File configFile = new File('/usr/share/jenkins/config.props')
if (configFile.exists()) {
  configFile.withInputStream {
    properties.load(it)
  }
}
def user = properties.getProperty("JENKINS_ADMIN_USER", "admin")
def pass = properties.getProperty("JENKINS_ADMIN_PASS", "admin")

println "================================================================================"
println "                     Creating administrative user: ${user}                      "
println "================================================================================"

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(user, pass)
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()

Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class)
