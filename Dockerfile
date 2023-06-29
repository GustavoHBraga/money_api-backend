FROM maven:3.8.5 AS buildStage
RUN mkdir /opt/money_api
WORKDIR /opt/money_api
COPY . /opt/money_api
RUN mvn clean -DskipTests install

FROM openjdk:17-alpine
COPY --from=buildStage  /opt/money_api/target/*.war app.war
ENTRYPOINT java -jar "app.war"
EXPOSE 8080