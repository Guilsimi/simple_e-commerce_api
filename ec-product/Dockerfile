FROM openjdk:17
VOLUME /tmp
ADD ./target/ec-product-0.0.1-SNAPSHOT.jar ec-product.jar
ENTRYPOINT ["java","-jar","/ec-product.jar"]