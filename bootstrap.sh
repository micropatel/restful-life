#!/usr/bin/env bash

apt-get update
apt-get install -y tomcat7
if ! [ -L /var/www ]; then
  rm -rf /var/www
  ln -fs /vagrant /var/www
fi

debconf-set-selections <<< 'mysql-server mysql-server/root_password password root'
debconf-set-selections <<< 'mysql-server mysql-server/root_password_again password root'
apt-get -y install mysql-server

echo "CREATE DATABASE stidb" | mysql --host=localhost --user=root --password=root

cp /vagrant/target/sti_services-1.0-SNAPSHOT.war /var/lib/tomcat7/webapps/sti_services.war
chmod 766 /var/lib/tomcat7/webapps/sti_services.war

wget http://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.30.tar.gz
gunzip mysql-connector-java-5.1.30.tar.gz
tar xvf mysql-connector-java-5.1.30.tar
cp mysql-connector-java-5.1.30/mysql-connector-java-5.1.30-bin.jar /usr/share/tomcat7/lib
service tomcat7 restart

