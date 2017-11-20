FROM ubuntu:16.04

MAINTAINER Amrish Bharatiya <bamrish@gmail.com>

RUN apt-get update && apt-get install -y openjdk-8-jdk curl

ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64

ENV CATALINA_HOME /opt/tomcat

RUN curl -O http://apache.mirrors.ionfish.org/tomcat/tomcat-8/v8.5.23/bin/apache-tomcat-8.5.23.tar.gz

RUN mkdir /opt/tomcat

RUN tar xzvf apache-tomcat-8*tar.gz -C /opt/tomcat --strip-components=1

RUN rm -rf  ${CATALINA_HOME}/webapps/examples \
			${CATALINA_HOME}/webapps/docs \
			${CATALINA_HOME}/webapps/host-manager \
			${CATALINA_HOME}/RELEASE-NOTES \
			${CATALINA_HOME}/RUNNING.txt ${CATALINA_HOME}/bin/*.bat  \
			${CATALINA_HOME}/bin/*.tar.gz

ADD build/libs/booker.war ${CATALINA_HOME}/webapps/

EXPOSE 8080

CMD /opt/tomcat/bin/catalina.sh run
