FROM registry.cn-hangzhou.aliyuncs.com/hezkvectory/java:v1.8

MAINTAINER hezkvectory hezkvectory@163.com

ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8

ENV JAVA_HOME /usr/local/java
ENV CLASSPATH .:${JAVA_HOME}/lib/tools.jar:${JAVA_HOME}/lib/dt.jar
ENV PATH $PATH:$JAVA_HOME/bin

RUN mkdir -p /opt/hezkvectory/application

WORKDIR /opt/hezkvectory/application

COPY ./target/application.jar /opt/hezkvectory/application
ENTRYPOINT ["java","-jar","$JAVA_OPTS","/opt/hezkvectory/application/application.jar"]

