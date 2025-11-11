@echo off
setlocal
set TOMCAT_HOME=C:\Tomcat10
echo === Building WAR ===
call mvn clean package -DskipTests

echo === Deploying WAR to Tomcat ===
copy target\CineManProject.war "%TOMCAT_HOME%\webapps\" /Y

echo === Restarting Tomcat ===
call "%TOMCAT_HOME%\bin\shutdown.bat"
timeout /t 3 >nul
call "%TOMCAT_HOME%\bin\startup.bat"

echo === Done! Access: http://localhost:8080/CineManProject ===
pause
