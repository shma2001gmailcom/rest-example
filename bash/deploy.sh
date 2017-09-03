#!/bin/bash

appname="rest-example"
appfolder="/home/misha/workspace/"${appname}
tomcatfolder="/home/misha/workspace/tomcat6"
M2_HOME='/opt/apache-maven-3.2.2/'
export M2_HOME
M2=${M2_HOME}/bin
export M2
PATH=${PATH}:${M2}
export PATH
logfile='./1'

################################################
#export JAVA_HOME=${javahome}
tomcatbin=${tomcatfolder}/bin
tomcatwebapps=${tomcatfolder}/webapps
if [ ! -e ${appfolder} ]; then echo 'ERROR: no appfolder' ${appfolder} 'found';exit 1; fi
if [ ! -e ${tomcatbin} ]; then echo 'ERROR: no tomcatbin found';exit 1; fi
cd ${appfolder}
mvn clean install $@ | tee out.txt ; test ${PIPESTATUS[0]} -eq 0
if [ ${PIPESTATUS[0]} -ne "0" ]; then
    echo ===================================================
    echo maven build failed, see output for details;exit 1;
    echo ===================================================
fi
cd ${tomcatbin}
if [ "$(ps axf | grep catalina | grep -v grep)" ]; then
    echo ///////////////////////////
    echo        stopping tomcat...
    bash shutdown.sh
    sleep 5
    echo ///////////////////////////
    echo        tomcat has been stopped
fi
cd ${tomcatwebapps}
if [ -e ${tomcatwebapps}/${appname} ]; then
    echo ///////////////////////////
    echo      remove old deployment...
    rm -rf -- ${tomcatwebapps}/${appname}
fi
if [ -e  ${tomcatwebapps}/${appname}.war ]; then
    echo ///////////////////////////
    echo      remove old war...
    rm -rf -- ${tomcatwebapps}/${appname}.war
fi
echo ///////////////////////////
echo      deploying new version...
cp  ${appfolder}/target/${appname}.war ${tomcatwebapps}/${appname}.war
cd ${tomcatbin}
echo ///////////////////////////
echo         starting tomcat...
echo ///////////////////////////
bash startup.sh
sleep 5
chromium-browser "http://localhost:8080/"${appname}/rest/UserService/users
