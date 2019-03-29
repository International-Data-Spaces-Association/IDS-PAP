FROM frolvlad/alpine-oraclejre8:slim
VOLUME /tmp
COPY ./mydata.odrlpap.webservice.war mydata.odrlpap.webservice.war
RUN sh -c 'touch /mydata.odrlpap.webservice.war'
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar /mydata.odrlpap.webservice.war" ]
