FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM aicp/spark:2
COPY --from=build /usr/src/app/target/aicplauncher.jar /usr/app/aicplauncher.jar
ENTRYPOINT ["java","-jar","/usr/app/aicplauncher.jar"]