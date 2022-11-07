FROM openjdk:11
VOLUME /tmp
EXPOSE 8081
ADD /build/libs/products-1.0.0.jar products-1.0.0.jar
ENTRYPOINT ["java","-jar","products-1.0.0.jar"]