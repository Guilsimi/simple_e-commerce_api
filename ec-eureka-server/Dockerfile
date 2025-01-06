FROM openjdk:17
VOLUME /tmp
EXPOSE 8761
ADD ./target/ec-eureka-server-0.0.1-SNAPSHOT.jar ec-eureka-server.jar
ENTRYPOINT ["java","-jar","/ec-eureka-server.jar"]