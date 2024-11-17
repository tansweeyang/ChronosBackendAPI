# The base image on which we would build our image
FROM openjdk:21-jdk-slim

# Install curl and maven
RUN apt-get update && apt-get install -y curl maven && apt-get clean

# Set environment variables
ENV MYSQL_HOST=${MYSQL_HOST}
ENV MYSQL_PORT=${MYSQL_PORT}
ENV MYSQL_USER=${MYSQL_USER}
ENV MYSQL_PASSWORD=${MYSQL_PASSWORD}
ENV DATABASE_NAME=${DATABASE_NAME}

ENV PRIVATE_KEY_PATH=${PRIVATE_KEY_PATH}
ENV PUBLIC_KEY_PATH=${PUBLIC_KEY_PATH}

ENV MAIL_USERNAME=${MAIL_USERNAME}
ENV MAIL_PASSWORD=${MAIL_PASSWORD}
ENV MAIL_HOST=${MAIL_HOST}
ENV MAIL_PORT=${MAIL_PORT}

# Expose port 8080
EXPOSE 8080

# Set the working directory
WORKDIR /app

# Copy the pom.xml file to the working directory
COPY pom.xml .

# Resolve the dependencies in the pom.xml file
RUN mvn dependency:resolve

# Copy the source code to the working directory
COPY src src

# Build the project
RUN mvn clean package -DskipTests

# Run the application
ENTRYPOINT ["java", "-jar", "target/application.jar"]