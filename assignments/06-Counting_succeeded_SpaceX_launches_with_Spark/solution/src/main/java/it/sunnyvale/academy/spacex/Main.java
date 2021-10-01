package it.sunnyvale.academy.spacex;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;


/*
    Run me (locally):

    $ mvn clean && mvn package
    $ mvn exec:exec
 */
public class Main {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf().setAppName("WordCount");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // Load dataset
        JavaRDD<String> lines = null;
        if(args.length > 0) {
            lines = sc.textFile(args[0]);
        }else {
            lines = sc.textFile("ass6_input/spacex_launches.csv");
        }

        long count = lines
                // Skip CSV header
                .filter(line -> !line.startsWith("#"))
                // Map to launch result (4th column)
                .map(line -> line.split(";",-1)[3])
                // Filter successful launches
                .filter(line -> line.equals("true"))
                // Count result
                .count();



        /*

        The following lines of code are TOTALLY equivalent to the lines up above

        // Skip CSV header
        JavaRDD<String> rdd1 = lines.filter(line -> !line.startsWith("#"));

        // Map to launch result (4th column)
        JavaRDD<String> rdd2 = rdd1.map(line -> line.split(";",-1)[3]);

        // Filter successful launches
        JavaRDD<String> rdd3 = rdd2.filter(line -> line.equals("true"));

        // Count result
        long count1 = rdd3.count();
        */


        System.out.println(count);

    }
}
