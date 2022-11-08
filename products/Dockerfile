FROM gradle:7.5.1-jdk11-alpine AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM adoptopenjdk/openjdk11
VOLUME /tmp
EXPOSE 8081
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/products-1.0.0.jar /app/products-1.0.0.jar
ENTRYPOINT ["java","-jar","/app/products-1.0.0.jar"]