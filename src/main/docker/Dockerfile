FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD jenkins-api-launcher-0.1.0.jar app.jar
RUN sh -c 'touch /app.jar'
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]