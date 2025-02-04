FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/msb.jar
COPY ${JAR_FILE} msb.jar
ENTRYPOINT ["java", "-jar", "msb.jar"]
ENV TZ=Europe/Berlin