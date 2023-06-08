FROM openjdk:11-ea-jre
VOLUME /tmp
COPY build/libs/sform-service-1.0.jar SformService.jar
ENTRYPOINT ["java", "-jar", "SformService.jar"]
