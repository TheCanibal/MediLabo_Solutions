FROM openjdk:21

COPY target/mongodb-0.0.1-SNAPSHOT.jar .
EXPOSE 9004
# Build the application using Maven
CMD ["java", "-jar", "mongodb-0.0.1-SNAPSHOT.jar"]