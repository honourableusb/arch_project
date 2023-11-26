FROM tomcat:11.0.0-jdk21-temurin

RUN mkdir /usr/local/bin && \
    mkdir /usr/local/tomcat/webapps/search
COPY artifacts /