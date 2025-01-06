FROM openjdk:17
VOLUME /tmp
ADD ./target/ec-orders-0.0.1-SNAPSHOT.jar ec-orders.jar
ENTRYPOINT ["java","-jar","/ec-orders.jar"]