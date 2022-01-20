## Prerequisites

- Having provisioned one of the following Ambari clusters:
    - Vagrant single node cluster [instructions here](../02-Provision_the_environment/Vagrant_single_node/README.md)
    - Vagrant 3 nodes cluster [instructions here](../02-Provision_the_environment/Vagrant/README.md)
- Kafka and Zookeeper processes must be started using Ambari

Commands in this README are taken from a 3 nodes cluster, if you run the same wizard on a single node cluster the syntax  might change slightly.


Open two terminals on node1 and node2 VMs. The following commands will report in the shell prompt which VM must be run from.

Alternatively, you can use only one VM (node1 for example), just make sure to modify each command accordingly.


List brokers:

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/zookeeper-shell.sh localhost:2181 ls /brokers/ids
Connecting to localhost:2181

WATCHER::

WatchedEvent state:SyncConnected type:None path:null
[1003, 1002, 1001]
```

Create a single-partition, non replicated topic

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-topics.sh \
    --create \
    --topic mytopic \
    --zookeeper node1.example.com:2181,node2.example.com:2181,node3.example.com:2181 \
    --partitions 1 \
    --replication-factor 1
Created topic "mytopic".
```

Describe the topic

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-topics.sh \
    --describe \
    --topic mytopic \
    --zookeeper node1.example.com:2181,node2.example.com:2181,node3.example.com:2181
Topic:mytopic   PartitionCount:1        ReplicationFactor:1     Configs:
        Topic: mytopic  Partition: 0    Leader: 1001    Replicas: 1001  Isr: 1001
```

List all the topics

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-topics.sh \
    --list \
    --zookeeper node1.example.com:2181,node2.example.com:2181,node3.example.com:2181
mytopic
```

From node2, start consuming messages:

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-console-consumer.sh \
    --topic mytopic \
    --bootstrap-server node1.example.com:6667
``` 


From node1, send messages:

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-console-producer.sh \
    --topic mytopic \
    --broker-list node1.example.com:6667,node2.example.com:6667,node3.example.com:6667
>ciao
>mondo
^C
[vagrant@node1 ~]$ 
``` 

On node2, messages have been received successfully:

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-console-consumer.sh --topic mytopic --bootstrap-server node1.example.com:6667
ciao
mondo
``` 

Delete the topic

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.4.0-315/kafka/bin/kafka-topics.sh \
    --delete \
    --topic mytopic \
    --zookeeper node1.example.com:2181,node2.example.com:2181,node3.example.com:2181
Topic mytopic is marked for deletion.
Note: This will have no impact if delete.topic.enable is not set to true.
```

