FROM openjdk:21
# Copy the pom.xml and the project files to the container
COPY target/riskevaluator-0.0.1-SNAPSHOT.jar .
EXPOSE 9005
# Build the application using Maven
CMD ["java", "-jar", "riskevaluator-0.0.1-SNAPSHOT.jar"]