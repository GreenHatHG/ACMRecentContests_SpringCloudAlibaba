#!/bin/bash

# -----------------install父类依赖--------------------- 
ip=$1
pwd=$2

for arg; 
do
    if [ $arg==$ip ]||[ $arg==$pwd ];
    then
        continue
    fi
    mvn clean install -f $arg/pom.xml
done

# -----------------创建dockerfile--------------------- 
# $1:项目名，$2:jar的相对路径
createDokcerfiler(){
    touch $1/dockerfile
    echo 'FROM openjdk:8-alpine' >> $1/dockerfile
    echo 'RUN mkdir /app' >> $1/dockerfile
    echo 'COPY '$1'/target/'$2 ' /app/' >> $1/dockerfile
    echo 'CMD java -jar /app/'$2 >> $1/dockerfile
}

# -----------------打包--------------------- 
baseFolder='/root/spring-cloud-alibaba/'

for path in `ls`
do
    flag=1
    # 判断是否是父类依赖，是的话则直接跳过
    for arg;
    do
        if [ $path = $arg ]; then
            flag=0
            break
        fi    
    done    
    # 判断是不是文件夹
    if [ $flag == 1 ]&&[ -d $path/target ]; then
        mvn clean package -f $path/pom.xml
        # 创建文件夹用来存放jar
        sshpass -p $pwd ssh -o StrictHostKeyChecking=no root@$ip 'mkdir -p '${baseFolder}${path} || true
        sshpass -p $pwd scp $path/target/*.jar root@$ip:${baseFolder}${path}
        jarPath=`ls $path/target | grep jar`
        createDokcerfiler $path $jarPath
    fi
done

