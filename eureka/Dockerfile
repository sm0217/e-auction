FROM gradle:7.5.1-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11
VOLUME /tmp
EXPOSE 8761
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/eureka-0.0.1-SNAPSHOT.jar /app/eureka-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/app/eureka-0.0.1-SNAPSHOT.jar"]