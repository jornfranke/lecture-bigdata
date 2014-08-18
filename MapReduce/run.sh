hadoop fs -rm -R /user/cloudera/outputtweet
hadoop jar build/libs/example-hadoop-0.1.0.jar org.university.tutorial.mapreduce.driver.TweetDriver /user/cloudera/inputtweet /user/cloudera/outputtweet
