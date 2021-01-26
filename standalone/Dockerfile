FROM hub.vipkid.com.cn/devops/centos6.8-jdk1.8.0:v1.7

MAINTAINER hezhengkui hezhengkui@vipkid.com.cn

ENV LANG zh_CN.UTF-8
ENV LANGUAGE zh_CN.UTF-8
ENV LC_ALL zh_CN.UTF-8

ENV JAVA_HOME /usr/local/java
ENV PATH $PATH:$JAVA_HOME/bin

RUN mkdir -p /opt/vipkid/application

WORKDIR /opt/vipkid/application

COPY ./target/application.jar /opt/vipkid/application
ENTRYPOINT ["java","-jar","/opt/vipkid/application/application.jar"]
