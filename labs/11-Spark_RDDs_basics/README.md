
## Spark RDDs (Resilient Distributed Datasets) basics

This lab contains multiple examples about the basics of RDDs (Resilient Distributed Datasets).

Here's a list about the example included:

- Map + Collect (example name: MapExample, fully qualified main class: it.sunnyvale.academy.sparkrddsbasics.transformation.MapExample)
- FlatMap + Collect  (example name: FlatMapExample, fully qualified main class: it.sunnyvale.academy.sparkrddsbasics.transformation.FlatMapExample)
- Filter + Collect  (example name: FilterExample, fully qualified main class: it.sunnyvale.academy.sparkrddsbasics.transformation.FilterExample)
- FlatMap + CountByValue (example name: WordCount, fully qualified main class: it.sunnyvale.academy.sparkrddsbasics.WordCount)
- Distinct + Collect  (example name: DistinctExample, fully qualified main class: it.sunnyvale.academy.sparkrddsbasics.transformation.DistinctExample)
- Join (example name: JoinExample, fully qualified main class: it.sunnyvale.academy.sparkrddsbasics.transformation.JoinExample)
- Reduce (example name: ReduceExample, fully qualified main class: it.sunnyvale.academy.sparkrddsbasics.transformation.ReduceExample)


## Prerequisites

To run the examples on your local machine, the following prerequisites must be met:

- JDK 1.8 or above must be installed
- Maven 3.6 or above must be installed

To run the examples on a YARN cluster, the following prerequisites must be met:

- Having completed labs 02 (Provision the environment) and 10 (Spark installation)

## Run examples locally

Compile the source code

```console
$ mvn clean &&  mvn package
```

Run the example of your choice

```console
$ mvn exec:exec \
  -Dspark.master=local \
  -P <EXAMPLE NAME>
```

Make sure to change **\<EXAMPLE NAME\>** with the real example's name before running the previous command (ie: WordCount).

## Run examples on YARN cluster

Go to path **ITS-ICT_BigData/labs/02-Provision_the_environment/Vagrant** and connect to node 1.

```console
$ vagrant ssh node1 
[vagrant@node1 ~]$ 
```

Move to this lab's home directory

```console
[vagrant@node1 ~]$ cd ITS-ICT_BigData/labs/11-Spark_RDDs_basics
```

Create the lab input directory on HDFS

```console
[vagrant@node1]$ hadoop fs -mkdir lab11_input
```

To simplify the operation of loading the input datasets on HDFS, a script has been provided. Run it with the command:

```console
[vagrant@node1]$ ./copy_input_datasets.sh 
```

Copile and package the application

```console
[vagrant@node1]$ mvn clean && mvn package
```

Submit Spark job on the cluster

```console
[vagrant@node1]$ spark-submit \
  --master yarn \
  --driver-memory 512m  \
  --executor-memory 512m \
  --class <EXAMPLE FULLY QUALIFIED CLASS NAME> \
  --deploy-mode cluster \
  target/spark-rdds-basics-1.0-SNAPSHOT.jar 
```

Make sure to change **\<EXAMPLE FULLY QUALIFIED MAIN CLASS NAME\>** with the real example's fully qualified main class name in previous command (ie: it.sunnyvale.academy.sparkrddsbasics.WordCount).

To see the job's output run

```console
[vagrant@node1]$ yarn logs -applicationId <APPLICATION ID>
```

Application ID is showed as part of the `spark-submit` command (ie: application_1601154210621_0006)

To kill an application:

```console
[vagrant@node1]$ yarn application -kill <APPLICATION ID>
```

More at https://spark.apache.org/docs/2.2.1/rdd-programming-guide.html

## Run the WordCount example on AWS EMR cluster

As a prerequisite, you should have completed the EMR cluster creation procedure on lab 2, please [refer to](../02-Provision_the_environment/AWS/README.md)

Upload the application jar in the bucket (replace bucket name with the one you used when provisioning the AWS EMR environment in lab 2)

Please change the `--bucket` value with the one representing your bucket.

```console
$ aws s3api put-object --bucket its-ict-emr-bucket --key spark-rdds-basics-1.0-SNAPSHOT.jar --body target/spark-rdds-basics-1.0-SNAPSHOT.jar
{
  "ETag" : xyz
}
```

Upload the input dataset (change the `--bucket` value with the one representing your bucket)

```console
$ aws s3api put-object --bucket its-ict-emr-bucket --key lab11_input/constitution.txt --body lab11_input/constitution.txt
{
  "ETag" : xyz
}
```

Delete the output folder in the case you already run this lab (change **its-ict-emr-bucket** with your bucket name)

```console
$ aws s3 rm --recursive s3://its-ict-emr-bucket/lab11_output/
delete: s3://its-ict-emr-bucket/lab11_output/part-00000
delete: s3://its-ict-emr-bucket/lab11_output/part-00001
delete: s3://its-ict-emr-bucket/lab11_output/_SUCCESS
```

Submit the job (change the `--cluster-id` value with the one representing your cluster and **its-ict-emr-bucket** with your bucket name)

```console
$ aws emr add-steps \
  --cluster-id "j-2PWO1SLI4994A" \
  --steps Type=Spark,Name="lab11",ActionOnFailure=CONTINUE,Args=\[--class,it.sunnyvale.academy.sparkrddsbasics.WordCount,s3://its-ict-emr-bucket/spark-rdds-basics-1.0-SNAPSHOT.jar,s3://its-ict-emr-bucket/lab11_input/constitution.txt,s3://its-ict-emr-bucket/lab11_output\]
{
    "StepIds": [
        "s-2LEM06IK66SVP"
    ]
}
```

Open the AWS console (https://s3.console.aws.amazon.com) and inspect the bucket to see if the output folder has been created and contains the result file.

