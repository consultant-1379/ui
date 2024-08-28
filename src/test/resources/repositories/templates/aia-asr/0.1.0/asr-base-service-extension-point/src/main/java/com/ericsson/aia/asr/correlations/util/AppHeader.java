package com.ericsson.aia.asr.correlations.util;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.avro.generic.GenericRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import com.ericsson.aia.common.avro.kafka.decoder.KafkaGenericRecordDecoder;

import kafka.serializer.StringDecoder;

public class AppHeader {
	private static JavaStreamingContext jssc;
	private static   ApplicationConfiguration  configuration;
	static {
		System.setProperty("schemaRegistry.address", "http://localhost:8081/");
		System.setProperty("schemaRegistry.cacheMaximumSize", "100000");
			
	}
	public static ApplicationConfiguration getConfiguration() {
		return configuration;
	}
	public static JavaStreamingContext init(String[] args,String applicationName) {
		String group = args[0];
		checkArgument(group != null, "Kafka group  cannot be nulll.");
		String topic = args[1];
		checkArgument(topic != null, "Kafka topic  cannot be nulll.");
		String masterUrl = "local[*]";
		String tachyon = args[3];
		String kafka = args[4];// "localhost:9092"
		long windTime = Long.parseLong(args[5]);
		checkArgument(windTime > 0, "Spark window time canot be less than 0.");
		configuration = new ApplicationConfiguration(group, topic, kafka, masterUrl, tachyon, windTime, applicationName);
		SparkConf sparkConf = new SparkConf().setAppName(applicationName)
				.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
				.set("spark.externalBlockStore.url", tachyon)
				.set("spark.externalBlockStore.baseDir", "/tmp/ups-data")
				.set("spark.externalBlockStore.blockManager", "org.apache.spark.storage.TachyonBlockManager")
				
				.setMaster(masterUrl);
		// Create the context with a 1 second batch size
		jssc = new JavaStreamingContext(sparkConf, new Duration(1000));
		 jssc.checkpoint("/tmp/mycheck");      
		return jssc;
	}
	public static JavaPairInputDStream<String, GenericRecord> createConnection (JavaSparkContext sparkContexts) {
		
		
		HashSet<String> topicsSet = new HashSet<String>();
		topicsSet.addAll(Arrays.asList(Util.slitByDelimiter(configuration.getTopicName(), ",")));
		HashMap<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", configuration.getKafkaBroker());
		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, GenericRecord> messages = KafkaUtils.createDirectStream(jssc, String.class,
				GenericRecord.class, StringDecoder.class, KafkaGenericRecordDecoder.class, kafkaParams, topicsSet);
		return messages;
	}
public static JavaPairInputDStream<String, GenericRecord> createConnection (JavaSparkContext sparkContexts,String topicName) {
		
		
		HashSet<String> topicsSet = new HashSet<String>();
		topicsSet.addAll(Arrays.asList(Util.slitByDelimiter(topicName, ",")));
		HashMap<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", configuration.getKafkaBroker());
		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, GenericRecord> messages = KafkaUtils.createDirectStream(jssc, String.class,
				GenericRecord.class, StringDecoder.class, KafkaGenericRecordDecoder.class, kafkaParams, topicsSet);
		return messages;
	}
public static JavaPairInputDStream<String, GenericRecord> createConnection (JavaSparkContext sparkContexts, TransportConfiguration config) {
		
		
		HashSet<String> topicsSet = new HashSet<String>();
		topicsSet.add(config.getTopicName());
		HashMap<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", config.getKafkaBroker());
		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, GenericRecord> messages = KafkaUtils.createDirectStream(jssc, String.class,
				GenericRecord.class, StringDecoder.class, KafkaGenericRecordDecoder.class, kafkaParams, topicsSet);
		return messages;
	}
}
