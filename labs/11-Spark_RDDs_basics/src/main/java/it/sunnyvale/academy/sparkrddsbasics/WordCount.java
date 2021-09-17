package it.sunnyvale.academy.sparkrddsbasics;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

/*
Run me:

$ mvn exec:exec \
  -Dspark.master=local \
  -P WordCount

*/

public class WordCount {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("WordCount");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> lines = null;

        if(args[0] != null){
            lines = sc.textFile(args[0]);
        }else{
            lines = sc.textFile("lab11_input/constitution.txt");
        }
        
        
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        Map<String, Long> wordCounts = words.countByValue();


        // Save to text file
        if(args[1] != null){
            Pattern SPACE = Pattern.compile(" ");
            JavaPairRDD<String, Integer> counts = lines
                    .flatMap(s -> Arrays.asList(SPACE.split(s)).iterator())
                    .mapToPair(s -> new Tuple2<>(s, 1))
                    .reduceByKey((a, b) -> a + b);
            counts.saveAsTextFile(args[1]);
        }


        for (Map.Entry<String, Long> entry : wordCounts.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
