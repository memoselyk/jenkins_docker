FROM jenkins/jenkins:lts-alpine

ENV JENKINS_REF /usr/share/jenkins/ref
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Setup initialization scripts
COPY groovy/* $JENKINS_REF/init.groovy.d/
COPY jobs $JENKINS_REF/jobs

COPY plugins.txt $JENKINS_REF/plugins.txt
RUN /usr/local/bin/install-plugins.sh < $JENKINS_REF/plugins.txt
