FROM gradle:7.5.1-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11
VOLUME /tmp
EXPOSE 8087
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/sales-service-1.0.0.jar /app/sales-service-1.0.0.jar
ENTRYPOINT ["java","-jar","/app/sales-service-1.0.0.jar"]