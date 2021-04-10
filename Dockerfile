FROM maven:3.8.1-jdk-11
WORKDIR /build
COPY . /build
RUN mvn clean install -DskipTests

FROM openjdk:11-jdk-buster
WORKDIR /root/
COPY --from=0 /build/target/bigdemo.jar /root/
CMD ["java", "-jar", "-Dspring.profiles.active=kafka,stomp,jpa", "/root/bigdemo.jar"]
