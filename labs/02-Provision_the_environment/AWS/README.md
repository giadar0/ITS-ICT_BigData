# AWS Elastic Map Reduce

This procedure describes the necessary steps to create an Elastic Map Reduce cluster on Amazon Web Service (a valid AWS subscription is required).

## Prerequisites

- A valid AWS subscription
- The AWS CLI installed 
- Valid **Access key ID** and **Secret access key** to configure the CLI
    
As a general reference, please have a look of the official [AWS CLI documentation](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-quickstart.html)

## Configure AWS CLI

```console
$ aws configure
AWS Access Key ID [None]: <YOUR ACCESS KEY ID>
AWS Secret Access Key [None]:  <YOUR SECRET ACCESS KEY>
Default region name [None]: eu-west-1
Default output format [None]:
```

## Create a key pair

Create a key pair

```console
$ aws ec2 create-key-pair \
    --key-name my-key-pair \
    --key-type rsa \
    --query "KeyMaterial" \
    --output text > my-key-pair.pem
```

## AWS default roles creation
```console
$ aws emr create-default-roles
...
```

## Bucket creation

Create a bucket to store both the cluster's logs, applications and the input datasets

```console
$ aws s3api create-bucket --bucket its-ict-emr-bucket --region eu-west-1 --create-bucket-configuration LocationConstraint=eu-west-1
{
    "Location": "http://its-ict-emr-bucket.s3.amazonaws.com/"
}
```

## Cluster provisioning 

As a general reference, please have a look of the official [AWS CLI for EMR documentation](https://docs.aws.amazon.com/cli/latest/reference/emr/index.html)

```console
$ aws emr create-cluster \
    --name "my-lab-cluster" \
    --release-label "emr-5.33.0" \
    --applications Name=Spark Name=Hadoop Name=Pig \
    --ec2-attributes "KeyName=my-key-pair" \
    --instance-type m5.xlarge \
    --instance-count 1 \
    --use-default-roles \
    --log-uri s3://its-ict-emr-bucket/emr_logs/ \
    --enable-debugging

{
    "ClusterId": "j-2PWO1SLI4994A",
    "ClusterArn": "arn:aws:elasticmapreduce:eu-west-1:238764214477:cluster/j-2PWO1SLI4994A"
}
```

Check your cluster status with the following command.

```console
$ aws emr describe-cluster --cluster-id  "j-2PWO1SLI4994A"

{
    "Cluster": {
        "Id": "myClusterId",
        "Name": "My First EMR Cluster",
        "Status": {
            "State": "STARTING",
            "StateChangeReason": {
                "Message": "Configuring cluster software"
            },
            ...
        },
		...
	}
{
```

## List EMR clusters

```console
$ aws emr list-clusters
{
    "Clusters": [
        {
            "Id": "j-2PWO1SLI4994A",
            "Name": "my-lab-cluster",
            "Status": {
                "State": "STARTING",
                "StateChangeReason": {
                    "Message": "Configuring cluster software"
                },
                "Timeline": {
                    "CreationDateTime": "2021-09-15T17:24:06.364000+02:00"
                }
            },
            "NormalizedInstanceHours": 0,
            "ClusterArn": "arn:aws:elasticmapreduce:eu-west-1:238764214477:cluster/j-2PWO1SLI4994A"
        }
    ]
}
...
```

## Delete a cluster

In the case you want to delete the cluster 

```console
$ aws emr terminate-clusters --cluster-ids "j-2PWO1SLI4994A"
```
