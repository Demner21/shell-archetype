FROM openjdk:11-slim-buster as build
WORKDIR /apps/shell-archetype
COPY target/lib ./lib
COPY target/shell-archetype-1.0.0.jar .
ENTRYPOINT ["java" ,"-jar", "shell-archetype-1.0.0.jar"]
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar shellarchetype.jar