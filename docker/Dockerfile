FROM maven:3.9.9-eclipse-temurin-21-jammy AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

 #Runtime Stage
FROM amazoncorretto:21
 #define few things \
WORKDIR /app
COPY --from=build /build/target/ED-Tech-*-SNAPSHOT.jar ED-Tech-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "ED-Tech-0.0.1-SNAPSHOT.jar"]
