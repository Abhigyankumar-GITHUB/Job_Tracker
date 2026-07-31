# Stage 1: Maven se WAR build karo
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests


# Stage 2: Tomcat 10 par application run karo
FROM tomcat:10.1-jdk17-temurin

# Default Tomcat applications remove
RUN rm -rf /usr/local/tomcat/webapps/*

# Generated WAR ko ROOT application ke roop me deploy karo
COPY --from=build /app/target/JobTracker.war \
     /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]