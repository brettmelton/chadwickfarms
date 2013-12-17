#!/bin/sh

rm -r /Volumes/tomcat/webapps/chadwickfarms

rm -r "/Volumes/tomcat/work/Catalina/www.chadwickfarms.net"

rm /Volumes/tomcat/webapps/chadwickfarms.war

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

cp chadwickfarms.war /Volumes/tomcat/webapps

cd ~/Projects/chadwickfarms/tools
pwd

exit
