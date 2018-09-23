# jenkins_docker
Container with a Jenkins server ready for infra-as-a-code

## Running

In order to set the administrative user credentials, the container requires a properties file like this:

```
JENKINS_ADMIN_USER=admin
JENKINS_ADMIN_PASS=admin
```

When running the container it should be passed as a docker volume:

```
-v "$PWD"/sample.properties:/usr/share/jenkins/config.props
```

The seed job takes configuration from an URL provided in the environment variable `JOBS_URL`, so it must be passed when running the container.
