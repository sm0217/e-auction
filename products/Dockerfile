FROM openjdk:11
VOLUME /tmp
EXPOSE 8081
ADD /build/libs/products-0.0.1-SNAPSHOT.jar products-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","products-0.0.1-SNAPSHOT.jar"]