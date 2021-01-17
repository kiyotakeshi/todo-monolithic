# Todo

## [API Reference](./src/main/asciidoc/index.adoc)

- this is .adoc ,so run application and access [http://localhost:8081](http://localhost:8081)

## [Domain Model](./docs/domain-model.puml)

## Run local

- set jdk 11

```shell
export JAVA_HOME=~/Library/Java/JavaVirtualMachines/adopt-openjdk-11.0.8/Contents/Home
```

- spring

```shell
./mvnw spring-boot:run
```

- maven

```shell
./mvnw clean package

ARTIFACT_VERSION=$(./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)

java -jar target/todo-$ARTIFACT_VERSION.jar
```

## Build Docker image

- spring

```shell
./mvnw spring-boot:build-image
```

- [Cloud Native Buildpacks](https://buildpacks.io/docs/tools/pack/)

```shell
./mvnw clean package

ARTIFACT_VERSION=$(./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)

pack build todo:$ARTIFACT_VERSION -p target/todo-$ARTIFACT_VERSION.jar --builder cloudfoundry/cnb:bionic
```

- run as a docker container

```shell
docker image ls | grep todo

docker run -p 8081:8081 todo:TAG_VERSION
```
