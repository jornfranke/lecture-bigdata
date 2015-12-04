/**
 * Simple Driver for a map reduce job counting tweets or tweet content
 */
package org.university.tutorial.mapreduce.driver;

import java.io.IOException;
import java.util.*;
        
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import org.university.tutorial.mapreduce.tasks.TweetMap;
import org.university.tutorial.mapreduce.tasks.TweetReduce;
      
/**
* Author: Jörn Franke <jornfranke@gmail.com>
*
*/

public class TweetDriver  {

       
        
 public static void main(String[] args) throws Exception {
    JobConf conf = new JobConf(TweetDriver.class);
    conf.setJobName("example-hadoop-job");
    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(IntWritable.class);
        
    conf.setMapperClass(TweetMap.class);
    conf.setReducerClass(TweetReduce.class);
        
    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);
        
    FileInputFormat.addInputPath(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1]));
        
    JobClient.runJob(conf);
 }
        
}
