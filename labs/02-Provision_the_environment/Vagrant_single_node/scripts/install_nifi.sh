export NIFI_VERSION=1.12.1

rm -rf /tmp/apache-nifi 
mkdir /tmp/apache-nifi -p
cd /tmp/apache-nifi
wget ftp://mirror.nohup.it/apache/nifi/${NIFI_VERSION}/nifi-${NIFI_VERSION}-bin.tar.gz 


tar -zxf nifi-${NIFI_VERSION}-bin.tar.gz 
rm -rf /opt/nifi*
sudo mv nifi-${NIFI_VERSION} /opt/
sudo ln -s "/opt/nifi-${NIFI_VERSION}" /opt/nifi
cd /opt/nifi
sudo bin/nifi.sh install
perl -p -i -e 's/8080/8090/g' /opt/nifi-${NIFI_VERSION}/conf/nifi.properties

