Virtual machine with vagrant
vagrant up

BUILD (not inside VM)
cd /vagrant
mvn clean package tomcat:redeploy

TOMCAT directory (VM)
/usr/share/tomcat6/

TESTS
curl -v http://localhost:5080/Service/message/2


export JAVA_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5081"

