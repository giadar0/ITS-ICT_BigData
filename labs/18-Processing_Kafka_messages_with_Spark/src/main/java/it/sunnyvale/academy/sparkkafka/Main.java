package it.sunnyvale.academy.sparkkafka;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.StreamingQueryException;

import java.util.concurrent.TimeoutException;

import static org.apache.spark.sql.functions.col;

/*

Run me:

$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-topics.sh \
    --create \
    --topic my-spark-topic \
    --zookeeper node1.example.com:2181,node2.example.com:2181,node3.example.com:2181 \
    --partitions 1 \
    --replication-factor 1

$ mvn exec:exec -Dspark.master=local

$ spark-submit \
  --master yarn \
  --driver-memory 512m  \
  --executor-memory 512m \
  --class it.sunnyvale.academy.sparkkafka.Main \
  --deploy-mode cluster \
  target/spark-kafka-1.0-SNAPSHOT.jar

$ /usr/hdp/3.1.0.0-78/kafka/bin/kafka-console-producer.sh \
    --request-required-acks all \
    --topic my-spark-topic \
    --broker-list node1.example.com:6667,node2.example.com:6667,node3.example.com:6667

*/
public class Main {

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Spark loves Kafka")
                .getOrCreate();

        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "192.168.199.2:6667,192.168.199.3:6667,192.168.199.4:6667")
                .option("subscribe", "my-spark-topic")
                .option("startingOffsets", "latest")
                .load();


        try {

            df
                    .writeStream()
                    .outputMode("append")
                    .format("console")
                    .option("truncate", false)
                    .start()
                    .awaitTermination();

        } catch (StreamingQueryException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }
}
