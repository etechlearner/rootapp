FROM openjdk:8u111-jdk-alpine
VOLUME /tmp
ADD target/rootapp-0.0.1-SNAPSHOT.jar app.jar
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
