FROM openjdk:8-jre-slim
VOLUME /tmp
COPY ./mydata.odrlpap.webservice.war mydata.odrlpap.webservice.war
RUN sh -c 'touch /mydata.odrlpap.webservice.war'
EXPOSE 9090
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /mydata.odrlpap.webservice.war" ]
