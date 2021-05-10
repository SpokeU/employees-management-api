FROM openjdk:11-jdk-slim
EXPOSE 8080
COPY build/libs/employees-management-api-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/employees-management-api-0.0.1-SNAPSHOT.jar"]