# Kafka partition replication

## Prerequisites

- Having provisioned the Vagrant 3 nodes cluster [instructions here](../02-Provision_the_environment/Vagrant/README.md) Ambari cluster
- Kafka and Zookeeper processes must be started using Ambari on all hosts
- Kafka Tools installed on the PC (see lab 16 - Kafka partition replication)

Open two terminals on node1, node2, node3 VMs. The following commands will report in the shell prompt which VM must be run from.

## Create a partitioned topic

Create a partitioned topic

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-topics.sh \
    --create \
    --topic my-partitioned-topic \
    --zookeeper node1.example.com:2181,node2.example.com:2181,node3.example.com:2181 \
    --partitions 3 \
    --replication-factor 1
Created topic "my-partitioned-topic".
```

This topic has been splitted in 3 partitions:

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-topics.sh \
    --describe \
    --topic my-partitioned-topic \
    --zookeeper node1.example.com:2181,node2.example.com:2181,node3.example.com:2181
Topic:my-partitioned-topic      PartitionCount:3        ReplicationFactor:1     Configs:
        Topic: my-partitioned-topic     Partition: 0    Leader: 1003    Replicas: 1003  Isr: 1003
        Topic: my-partitioned-topic     Partition: 1    Leader: 1001    Replicas: 1001  Isr: 1001
        Topic: my-partitioned-topic     Partition: 2    Leader: 1002    Replicas: 1002  Isr: 1002
```

Observe that each partition has a different leader (3 partitions, 3 brokers).

## Produce messages to the partition 

Produce 3 messages

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-producer.sh \
    --request-required-acks all \
    --topic my-partitioned-topic \
    --broker-list node1.example.com:6667,node2.example.com:6667,node3.example.com:6667
>message 1
>message 2
>message 3
>^C
[vagrant@node1 ~]$
```

## Consume messages partition by partition

If you consume messages from a one partition at a time, you observe that message we published earlier have been sent to all the three partitions

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
    --topic my-partitioned-topic \
    --from-beginning \
    --partition 0 \
    --bootstrap-server node1.example.com:6667
message 2
^C
Processed a total of 1 messages
```

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
    --topic my-partitioned-topic \
    --from-beginning \
    --partition 1 \
    --bootstrap-server node1.example.com:6667
message 1
^C
Processed a total of 1 messages
```

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
    --topic my-partitioned-topic \
    --from-beginning \
    --partition 2 \
    --bootstrap-server node1.example.com:6667
message 3
^C
Processed a total of 1 messages
```

## Start multiple consumers

Start the first consumer:

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
    --topic my-partitioned-topic \
    --bootstrap-server node1.example.com:6667

```

And by opening a new terminal, start a new consumer

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
    --topic my-partitioned-topic \
    --bootstrap-server node1.example.com:6667

```

If you publish a message


```console
[vagrant@node3 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-producer.sh \
    --request-required-acks all \
    --topic my-partitioned-topic \
    --broker-list node1.example.com:6667,node2.example.com:6667,node3.example.com:6667
>message 4
>^C
[vagrant@node3 ~]$
```

you should see that both consumers received it:

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
      --topic my-partitioned-topic \
      --bootstrap-server node1.example.com:6667
message 4
```

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
      --topic my-partitioned-topic \
      --bootstrap-server node1.example.com:6667
message 4
```

Different Kafka cosumers subscribed to the same topic reveive all the messages.


## Start multiple consumers in a single group 

Start the first consumer:

```console
[vagrant@node1 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
    --topic my-partitioned-topic \
    --group my-consumer-group \
    --bootstrap-server node1.example.com:6667

```

And by opening a new terminal, start a new consumer in the same group

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
    --topic my-partitioned-topic \
    --group my-consumer-group \
    --bootstrap-server node1.example.com:6667

```

If you publish a message


```console
[vagrant@node3 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-producer.sh \
    --request-required-acks all \
    --topic my-partitioned-topic \
    --broker-list node1.example.com:6667,node2.example.com:6667,node3.example.com:6667
>message 5
>^C
[vagrant@node3 ~]$
```

you should see that only one consumer received it (in my case the one started in node2):

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
    --topic my-partitioned-topic \
    --group my-consumer-group \
    --bootstrap-server node1.example.com:6667
message 5
```

Kafka cosumers within the same group receive only one copy of the message (load balancing)

## Consume messages from offset

When you start a consumer, it start receiving messages from that point in time onwards.

If you want to receive messages from the beginning for a particular topic

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
     --topic my-partitioned-topic \
     --from-beginning \
     --bootstrap-server node1.example.com:6667
message 2
message 3
message 4
message 1
message 5
```

Messages are not ordered since the have been spread to all the topic's partitions (ordering between different partitions is not guaranteed)

If you want to receive messages from a specific offset of a single partition

```console
[vagrant@node2 ~]$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-consumer.sh \
     --topic my-partitioned-topic \
     --offset 1 \
     --partition 1 \
     --bootstrap-server node1.example.com:6667
message 4
```
