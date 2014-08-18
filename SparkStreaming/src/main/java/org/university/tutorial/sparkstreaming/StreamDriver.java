/**
 * Simple Streaming Example for Spark Streaming using tweets send from a simple server over a normal socket
 */
package org.university.tutorial.sparkstreaming;
import scala.Tuple2;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import java.util.regex.Pattern;
/**
* Author: Jörn Franke <jornfranke@gmail.com>
* 
*/

public final class StreamDriver {
	private static final Pattern SPACE = Pattern.compile(" ");
	public static void main(String[] args) {
		// Create the context with a 1 second window size
		SparkConf sparkConf = new SparkConf().setAppName("TweetServerCount");	
		JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, new Duration(1000));	
		// connect to a simple server streaming tweets (or any Json object)
		JavaReceiverInputDStream<String> lines = ssc.socketTextStream("localhost", 1234, StorageLevels.MEMORY_AND_DISK_SER);
		JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
			public Iterable<String> call(String x)  {
				try {
					JsonObject jsonObject =  new JsonParser().parse(x).getAsJsonObject();
					// read from the json object the value of the attribute "text" - in a tweet this is the current text of the tweet
					String textline=jsonObject.get("text").getAsString();
					// split into words
					return Lists.newArrayList(SPACE.split(textline));
					
					} catch (Exception e) {
						// sometimes the server does not transfer the correct result due to special characters, we ignore them
						
					}
				return Lists.newArrayList("");
			}
		});
		/** Count the number of tweets containing the word "worldcup" within the 1 second window **/
		JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
				new PairFunction<String, String, Integer>() {
					public Tuple2<String, Integer> call(String s) {
						// count number of worldcup
						if (s.toLowerCase().contains("worldcup")) {
							return new Tuple2<String, Integer>("worldcup",1);
						} 
						// if it does not contain worldcup return 0
						return new Tuple2<String, Integer>("worldcup",0);
					}
				}).reduceByKey(new Function2<Integer, Integer, Integer>() {
					// summarize the total counts of worldcup with in the 1 second window
					public Integer call(Integer i1, Integer i2) {
						return i1 + i2;
				}
				});
		// print the counts per 1 second / window
		wordCounts.print();
		ssc.start();
		ssc.awaitTermination();
	}
}
