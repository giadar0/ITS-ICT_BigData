# Apache Nifi MQTT to Kafka

## Prerequisites

- Having provisioned one of the Vagrant 3 nodes cluster or Vagrant single node cluster [instructions here](../02-Provision_the_environment/README.md) 
- Kafka and Zookeeper processes must be started using Ambari 
- Having connected to node1 

```
$ vagrant ssh node1
```

## Kafka preparation

Create Kafka topic

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-topics.sh \
    --create \
    --topic nifi \
    --zookeeper localhost:2181 \
    --partitions 1 \
    --replication-factor 1
```

Describe the topic to see if everything is ok

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-topics.sh  \
    --describe \
    --topic nifi \
    --zookeeper localhost:2181
Topic:nifi      PartitionCount:1        ReplicationFactor:1     Configs:
        Topic: nifi     Partition: 0    Leader: 1001    Replicas: 1001  Isr: 1001
```

## MQTT preparation

Start the mosquitto service

```console
[vagrant@node1 ~]$ sudo service mosquitto start
```

## Nifi configuration

Start Nifi

```console
[vagrant@hdp-singlenode ~]$ sudo service nifi start
nifi.sh: JAVA_HOME not set; results may vary

Java home: 
NiFi home: /opt/nifi-1.12.1

Bootstrap Config File: /opt/nifi-1.12.1/conf/bootstrap.conf
```

Open you browser to [http://localhost:8090/nifi/](http://localhost:8090/nifi/) (port 8090 on localhost should be redirected to port 8090 on node1 VM).

![](img/1.png)

Add a **ConsumeMQTT** processor

![](img/2.png)

Click Add to add it to the flow

![](img/3.png)

Double click on it and fill the following properties:

- Broker URI: tcp://localhost
- Topic Filter: test
- Quality of Service: 0 - At most once
- Max Queue Size: 20

![](img/4.png)

Add a **PublishKafka_2_0** processor

![](img/2.png)

Click Add to add it to the flow

![](img/4b.png)

Double click on it and fill:

in the PROPERTIES sections the following properties

- Kafka Brokers: node1.example.com:6667 (or the list of nodes where Kafka brokers reside)
- Topic Name: nifi
- Delivery Guarantee: Best Effort
- Use Transaction: false

in the SETTINGS screen flag both "success" and "failure" under **Automatically Terminate Relationships**

![](img/5.png)


Then, wire the two together by tragging an arrow from ConsumerMQTT to PublishKafka_2_0, a new configuration windows opens: just click on Add.

![](img/6.png)

You should now see a flow as the following:

![](img/7.png)

Start the flow by clicking on the start button

![](img/8.png)

The flow has started

![](img/9.png)



## Publishing to MQTT

Publish a MQTT message 

```
[vagrant@node1 ~]$ mosquitto_pub -h localhost -t test -m "test 123"
```

## Consume from Kafka

Start consuming Kafka messages

```
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-console-consumer.sh \
    --topic nifi \
    --from-beginning \
    --bootstrap-server $(hostname):6667
test 123
```

You should have received the message "test 123" to Kafka routed by NiFi. 