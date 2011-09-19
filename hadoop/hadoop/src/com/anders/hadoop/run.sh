#!/bin/bash

export HADOOP_CLASSPATH=/home/anders/code/java/hadoop/bin
nohup hadoop com/anders/hadoop/NewMaxTemperature /home/anders/code/java/hadoop/bin/com/anders/hadoop/sample.txt output &
