/**
 * Simple Reducer for summarizing counts by the mapper
 */
package org.university.tutorial.mapreduce.tasks;

/**
* Author: Jörn Franke <jornfranke@gmail.com>
*
*/
import java.io.IOException;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.io.*;
import java.util.*;

public class TweetReduce extends Reducer<Text, IntWritable, Text, IntWritable> {

   public void reduce(Text key, Iterable<IntWritable> values, Context context)
     throws IOException, InterruptedException {
       int sum = 0;
       for (IntWritable val : values) {
           sum += val.get();
       }
       context.write(key, new IntWritable(sum));
   }
}

