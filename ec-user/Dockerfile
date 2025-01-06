FROM openjdk:17
VOLUME /tmp
ADD ./target/ec-user-0.0.1-SNAPSHOT.jar ec-user.jar
ENTRYPOINT ["java","-jar","/ec-user.jar"]