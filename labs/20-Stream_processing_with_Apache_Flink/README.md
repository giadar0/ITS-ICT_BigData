# Stream processing with Apache Flink

## Prerequisites

- Having provisioned one of the Vagrant 3 nodes cluster or Vagrant single node cluster [instructions here](../02-Provision_the_environment/README.md) 
- Having connected to node1 

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
