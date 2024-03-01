FROM openjdk:21
# Copy the pom.xml and the project files to the container
COPY target/back-0.0.1-SNAPSHOT.jar .
EXPOSE 9002
# Build the application using Maven
CMD ["java", "-jar", "back-0.0.1-SNAPSHOT.jar"]