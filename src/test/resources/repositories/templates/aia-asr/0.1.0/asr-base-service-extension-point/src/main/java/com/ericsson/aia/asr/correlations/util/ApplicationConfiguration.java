package com.ericsson.aia.asr.correlations.util;

public class ApplicationConfiguration{
	private TransportConfiguration  tConfig=null;
	private String sparkMaster;
	private String tachyonMaster;
	private long winTime;
	public ApplicationConfiguration(String kafkaTopicGroup, String topicName, String kafkaBroker,
			String sparkMaster, String tachyonMaster, long winTime, String appName) {
		super();
		tConfig= new TransportConfiguration(kafkaTopicGroup,topicName,kafkaBroker);
		this.sparkMaster = sparkMaster;
		this.tachyonMaster = tachyonMaster;
		this.winTime = winTime;
		this.appName = appName;
	}
	public String getKafkaTopicGroup() {
		return tConfig.getKafkaTopicGroup();
	}
	public String getTopicName() {
		return tConfig.getTopicName();
	}
	
	public String getKafkaBroker() {
		return tConfig.getKafkaBroker();
	}
	public String getSparkMaster() {
		return sparkMaster;
	}
	public void setSparkMaster(String sparkMaster) {
		this.sparkMaster = sparkMaster;
	}
	public String getTachyonMaster() {
		return tachyonMaster;
	}
	public void setTachyonMaster(String tachyonMaster) {
		this.tachyonMaster = tachyonMaster;
	}
	public long getWinTime() {
		return winTime;
	}
	public void setWinTime(long winTime) {
		this.winTime = winTime;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	private String appName;
	
}