FROM openjdk:8-jdk-alpine
VOLUME /tmp
MAINTAINER casey.okane8451@gmail.com
ADD target/*.jar app.jar

ENV JAVA_OPTS=""

ENTRYPOINT exec java $JAVA_OPTS \
 -Djava.security.egd=file:/dev/./urandom \
 -jar app.jar

