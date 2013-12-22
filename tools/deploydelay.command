#!/bin/sh

rm -r /Volumes/brettmelton/apache-tomcat-7.0.29/webapps/chadwickfarms

rm -r "/Volumes/brettmelton/apache-tomcat-7.0.29/work/Catalina/www.chadwickfarms.net"

rm /Volumes/brettmelton/apache-tomcat-7.0.29/webapps/chadwickfarms.war

echo  
echo  
echo Creating WAR File
echo  
echo  

cd ~/Projects/chadwickfarms/app
pwd

jar -cvf ../deploy/chadwickfarms.war ./WEB-INF -C ../web .
cd ~/Projects/chadwickfarms/deploy
pwd

echo sleeping 15 seconds
sleep 15

cp chadwickfarms.war /Volumes/brettmelton/apache-tomcat-7.0.29/webapps

cd ~/Projects/chadwickfarms/tools
pwd

exit
