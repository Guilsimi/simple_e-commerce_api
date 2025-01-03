FROM openjdk:17
VOLUME /tmp
EXPOSE 8888
ADD ./target/ec-config-server-0.0.1-SNAPSHOT.jar ec-config-server.jar
ENTRYPOINT ["java","-jar","/ec-config-server.jar"]