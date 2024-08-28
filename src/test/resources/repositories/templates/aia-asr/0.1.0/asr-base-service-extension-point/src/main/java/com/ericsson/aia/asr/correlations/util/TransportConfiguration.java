package com.ericsson.aia.asr.correlations.util;

public class TransportConfiguration {
	private String kafkaBroker;
	private String kafkaTopicGroup;
	private String topicName;

	public TransportConfiguration(String kafkaTopicGroup, String topicName, String kafkaBroker) {
		this.kafkaTopicGroup = kafkaTopicGroup;
		this.kafkaBroker = kafkaBroker;
		this.topicName = topicName;
	}

	public String getKafkaBroker() {
		return kafkaBroker;
	}

	public String getKafkaTopicGroup() {
		return kafkaTopicGroup;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setKafkaBroker(String kafkaBroker) {
		this.kafkaBroker = kafkaBroker;
	}

	public void setKafkaTopicGroup(String kafkaTopicGroup) {
		this.kafkaTopicGroup = kafkaTopicGroup;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	
}
