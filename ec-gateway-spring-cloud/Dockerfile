FROM openjdk:17
VOLUME /tmp
EXPOSE 8765
ADD ./target/ec-gateway-spring-cloud-0.0.1-SNAPSHOT.jar ec-gateway-spring-cloud.jar
ENTRYPOINT ["java","-jar","/ec-gateway-spring-cloud.jar"]