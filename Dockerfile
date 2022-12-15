 
FROM amazoncorretto:11 
ARG JAR_FILE=build/libs/nftfont-0.0.1.jar
COPY ${JAR_FILE} app.jar 
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-Dcom.amazonaws.sdk.disableEc2Metadata=true","-jar","app.jar"] 
