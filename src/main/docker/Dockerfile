FROM openjdk:11-jre-slim
VOLUME /tmp
COPY ./mydata.odrlpap.webservice.jar mydata.odrlpap.webservice.jar
RUN sh -c 'touch /mydata.odrlpap.webservice.jar'
EXPOSE 9090
EXPOSE 3000
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /mydata.odrlpap.webservice.jar" ]
