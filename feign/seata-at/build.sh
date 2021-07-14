#!/bin/zsh

services=(account bussiness order storage)
#services=(bussiness order)

version=v6

for service in ${services[@]}; do
  docker build -t ${service}:${version} ./${service}
  docker tag ${service}:${version} registry.cn-hangzhou.aliyuncs.com/hezkvectory/${service}:${version}
  docker push registry.cn-hangzhou.aliyuncs.com/hezkvectory/${service}:${version}
  docker rmi  registry.cn-hangzhou.aliyuncs.com/hezkvectory/${service}:${version}
  docker rmi  ${service}:${version}
done

