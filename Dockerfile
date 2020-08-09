FROM maven:3.5-jdk-8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package
RUN wget https://archive.apache.org/dist/spark/spark-3.0.0/spark-3.0.0-bin-hadoop2.7.tgz
RUN tar -xzvf spark-3.0.0-bin-hadoop2.7.tgz && rm spark-3.0.0-bin-hadoop2.7.tgz

FROM bitnami/spark
COPY --from=build /usr/src/app/target/aicplauncher.jar /usr/app/aicplauncher.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/aicplauncher.jar"]