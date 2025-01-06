FROM openjdk:17
VOLUME /tmp
ADD ./target/ec-payment-0.0.1-SNAPSHOT.jar ec-payment.jar
ENTRYPOINT ["java","-jar","/ec-payment.jar"]