#
# Package stage
#
FROM openjdk:8
COPY target/RestaurantService-0.0.1-SNAPSHOT.jar .
EXPOSE 8040
ENTRYPOINT ["java", "-jar", "/app.jar"]