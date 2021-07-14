FROM registry.cn-hangzhou.aliyuncs.com/hezkvectory/java:v1.8.1

MAINTAINER hezhengkui hezkvectory@163.com

ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8

ENV JAVA_HOME /usr/local/java
ENV PATH $PATH:$JAVA_HOME/bin

RUN mkdir -p /opt/test/application

WORKDIR /opt/test/application

COPY ./target/application.jar /opt/test/application
ENTRYPOINT ["java","-jar","/opt/test/application/application.jar"]
