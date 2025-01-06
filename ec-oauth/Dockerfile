FROM openjdk:17
VOLUME /tmp
ADD ./target/ec-oauth-0.0.1-SNAPSHOT.jar ec-oauth.jar
ENTRYPOINT ["java","-jar","/ec-oauth.jar"]