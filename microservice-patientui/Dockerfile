FROM openjdk:21
# Copy the pom.xml and the project files to the container
COPY target/patientui-0.0.1-SNAPSHOT.jar .
EXPOSE 9001
# Build the application using Maven
CMD ["java", "-jar", "patientui-0.0.1-SNAPSHOT.jar"]