FROM openjdk:21
# Copy the pom.xml and the project files to the container
COPY target/gateway-0.0.1-SNAPSHOT.jar .
EXPOSE 9003
# Build the application using Maven
CMD ["java", "-jar", "gateway-0.0.1-SNAPSHOT.jar"]