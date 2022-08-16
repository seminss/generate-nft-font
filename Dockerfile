
FROM amazoncorretto:11
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENV JAVA_OPTS=""
CMD java $JAVA_OPTS -server -jar -Dspring.profiles.active=dev -Duser.timezone="Asia/Seoul" app.jar