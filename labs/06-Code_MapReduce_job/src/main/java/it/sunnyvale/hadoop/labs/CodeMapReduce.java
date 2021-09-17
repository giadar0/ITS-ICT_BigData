package it.sunnyvale.hadoop.labs;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

public class CodeMapReduce {

    public static class MyMapper
            extends org.apache.hadoop.mapreduce.Mapper<Object, Text, Text, IntWritable> {


        @Override
        public void map(Object key, Text value, org.apache.hadoop.mapreduce.Mapper.Context context
        ) throws IOException, InterruptedException {
            String line = value.toString();
            String lasttoken = null;
            StringTokenizer s = new StringTokenizer(line,"\t");
            String year = s.nextToken();

            while(s.hasMoreTokens()) {
                lasttoken = s.nextToken();
            }
            int avgprice = Integer.parseInt(lasttoken);
            context.write(new Text(year), new IntWritable(avgprice));
        }
    }

    public static class MyReducer
            extends Reducer<Text,IntWritable,Text, IntWritable> {


        private IntWritable result = new IntWritable();

        @Override
        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            int maxavg = 30;
            int val = Integer.MIN_VALUE;

            for(IntWritable item :values){
                if(item.get()>maxavg) {
                    context.write(key, new IntWritable(item.get()));
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        FileUtils.deleteDirectory(new File(args[1]));

        Configuration conf = new Configuration();
        //Job job = Job.getInstance(conf, "word count");
        Job job = new Job(conf, "Hadoop Example for dynamically and programmatically compiling-running a job");
        //job.getConfiguration().set("fs.file.impl", "com.conga.services.hadoop.patch.HADOOP_7682.WinLocalFileSystem");
        job.setJarByClass(CodeMapReduce.class);
        job.setMapperClass(MyMapper.class);
        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
