FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/CourseService-*.jar /app/course-service.jar

RUN ["chmod", "+x", "/app/course-service.jar"]

CMD ["java", "-jar", "/app/course-service.jar"]