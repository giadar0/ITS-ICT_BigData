# Stream processing with Apache Flink

## Prerequisites

- Having provisioned one of the Vagrant 3 nodes cluster or Vagrant single node cluster [instructions here](../02-Provision_the_environment/README.md) 
- Having connected to node1 
- YARM, MapReduce2, HDFS and Zookeeper must be running __BEFORE__ to install Flink

```
$ vagrant ssh node1
```

## Flink installation

After having logged in into Ambari using the web UI, follow the visual instructions listed below:

Click on Add Service
![](./img/1.png)

Select **Flink** and click Next
![](./img/2.png)

Select one node to host Apache Flink then click Next
![](./img/3.png)

Accept all the defaults and click Next
![](./img/4.png)

Aknowledge the warnings and click Proceed anyway
![](./img/5.png)

Click Deploy
![](./img/6.png)

Remember to restart all the services if needed

In the case of trouble, Flink installation/runtime logs are available at

```console
[vagrant@node1 ~]$ tail -500f /var/log/flink/flink-setup.log
...
```

## Testing Flink installation

```console
[vagrant@node1 ~]$ sudo su - flink
[flink@node1 ~]$ cd /opt/flink
[flink@node1 ~]$ export HADOOP_CLASSPATH=`hadoop classpath`
[flink@node1 ~]$ export HADOOP_CONF_DIR=/etc/hadoop/conf
[flink@node1 ~]$ ./bin/flink run ./examples/streaming/TopSpeedWindowing.jar
```

You might access the Flink Web Interface at http://localhost:8081 (or substitute **localhost** with the hostname where Flink has been started)

YARN Web Interface is accessible at http://localhost:8088/ (or substitute **localhost** with the hostname where YARN Web Interface  has been started)

In the case you need to kill applications submitted to YARN:

```console
[vagrant@node1 ~]$ yarn application -kill <application ID>
```