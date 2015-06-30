#!/bin/bash

server=192.168.2.100
anders1=192.168.2.88
anders2=192.168.2.89
anders3=192.168.2.87
t60=192.168.2.77
x60=192.168.2.90

#tomcat
sh $TOMCAT1/bin/startup.sh
sh $TOMCAT2/bin/startup.sh
echo 'tomcat started...'

#mongodb
mongod --bind_ip $anders1 --dbpath /data/db1 --port 27017 --master &
mongod --bind_ip $anders1 --dbpath /data/db2 --port 27018 --slave --source $anders1:27017 &
mongod --bind_ip $anders1 --dbpath /data/db3 --port 27019 --slave --source $anders1:27017 &
mongod --bind_ip $anders1 --dbpath /data/db4 --port 27020 --slave --source $anders1:27017 &
echo 'mongodb started...'

#memcached
memcached -m 64 -p 11211 -c 1024 -u memcache -l $anders1 &
memcached -m 64 -p 11212 -c 1024 -u memcache -l $anders1 &
memcached -m 64 -p 11213 -c 1024 -u memcache -l $anders1 &
echo 'memcached started...'