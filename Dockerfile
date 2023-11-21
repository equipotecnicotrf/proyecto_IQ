FROM eclipse-temurin:11-alpine
MAINTAINER modusoftware.com

RUN apk update \
    && apk add busybox-extras \
    && apk add nano \
    && apk add vim \
    && apk --no-cache add curl \
    && apk add tzdata \
    && cp /usr/share/zoneinfo/America/Bogota /etc/localtime \
    && echo 'America/Bogota' > /etc/timezone \
    && mkdir /logs \
    && mkdir /config

VOLUME /config
VOLUME /logs

ENV NAME_LOG_FILE=$ARTIFACT_ID
ARG JAR_NAME

EXPOSE $WS_PORT
COPY ${JAR_NAME} /app.jar

ENTRYPOINT [ "sh", "-c", "java -jar -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=$PROFILE -Dlogging.config=/config/logback-spring.xml -Xmx256m -Xms128m /app.jar" ]