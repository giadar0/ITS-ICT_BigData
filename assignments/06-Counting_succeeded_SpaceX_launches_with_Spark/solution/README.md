# Counting succeeded SpaceX launches with Spark

Compile sources

```console
$ mvn clean && mvn package
```

Run the Main class

```console
$ mvn exec:exec
```

Run on AWS Elastic Map Reduce


Upload the java archive (change the bucket name with YOUR bucket name)

```console
$ aws s3api put-object --bucket its-ict-emr-bucket --key spacex-1.0-SNAPSHOT.jar --body target/spacex-1.0-SNAPSHOT.jar
{
  "ETag" : xyz
}
```

Upload the input dataset (change the bucket name with YOUR bucket name)


```console
$ aws s3api put-object --bucket its-ict-emr-bucket --key ass6_input/spacex_launches.csv --body ass6_input/spacex_launches.csv 
{
  "ETag" : xyz
}
```

Run the job on AWS EMR cluster

```console
$ aws emr add-steps \
  --cluster-id "$(aws emr list-clusters | grep -i Id | head -1 |  cut -d ":" -f 2 | sed -e "s/\"//g"  | sed -e "s/,//g" | sed -e "s/ //g")" \
  --steps Type=Spark,Name="Ass6",ActionOnFailure=CONTINUE,Args=\[--class,it.sunnyvale.academy.spacex.Main,s3://its-ict-emr-bucket/spacex-1.0-SNAPSHOT.jar,s3://its-ict-emr-bucket/ass6_input/spacex_launches.csv,s3://its-ict-emr-bucket/ass6_output\]
```