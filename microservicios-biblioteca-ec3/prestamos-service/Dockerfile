FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml pom.xml
COPY eureka-server/pom.xml eureka-server/pom.xml
COPY config-server/pom.xml config-server/pom.xml
COPY libros-service/pom.xml libros-service/pom.xml
COPY prestamos-service/pom.xml prestamos-service/pom.xml
RUN mvn -B -q -pl prestamos-service -am dependency:go-offline
COPY eureka-server/src eureka-server/src
COPY config-server/src config-server/src
COPY libros-service/src libros-service/src
COPY prestamos-service/src prestamos-service/src
RUN mvn -B -q -pl prestamos-service -am clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/prestamos-service/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
