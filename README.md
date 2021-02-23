# Todo

## [API Reference](src/main/asciidoc/api-reference.adoc)

- this is .adoc ,so run application and access [http://localhost:8081/api](http://localhost:8081/api)

## [Domain Model](./docs/domain-model.puml)

## Run local

- set jdk 11

```shell
export JAVA_HOME=`/usr/libexec/java_home -v 11`

java -version
```

- run postgres

```shell
docker-compose up -d
```

- build(generate api reference)

```shell
./mvnw clean package
```

- run application as a spring-boot

```shell
./mvnw spring-boot:run
```

- run application as a jar using maven

```shell
ARTIFACT_VERSION=$(./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)

java -jar target/todo-$ARTIFACT_VERSION.jar
```

### Access [localhost:8081](http://localhost:8081/)

## Build Docker image

- spring

```shell
./mvnw spring-boot:build-image
```

- [Cloud Native Buildpacks](https://buildpacks.io/docs/tools/pack/)

```shell
./mvnw clean package

export ARTIFACT_VERSION=$(./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)

pack build todo:$ARTIFACT_VERSION -p target/todo-$ARTIFACT_VERSION.jar --builder cloudfoundry/cnb:bionic
```

## Run Docker container

- run as a docker container ***from spring or Cloud Native Buildpacks image***

```shell
docker image ls | grep todo

export ARTIFACT_VERSION=$(./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)

docker-compose -f app.yaml up -d

docker-compose -f app.yaml ps

docker-compose -f app.yaml down
```

- run as a docker container ***from Dockerfile image***

```shell
export ARTIFACT_VERSION=$(./mvnw org.apache.maven.plugins:maven-help-plugin:3.2.0:evaluate -Dexpression=project.version -q -DforceStdout)

docker-compose -f app-from-dockerfile.yaml build

docker-compose -f app-from-dockerfile.yaml up -d

docker-compose -f app-from-dockerfile.yaml ps

docker-compose -f app-from-dockerfile.yaml down
```
