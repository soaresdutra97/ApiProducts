FROM openjdk:17-jdk

WORKDIR /app

COPY build/libs/FakeApiPrducts-0.0.1-SNAPSHOT.jar /app/FakeApiPrducts.jar

EXPOSE 8181

CMD ["java", "-jar", "/app/FakeApiPrducts.jar"]

