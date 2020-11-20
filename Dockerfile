FROM openjdk:8
ADD target/user_movie-0.0.1-SNAPSHOT.jar user_movie-0.0.1-SNAPSHOT.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "user_movie-0.0.1-SNAPSHOT.jar"]