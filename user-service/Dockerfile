FROM openjdk:11-ea-jre
VOLUME /tmp
COPY build/libs/user-service-1.0.jar UserService.jar
ENTRYPOINT ["nohup", "java", "-jar", "UserService.jar"]
