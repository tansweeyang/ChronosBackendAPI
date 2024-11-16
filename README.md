# Getting Started with the AutoTodo Project

Welcome to the **AutoTodo** project! This guide will walk you through the process of setting up your local development environment, building, and running the project. It includes instructions for Docker, environment setup, and other key dependencies.

## Prerequisites

Before you start, make sure you have the following tools installed:

1. **Docker** (for containerization)
    - [Download Docker](https://www.docker.com/get-started) and follow the installation instructions for your OS.

2. **Java 22** (for building and running the Spring Boot application)
    - [Download Java 22](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html).

3. **Maven** (for building the project)
    - [Install Maven](https://maven.apache.org/install.html).

4. **Git** (to clone the repository)
    - [Download Git](https://git-scm.com/).

5. **IDE** (e.g., IntelliJ IDEA, Eclipse) for editing Java code.

---

## 1. Clone the Repository

Start by cloning the project repository to your local machine:

```bash
git clone https://github.com/tansweeyang/ChronosBackendAPI.git
cd ChronosBackend
```

## 2. Setting Up the Environment

This project uses Docker to manage the application's dependencies. Follow the steps below to configure the environment.

### 2.1 Docker Environment Variables
Create a .env.docker file in the root directory of the project and add the following variables:

```bash
MYSQL_HOST=mysql
MYSQL_PORT=3306
MYSQL_USER=root
MYSQL_PASSWORD=admin1234
DATABASE_NAME=chronos

PRIVATE_KEY_PATH=/path/to/your/private/key
PUBLIC_KEY_PATH=/path/to/your/public/key

MAIL_USERNAME=your-email@example.com
MAIL_PASSWORD=your-email-password
MAIL_HOST=smtp.example.com
MAIL_PORT=587
```
Make sure to replace these values with your own credentials and paths.

### 2.2 Local Environment Variables
Create a .env file in the root directory of the project and add the following variables:
```bash
MYSQL_HOST=localhost
MYSQL_PORT=3306
MYSQL_USER=root
MYSQL_PASSWORD=
DATABASE_NAME=chronos

PRIVATE_KEY_PATH=/path/to/your/private/key
PUBLIC_KEY_PATH=/path/to/your/public/key

MAIL_USERNAME=your-email@example.com
MAIL_PASSWORD=your-email-password
MAIL_HOST=smtp.example.com
MAIL_PORT=587
```

## 4. Build the Project

Now, you can build the project using Docker and Docker Compose.

### Step 1: Build the Docker Containers
```bash
docker-compose build
```
### Step 2: Start the Containers
```bash
docker-compose up
```

This will start both the MySQL and application containers. The MySQL container will be available on port 3308 (host), and the application will be available on port 8080 (host).

## 4. Verify the Application
Once the containers are running, you can access the application by visiting http://localhost:8080 in your web browser. 

To check the application status, view the logs of the application container:
```bash
docker logs application
```

To check the MySQL database status, view the logs of the mysql container:

```bash
docker logs mysql
```

## 5. Working with Code
You can now start working on the project.
### Testing
To run unit and integration tests, use the following Maven command:

```bash
mvn clean test
```

### Building the Application
To package the application into a JAR file, run:

```bash
mvn package -DskipTests
```
This will create a JAR file in the target folder, which the Dockerfile uses to run the application.

