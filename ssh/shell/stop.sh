#!/bin/bash

server=192.168.2.100
anders1=192.168.2.88
anders2=192.168.2.89
anders3=192.168.2.87
t60=192.168.2.77
x60=192.168.2.90

#tomcat
tomcat=`ps -ef | grep -i 'tomcat' | grep -v 'grep' | wc -l`
#if [ $tomcat -eq 2 ]
if [ $tomcat -gt 0 ]
then
        ps -ef | grep -i 'tomcat' | grep -v 'grep' | awk '{print $2}' | xargs kill -9
        echo 'successfully killed tomcat process'
else
        echo 'no tomcat process'
fi

#mongodb
mongodb=`ps -ef | grep -i 'mongod' | grep -v 'grep' | wc -l`
#if [ $mongodb -eq 1 ]
if [ $mongodb -gt 0 ]
then
        ps -ef | grep -i 'mongod' | grep -v 'grep' | awk '{print $2}' | xargs kill -2
        rm /data/db1/mongod.lock
        rm /data/db2/mongod.lock
        rm /data/db3/mongod.lock
        rm /data/db4/mongod.lock
        echo 'successfully killed mongodb process'
else
        echo 'no mongodb process'
fi

#memcached
memcached=`ps -ef | grep -i 'memcached' | grep -v 'grep' | wc -l`
#if [ $memcached -eq 1 ]
if [ $memcached -gt 0 ]
then
        ps -ef | grep -i 'memcached' | grep -v 'grep' | awk '{print $2}' | xargs kill -9
        echo 'successfully killed memcached process'
else
        echo 'no memcached process'
fi