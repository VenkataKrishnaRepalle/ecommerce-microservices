# Use a lightweight base image with Java 17 for the final image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /config-server

# Set default values
ARG ARTIFACT_ID=default-artifact
ARG VERSION=default-version

# Copy the source code
COPY ./target/${ARTIFACT_ID}-${VERSION}.jar config-server.jar

## Define the command to run your application
CMD ["java", "-jar", "config-server.jar"]
