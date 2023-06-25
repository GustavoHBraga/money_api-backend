FROM maven:3.8.5 as buildStage
RUN mkdir /opt/money_api
WORKDIR /opt/money_api
COPY . /opt/money_api
RUN mvn clean -DskipTests install

FROM openjdk:17-alpine
COPY --from=buildStage  /opt/money_api/target/*.jar app.jar
ENTRYPOINT java -jar "app.jar"
EXPOSE 8080