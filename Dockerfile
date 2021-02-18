# Start with a base image containing Java runtime
FROM adoptopenjdk/openjdk11:jdk-11.0.2.9-alpine
LABEL maintainer="Egor Avilov <egoravilov99@mail.ru>"
# Add a volume pointing to /tmp
VOLUME /tmp
# Make port available to the world outside this container
EXPOSE 8080
# The application's jar file
ARG JAR_FILE=/target/Inventory-Service-0.0.1-SNAPSHOT.jar
# Add the application's jar to the container
ADD ${JAR_FILE} inventory-service.jar
ENV JAVA_OPTS="-Xms128m -Xmx128m"
# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/inventory-service.jar"]