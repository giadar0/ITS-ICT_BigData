package it.sunnyvale.academy.searchstrava;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;

/*
Run me:

$ mvn exec:exec -Dspark.master=local

$ spark-submit \
  --master yarn \
  --driver-memory 512m  \
  --executor-memory 512m \
  --class it.sunnyvale.academy.searchstrava.Main \
  --deploy-mode cluster \
  target/search-strava-1.0-SNAPSHOT.jar

*/
public class Main {

    public static void main(String[] args) {

        SparkSession spark = SparkSession
                .builder()
                .appName("Search in Strava activities")
                .getOrCreate();

        Dataset<Row> df = spark
                .read()
                .option("multiline", "true")
                .option("inferSchema", "false")
                .option("nullValue", "null")
                .option("treatEmptyValuesAsNulls", "true")
                .json("ass8_input"+System.getProperty("file.separator")+"strava_activities.json");


        df.createOrReplaceTempView("activities");

        // Max distance (DataFrame API)
        System.out.println(String.format("\n=== Max distance (DataFrame API) ==="));
        df
                .filter(col("Distance").isNotNull())
                .groupBy(col("Activity Type"))
                .max("Distance")
                .show();

        // Max distance (SQL query)
        System.out.println(String.format("\n=== Max distance (SQL query) ==="));
        spark
                .sql("SELECT `Activity Type`, max(Distance) from activities GROUP BY `Activity Type`")
                .show();

        // Max elevation gain (DataFrame API)
        System.out.println(String.format("\n=== Max elevation gain (DataFrame API) ==="));
        df
                .filter(col("Elevation Gain").isNotNull())
                .select(max("Elevation Gain"))
                .show();

        // Max elevation gain (SQL query)
        System.out.println(String.format("\n=== Max elevation gain (SQL query) ==="));
        spark
                .sql("SELECT max(`Elevation Gain`) from activities WHERE `Elevation Gain` IS NOT NULL")
                .show();

        // Lowest average temperature (DataFrame API)
        System.out.println(String.format("\n=== Lowest average temperature (DataFrame API) ==="));
        df
                .filter(col("Average Temperature").isNotNull())
                .select(min("Average Temperature"))
                .show();

        // Lowest average temperature (SQL query)
        System.out.println(String.format("\n=== Lowest average temperature (SQL query) ==="));
        spark
                .sql("SELECT min(`Average Temperature`) from activities")
                .show();

        // Highest calories (DataFrame API)
        System.out.println(String.format("\n=== Highest calories (DataFrame API) ==="));
        df
                .filter(col("Calories").isNotNull())
                .select(max("Calories"))
                .show();

        // Highest calories (SQL query)
        System.out.println(String.format("\n=== Highest calories (SQL query) ==="));
        spark
                .sql("SELECT max(`Calories`) from activities WHERE Calories IS NOT NULL")
                .show();

        // Max speed (DataFrame API)
        System.out.println(String.format("\n=== Max speed (DataFrame API) ==="));
        df
                .filter(col("Max Speed").isNotNull())
                .select(max("Max Speed"))
                .show();

        // Max speed (SQL query)
        System.out.println(String.format("\n=== Max speed (SQL query) ==="));
        spark
                .sql("SELECT max(`Max Speed`) from activities WHERE `Max Speed` IS NOT NULL")
                .show();

        // Longest moving time (DataFrame API)
        System.out.println(String.format("\n===  Longest moving time (DataFrame API) ==="));
        df
                .filter(col("Moving Time").isNotNull())
                .select(max("Moving Time"))
                .show();

        //  Longest moving time (SQL query)
        System.out.println(String.format("\n===  Longest moving time (SQL query) ==="));
        spark
                .sql("SELECT max(`Moving Time`) from activities WHERE `Moving Time` IS NOT NULL")
                .show();

        //  First and last activities (DataFrame API)
        System.out.println(String.format("\n===   First and last activities (DataFrame API) ==="));
        df
                .filter(col("Activity Date").isNotNull())
                .select(min(to_date(col("Activity Date"),"MMM d, yyyy, h:mm:ss a")).as("first"),max(to_date(col("Activity Date"),"MMM d, yyyy, h:mm:ss a")).as("last"))
                .show();

        //  First and last activities (SQL query)
        System.out.println(String.format("\n===   First and last activities (SQL query) ==="));
        spark
                .sql("SELECT min(to_date(`Activity Date`,'MMM d, yyyy, h:mm:ss a')) as first, max(to_date(`Activity Date`,'MMM d, yyyy, h:mm:ss a')) as last from activities WHERE `Activity Date` IS NOT NULL")
                .show();

        // Count activities (DataFrame API)
        System.out.println(String.format("\n=== Count activities (DataFrame API) ==="));
        long countDF = df.count();
        System.out.println(countDF);

        // Count activities (SQL query)
        System.out.println(String.format("\n=== Count activities (SQL query) ==="));
        spark
                .sql("SELECT count(*) from activities")
                .show();

    }
}
