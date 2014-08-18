source /etc/spark/conf/spark-env.sh

spark-submit \
--class org.university.tutorial.sparkstreaming.StreamDriver \
--jars /usr/lib/spark/streaming/lib/gson-2.2.4.jar  \
--deploy-mode client \
--master spark://$SPARK_MASTER_IP:$SPARK_MASTER_PORT \
build/libs/example-spark-streaming-0.1.0.jar
