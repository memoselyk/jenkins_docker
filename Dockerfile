FROM jenkins/jenkins:lts-alpine

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Setup initialization scripts
COPY groovy/* /usr/share/jenkins/ref/init.groovy.d/
