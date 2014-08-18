/**
 * Simple Driver for a map reduce job counting tweets or tweet content
 */
package org.university.tutorial.mapreduce.driver;

import java.io.IOException;
import java.util.*;
        
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.university.tutorial.mapreduce.tasks.TweetMap;
import org.university.tutorial.mapreduce.tasks.TweetReduce;
      
/**
* Author: Jörn Franke <jornfranke@gmail.com>
*
*/

public class TweetDriver {

       
        
 public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
        
        Job job = new Job(conf, "example-hadoop-job");
    job.setJarByClass(TweetDriver.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
        
    job.setMapperClass(TweetMap.class);
    job.setReducerClass(TweetReduce.class);
        
    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);
        
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
        
    job.waitForCompletion(true);
 }
        
}
