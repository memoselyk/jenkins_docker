#!groovy

// Harden Jenkins and remove all the nagging warnings in the web interface
// Source: https://gist.github.com/peterjenkins1/8f8bdbc82669314f7a2cc392f48be6a0
import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.model.Jenkins
import jenkins.security.s2m.*
import java.util.logging.Logger

Jenkins jenkins = Jenkins.getInstance()

Logger.global.info("Disable remote CLI")
jenkins.CLI.get().setEnabled(false)

Logger.global.info("Enable Agent to master security subsystem")
jenkins.injector.getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false);

Logger.global.info("Disable jnlp")
jenkins.setSlaveAgentPort(-1);

Logger.global.info("Disable old Non-Encrypted protocols")
HashSet<String> newProtocols = new HashSet<>(jenkins.getAgentProtocols());
newProtocols.removeAll(Arrays.asList(
        "JNLP3-connect", "JNLP2-connect", "JNLP-connect", "CLI-connect"
));
jenkins.setAgentProtocols(newProtocols);

Logger.global.info("Enable CSRF protection")
jenkins.setCrumbIssuer(new DefaultCrumbIssuer(true))

jenkins.save()
