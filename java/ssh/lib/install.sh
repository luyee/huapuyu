#!/bin/bash

mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.4.0 -Dpackaging=jar -Dfile=ojdbc14-10.2.0.4.0.jar -DgeneratePom=true