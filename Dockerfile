FROM openjdk:12
VOLUME /tmp
EXPOSE 9100
ADD ./target/springboot-oauth-server-0.0.1-SNAPSHOT.jar oauth-server.jar
ENTRYPOINT [ "java", "-jar", "oauth-server.jar" ]