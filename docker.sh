#!/bin/bash
set -x
time=$(date "+%Y%m%d%H")

for path in `ls`
do
    if [ -f $path/dockerfile ]; then
        docker build -t $path:$time $path
        docker tag $path:$time registry.cn-hongkong.aliyuncs.com/acm-recentcontests/$path:$time
        docker push registry.cn-hongkong.aliyuncs.com/acm-recentcontests/$path:$time
    fi    
done