FROM registry.cn-hangzhou.aliyuncs.com/hezkvectory/centos6.8:v1

MAINTAINER hezhengkui hezkvectory@163.com

ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8

ENV JAVA_HOME /usr/local/java
ENV PATH $PATH:$JAVA_HOME/bin

ENV SW_AGENT_NAME account-service
ENV DSW_AGENT_COLLECTOR_BACKEND_SERVICES ubuntu:11800

RUN mkdir -p /opt/hezkvectory/application

WORKDIR /opt/hezkvectory/application

COPY ./target/application.jar /opt/hezkvectory/application
ENTRYPOINT ["java","-jar", "/opt/hezkvectory/application/application.jar"]
