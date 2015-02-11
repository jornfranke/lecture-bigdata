lecture-bigdata
===============

Example Projects for using various big data technologies in the context of the Hadoop Ecosystem

Building the applications
===============
You need to clone the whole repository by using the following command:

git clone https://github.com/jornfranke/lecture-bigdata.git

You will need to install Gradle (http://www.gradle.org)

You can change to the folder lecture-bigdata

Afterwards you can change into any of the subfolders to build the projects you are interested in
* MapReduce: A simple hadoop map reduce job to count the number of tweets in a text file on HDFS
* SparkStreaming: A simple spark streaming job to count the number of tweets send by a simple network server
* tweet-server : A simple server for sending tweets to any client connecting to port 1234 on localhost. It is a shellscript using netcat.

You can get tweets using a twitter client, such as https://github.com/twitter/hbc

You can build the map reduce job or the spark streaming job using the following command in the corresponding subfolders:

gradle clean build

You do not need to build the tweet-server - it is a simple Linux shell script.

Running the applications
=================
You will find a run.sh in the directories "MapReduce" or "SparkStreaming". Both have been tested with the Cloudera Quickstart VM 5.1

The run.sh of the map reduce jobs expects an input file with tweets (or json-objects) on HDFS on /user/cloudera/inputtweet 

After running it you can view the number of tweets by using the following command:
hadoop fs -cat /user/cloudera/outputtweet/part-r-00000


The run.sh of the spark streaming jobs requires the following:
* Spark environment variables set in /etc/spark/conf/spark-env.sh
* The jar "/usr/lib/spark/streaming/lib/gson-2.2.4.jar". You can get it here: http://repo1.maven.org/maven2/com/google/code/gson/gson/2.2.4/gson-2.2.4.jar
* The simple server for sending tweets started

You can start the simple tweet server as follows:

./tweet-server/twitterstreamserver.sh

It sends the tweets in the file "sample.txt" in a never ending while loop. Each second it sends one tweet to the client.

Please note that after disconnection of the client, you need to restart the server using the command above.
