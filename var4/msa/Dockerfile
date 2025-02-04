FROM openjdk:17-jdk-slim
ARG JAR_FILE=target/msa.jar
COPY ${JAR_FILE} msa.jar
ENTRYPOINT ["java", "-jar", "msa.jar"]
ENV TZ=Europe/Berlin