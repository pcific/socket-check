#!/bin/sh

JAVA_HOME=/app/jdk170

#JAVA_OPTS="${JAVA_OPTS} -Dsocket.woo.server.port=55555"   
#JAVA_OPTS="${JAVA_OPTS} -Dsocket.woo.server.sotimeout=90000"
#JAVA_OPTS="${JAVA_OPTS} -Dsocket.woo.logstdout=false"
#JAVA_OPTS="${JAVA_OPTS} -Dsocket.woo.logfile=true"


$JAVA_HOME/bin/java -classpath socket-woo.jar ${JAVA_OPTS} socket.woo.Server 

