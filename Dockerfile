FROM eclipse-temurin:17-jdk-jammy

WORKDIR /tab-application

COPY target/*.jar tab-application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tab-application.jar"]