## Processing Kafka messages with Spark

## Prerequisites

- Having provisioned one of the Vagrant 3 nodes cluster or Vagrant single node cluster [instructions here](../02-Provision_the_environment/README.md) 
- Kafka and Zookeeper processes must be started using Ambari 
- Kafka Tools installed on the PC (see lab 16 - Kafka partition replication)


Create the topic

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-topics.sh \
    --create \
    --topic my-spark-topic \
    --zookeeper node1.example.com:2181,node2.example.com:2181,node3.example.com:2181 \
    --partitions 1 \
    --replication-factor 1
```

Run the application locally

```console
$ mvn package && mvn exec:exec -Dspark.master=local
-------------------------------------------
Batch: 0
-------------------------------------------
+---+-----+-----+---------+------+---------+-------------+
|key|value|topic|partition|offset|timestamp|timestampType|
+---+-----+-----+---------+------+---------+-------------+
+---+-----+-----+---------+------+---------+-------------+
```

Publish a message on Kafka

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-producer.sh \
    --request-required-acks all \
    --topic my-spark-topic \
    --broker-list node1.example.com:6667,node2.example.com:6667,node3.example.com:6667
```

You should see in the application standard output

```console
-------------------------------------------
Batch: 0
-------------------------------------------
+---+-----+-----+---------+------+---------+-------------+
|key|value|topic|partition|offset|timestamp|timestampType|
+---+-----+-----+---------+------+---------+-------------+
+---+-----+-----+---------+------+---------+-------------+

-------------------------------------------
Batch: 1
-------------------------------------------
+----+----------------+--------------+---------+------+-----------------------+-------------+
|key |value           |topic         |partition|offset|timestamp              |timestampType|
+----+----------------+--------------+---------+------+-----------------------+-------------+
|null|[68 65 6C 6C 6F]|my-spark-topic|0        |0     |2020-11-20 02:13:41.442|0            |
+----+----------------+--------------+---------+------+-----------------------+-------------+

-------------------------------------------
Batch: 2
-------------------------------------------
+----+----------------------------------------------------+--------------+---------+------+-----------------------+-------------+
|key |value                                               |topic         |partition|offset|timestamp              |timestampType|
+----+----------------------------------------------------+--------------+---------+------+-----------------------+-------------+
|null|[69 73 20 61 6E 79 62 6F 64 79 20 74 68 65 72 65 3F]|my-spark-topic|0        |1     |2020-11-20 02:13:59.181|0            |
+----+----------------------------------------------------+--------------+---------+------+-----------------------+-------------+
```

Run the application in the cluster

```
[vagrant@node1 ~]$ spark-submit \
                     --master yarn \
                     --driver-memory 512m  \
                     --executor-memory 512m \
                     --class it.sunnyvale.academy.sparkkafka.Main \
                     --deploy-mode cluster \
                     target/spark-kafka-1.0-SNAPSHOT.jar
```

