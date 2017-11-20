export JAVA_OPTS="-DCOUCH_DB_SERVER=doc-machine"
/Library/Tomcat/bin/shutdown.sh
rm -f /Library/Tomcat/webapps/booker.war
cp /Users/amrish/Work/Training/Docker2/booker/build/libs/Booker-0.1.war /Library/Tomcat/webapps/booker.war
rm /Library/Tomcat/logs/*
/Library/Tomcat/bin/startup.sh 
