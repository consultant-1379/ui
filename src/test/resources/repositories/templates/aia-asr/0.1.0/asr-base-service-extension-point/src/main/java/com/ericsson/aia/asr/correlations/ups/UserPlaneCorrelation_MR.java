package com.ericsson.aia.asr.correlations.ups;

import static com.google.common.base.Preconditions.checkArgument;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.avro.generic.GenericRecord;
import org.apache.avro.util.Utf8;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.aia.common.avro.kafka.decoder.KafkaGenericRecordDecoder;
import com.ericsson.aia.model.eventbean.correlation.LTE_USER_SESSION;

import kafka.serializer.StringDecoder;
import scala.Tuple2;

public class UserPlaneCorrelation_MR {

	private static Logger logger = LoggerFactory.getLogger(UserPlaneCorrelation_MR.class);
	private static final String INTERNAL_PROC_UE_CTXT_RELEASE = "INTERNAL_PROC_UE_CTXT_RELEASE";
	private static final String INTERNAL_EVENT_UE_CAPABILITY = "INTERNAL_EVENT_UE_CAPABILITY";
	private static final String INTERNAL_EVENT_UE_MOBILITY_EVAL = "INTERNAL_EVENT_UE_MOBILITY_EVAL";
	private static final String INTERNAL_PER_RADIO_UE_MEASUREMENT = "INTERNAL_PER_RADIO_UE_MEASUREMENT";
	



	private static final Duration WINDOW_LENGTH_M =  Durations.seconds(30);
	// Stats will be computed every slide interval time.
	private static final Duration SLIDE_INTERVAL_M =  Durations.seconds(10);
	private static JavaSparkContext sparkContext;
	private static  Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.setProperty("schemaRegistry.cacheMaximumSize", "50000");
		String group = args[0];
		checkArgument(group != null, "Kafka group  cannot be nulll.");
		String topic = args[1];
		checkArgument(topic != null, "Kafka topic  cannot be nulll.");
		checkArgument(args[2] !=null, "Spark master user cannot be null.");
        String masterUrl = args[2];
        String tachyon=args[3];//s[3]"tachyon://localhost:19998";
        String kafka =args[4];//"localhost:9092";
        String windowTime =args[5];
        String schemaReg= System.getProperty("schemaRegistry.address");
        checkArgument(schemaReg != null, "Schema Registry address   cannot be nulll [Please use following syntax http://server:port].");
        System.out.println("master ="+masterUrl);
        System.out.println("tachyon ="+tachyon);
        System.out.println("kafka ="+kafka);
        System.out.println("topic ="+topic);
		SparkConf sparkConf = new SparkConf().setAppName("Radio-UPS")
		.setMaster(masterUrl)
		.set("spark.serializer","org.apache.spark.serializer.KryoSerializer")
		//.set("spark.kryoserializer.buffer.max", "1024m")
		.set("spark.externalBlockStore.url", tachyon);


		//.set("spark.externalBlockStore.baseDir", "/tmp/ups-data");
		//.setMaster(masterUrl);
	
		// Create the context with a 1 second batch size
		JavaStreamingContext jssc = new JavaStreamingContext(sparkConf,
				new Duration(1000));
		jssc.checkpoint(tachyon+"/tmp/");
		sparkContext = jssc.sparkContext();
		HashSet<String> topicsSet = new HashSet<String>();
		topicsSet.add(topic);
		HashMap<String, String> kafkaParams = new HashMap<String, String>();
		kafkaParams.put("metadata.broker.list", kafka);
		// Create direct kafka stream with brokers and topics
		JavaPairInputDStream<String, GenericRecord> messages = KafkaUtils
				.createDirectStream(jssc, String.class, GenericRecord.class,
						StringDecoder.class, KafkaGenericRecordDecoder.class,
						kafkaParams, topicsSet);
				JavaPairDStream<String, GenericRecord> window = messages.window(WINDOW_LENGTH_M, SLIDE_INTERVAL_M);
			//	JavaPairDStream<String, GenericRecord> mapToPair = window
		       // .mapToPair(new mapByKey());
				JavaDStream<GenericRecord> map = window.map(new Function<Tuple2<String,GenericRecord>, GenericRecord>() {

					@Override
					public GenericRecord call(Tuple2<String, GenericRecord> v1)
							throws Exception {
						return v1._2;
					}
				});
				JavaPairDStream<String, List<GenericRecord>> mapPartitionsToPair = map.mapPartitionsToPair(new PairFlatMapFunction<Iterator<GenericRecord>, String, List<GenericRecord>>() {

					@Override
					public Iterable<Tuple2<String, List<GenericRecord>>> call(
							Iterator<GenericRecord> t) throws Exception {
					
						ConcurrentHashMap<String, Tuple2<String, List<GenericRecord>>> map = new ConcurrentHashMap<>();
						 while (t.hasNext()) {
							 GenericRecord record = 	t.next();
							 Long cellId = (Long) record.get("GLOBAL_CELL_ID");
								String key = cellId + ""
												+ ((Long) record.get("ENBS1APID")) + ""
												+ ((Long) record.get("MMES1APID"));
								Tuple2<String, List<GenericRecord>> tuple2 = map.get(key);
								if(tuple2 == null){
									List<GenericRecord> container = new ArrayList<>();
									container.add(record);
									tuple2 = new Tuple2<String, List<GenericRecord>>(key, container);
									map.put(key, tuple2);
								}else{
									tuple2._2.add(record);
								}
								
						}
						return map.values();
					}
				});
				mapPartitionsToPair.foreachRDD(new Function<JavaPairRDD<String,List<GenericRecord>>, Void>() {
   
					/**
					 * 
					 */
					private static final long serialVersionUID = 338405191233840741L;

					@Override
					public Void call(JavaPairRDD<String, List<GenericRecord>> v1)
							throws Exception {
						
						List<Tuple2<String, List<GenericRecord>>> collect = v1.collect();
						JavaRDD<Tuple2<String, List<GenericRecord>>> parallelize = sparkContext.parallelize(collect);
						JavaPairRDD<LTE_USER_SESSION_EXT, Tuple2<String, List<GenericRecord>>> keyBy = parallelize.keyBy(new Function<Tuple2<String,List<GenericRecord>>, LTE_USER_SESSION_EXT>() {

							/**
							 * 
							 */
							private static final long serialVersionUID = -4389884053626012471L;

							@Override
							public LTE_USER_SESSION_EXT call(
									Tuple2<String, List<GenericRecord>> v1)
									throws Exception {
								return executeCorrelation(v1._2().iterator());
							}
						});
						JavaRDD<LTE_USER_SESSION_EXT> keys = keyBy.keys();
						List<LTE_USER_SESSION_EXT> collect2 = keys.collect();
						System.out.println("LTE_USER_SESSION Storing data ->" +collect2.size());
						/*for (LTE_USER_SESSION_EXT string : collect2) {
							System.out.println("LTE_USER_SESSION ->" +string.getDecodedString());
						}*/
						if(!keys.isEmpty()){
						String path = cal.get(Calendar.YEAR)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.DATE)
								+"/"+cal.get(Calendar.HOUR)+"/"+cal.get(Calendar.MINUTE)+"/"+System.currentTimeMillis()+"/";
					//	keys.coalesce(1).saveAsTextFile("/home/anauser/data-spark-output/demo/"+path);
						JavaRDD<LTE_USER_SESSION_EXT> coalesce = keys.coalesce(1);
						
						coalesce.saveAsTextFile(tachyon+"/demo/lte-radio-user-plane/data/"+path);
						coalesce.saveAsObjectFile(tachyon+"/demo/lte-radio-user-plane/rdd/"+path);
						System.out.println("LTE_USER_SESSION Storing data ->" +collect2.size());
						}
						return null;
					}
					
					private LTE_USER_SESSION_EXT executeCorrelation(Iterator<GenericRecord> iterator) {
						LTE_USER_SESSION_EXT userSession = new LTE_USER_SESSION_EXT();
						int counter =0;
						while(iterator.hasNext()){
							GenericRecord record = iterator.next();
							if(record == null){
								System.err.println("Input data is null");
								continue;
							}
							String name = record.getSchema().getName();
							updateHeader(userSession, record);
							userSession.setTOTAL_SAMPLES(counter++);
							switch(name){
								case INTERNAL_PER_RADIO_UE_MEASUREMENT:
									
									collectRadioUeMeasurementData(userSession, record);
									continue;
								case INTERNAL_EVENT_UE_MOBILITY_EVAL:
									
									continue;
								case INTERNAL_EVENT_UE_CAPABILITY:
									collect_UE_Capability_Data(userSession, record);
									continue;
								case INTERNAL_PROC_UE_CTXT_RELEASE:
									collectSessionCloseData(userSession, record);
									continue;
								case "INTERNAL_PER_UE_TRAFFIC_REP":
									collectData(userSession, record);
									continue;
								case "INTERNAL_PER_UE_RB_TRAFFIC_REP":
									collectRabData(userSession, record);
									continue;
							}
						}
						return userSession;
					}
					 private void collectRadioUeMeasurementData(LTE_USER_SESSION_EXT data, GenericRecord record) {
				            data.setPOWER_RESTRICT((Long) record.get("TBSPWRRESTRICTED"));
				             data.setPOWER_NO_RESTRICT((Long) record.get("TBSPWRUNRESTRICTED"));
				             long samplesCount =cqiSamples(record);
				             samplesCount=samplesCount ==0 ?1:samplesCount;
				             long cqiWtSum = cqiWeightedSamples(record);
				             if(samplesCount>0){
				                 double calculatedCQIAVG=cqiWtSum/(double)samplesCount;
				                 data.setCQI_AVG(calculatedCQIAVG);
				             }
				        }


				        private long cqiSamples(GenericRecord record){
				            return (Long) record.get("CQI_REPORTED_0")
				            		+(Long) record.get("CQI_REPORTED_1")
				            		+(Long) record.get("CQI_REPORTED_2")
				            		+(Long) record.get("CQI_REPORTED_3")
				            		+(Long) record.get("CQI_REPORTED_4")
				            		+(Long) record.get("CQI_REPORTED_5")
				            		+(Long) record.get("CQI_REPORTED_6")
				                    +(Long) record.get("CQI_REPORTED_7")
				                    +(Long) record.get("CQI_REPORTED_8")
				                    +(Long) record.get("CQI_REPORTED_9")
				                    +(Long) record.get("CQI_REPORTED_10")
				                    +(Long) record.get("CQI_REPORTED_11")
				                    +(Long) record.get("CQI_REPORTED_12")
				                    +(Long) record.get("CQI_REPORTED_13")
				                    +(Long) record.get("CQI_REPORTED_14")
				                    +(Long) record.get("CQI_REPORTED_15");
				        }

				        private long cqiWeightedSamples(GenericRecord record){
				            return (Integer) record.get("CQI_REPORTED_1")
				            		+((Long) record.get("CQI_REPORTED_2")*2)
				            		+((Long) record.get("CQI_REPORTED_3")*3)
				                    +((Long) record.get("CQI_REPORTED_4")*4)
				                    +((Long) record.get("CQI_REPORTED_5")*5)
				                    +((Long) record.get("CQI_REPORTED_6")*6)
				                    +((Long) record.get("CQI_REPORTED_7")*7)
				                     +((Long) record.get("CQI_REPORTED_8")*8)
				                     +((Long) record.get("CQI_REPORTED_9")*9)
				                     +((Long) record.get("CQI_REPORTED_10")*10)
				                     +((Long) record.get("CQI_REPORTED_11")*11)
				                     +((Long) record.get("CQI_REPORTED_12")*12)
				                     +((Long) record.get("CQI_REPORTED_13")*13)
				                     +((Long) record.get("CQI_REPORTED_14")*14)
				                     +((Long) record.get("CQI_REPORTED_15")*15);
				        }

					private void collectRabData(LTE_USER_SESSION_EXT data, GenericRecord record) {
			            data.setPACKET_TR_DL((Long) record.get("PER_DRB_PDCP_TRANSVOL_DL"));
			            data.setPACKET_REC_DL((Long) record.get("PER_DRB_PDCP_ACKVOL_DL"));
			            data.setPACKET_LOST_HO_DL((Long) record.get("PER_DRB_PACKET_LOST_HO_DL"));
			            data.setPACKET_LOST_PELR_DL((Long) record.get("PER_DRB_PACKET_LOST_PELR_DL"));
			            data.setPACKET_REC_UL((Long) record.get("PER_DRB_PACKET_REC_UL"));
			            data.setPACKET_LOST_UL((Long) record.get("PER_DRB_PACKET_LOST_UL"));
			            data.setPACKET_LOST_PELR_UU_DL_RLCUM((Long) record.get("PER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM"));
			            data.setPACKET_LOST_PERL_UU_DL((Long) record.get("PER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM"));
			        }
					private void collect_UE_Capability_Data(LTE_USER_SESSION_EXT data, GenericRecord record) {
			            data.setASR((Integer) record.get("UE_CAP_ASR"));
			            data.setUE_CAPABILITY((Integer) record.get("UE_CAP_CATEGORY"));
			            data.setFGI(new String(((ByteBuffer) record.get("UE_CAP_FGI_BITMAP")).array()));
			        }

			        private void collectSessionCloseData(LTE_USER_SESSION_EXT data, GenericRecord record) {
			            data.setTRIGGER_NODE((Integer) record.get("TRIGGERING_NODE"));
			            data.setUE_CTXT_RELEASE_CAUSE((Integer) record.get("INTERNAL_RELEASE_CAUSE"));
			            data.setUE_CTXT_RELEASE_CAUSE_S1((Integer) record.get("S1_RELEASE_CAUSE"));
			        }


					private void updateHeader(LTE_USER_SESSION_EXT userSession,
							GenericRecord record) {
						if(userSession.isHeaderSet()){
							long timestamp = (long) record.get("_TIMESTAMP");
							if(userSession.getSTART_TIMESTAMP()<timestamp){
								userSession.setEND_TIMESTAMP(userSession.getSTART_TIMESTAMP());
								userSession.setSTART_TIMESTAMP(timestamp);
								userSession.setDURATION(userSession.getEND_TIMESTAMP()-userSession.getSTART_TIMESTAMP());
							}
							userSession.setEND_TIMESTAMP(timestamp);
							userSession.setDURATION(userSession.getEND_TIMESTAMP()-userSession.getSTART_TIMESTAMP());
							return;
						}
						
						Utf8 neName = (Utf8) record.get("_NE");
						Long globalCellId = (Long) record.get("GLOBAL_CELL_ID");
						Long racUeRef = (Long) record.get("RAC_UE_REF");
						Long enbs1ApId = (Long)record.get("ENBS1APID");
						Long mmeS1Apid = (Long) record.get("MMES1APID");
						ByteBuffer gummei = (ByteBuffer) record.get("GUMMEI");
						long timestamp = (long) record.get("_TIMESTAMP");
						
						byte[] dataGummei = gummei==null?new byte[0]:gummei.array();
						setHeader(userSession,  neName.toString(),globalCellId,
								 racUeRef, enbs1ApId,mmeS1Apid,
								 dataGummei,timestamp);
						// heder flags sets true to avoid further updates
						userSession.setHeader();
					}
					
					/**
					 * This method reads the value from the INTERNAL_PER_UE_TRAFFIC_REP events and aggregate records for a min
					 * 
					 * @param data Output record
					 * @param record input event of type avro GenericRecrod.
					 */
					private void collectData(LTE_USER_SESSION_EXT data,
							GenericRecord record) {
						
							data.setUE_TRAF_REP_SAMPLES(data.getUE_TRAF_REP_SAMPLES()+1);
							long per_UE_THP_TIME_DL = (Long) record.get("PER_UE_THP_TIME_DL");
							per_UE_THP_TIME_DL =per_UE_THP_TIME_DL >0?per_UE_THP_TIME_DL:1;// to avoid division by zero
					        double vol_THP_DL= ((Long) record.get("PER_UE_THP_DL_DRB"))/8.0*per_UE_THP_TIME_DL;
					        data.setVOL_THP_DL( data.getVOL_THP_DL()+vol_THP_DL);
							long per_UE_THP_TIME_UL = (Long) record.get("PER_UE_THP_TIME_UL");
							per_UE_THP_TIME_UL =per_UE_THP_TIME_UL >0?per_UE_THP_TIME_UL:1;// to avoid division by zero
					        double vol_THP_UL=((Long) record.get("PER_UE_THP_UL_DRB"))/8.0*per_UE_THP_TIME_UL;
					        data.setVOL_THP_UL(data.getVOL_THP_UL()+vol_THP_UL);
					        data.setVOL_TOTAL_DL(data.getVOL_TOTAL_DL()+(vol_THP_DL+(Long) record.get("PER_UE_THP_PDCPVOL_TRUNK_DL"))); // 1084418.375 + 3223716.0 + 1539244.75
					        Double d = (Double) (data.getVOL_TOTAL_UL()+(vol_THP_UL+(Long) record.get("PER_UE_THP_PDCPVOL_TRUNK_UL")));
							data.setVOL_TOTAL_UL(d.longValue());
							
					        data.setTIME_THP_DL(data.getTIME_THP_DL()+(Long) record.get("PER_UE_THP_TIME_DL"));
					        data.setTIME_THP_UL( data.getTIME_THP_UL()+(Long) record.get("PER_UE_THP_TIME_UL"));
							//data.setTOTAL_PER_UE_THP_PDCPVOL_TRUNK_DL((Integer) record.get("PER_UE_THP_PDCPVOL_TRUNK_DL"));
							data.setTOTAL_SCHED_ACTIVITY_UE_DL(data.getTOTAL_SCHED_ACTIVITY_UE_DL()+(Long) record.get("PER_SCHED_ACTIVITY_UE_DL"));
							data.setTOTAL_SCHED_ACTIVITY_UE_UL(data.getTOTAL_SCHED_ACTIVITY_UE_UL()+(Long) record.get("PER_SCHED_ACTIVITY_UE_UL"));
							data.setTOTAL_SCHED_RESTRICT_UE_CAT_DL(data.getTOTAL_SCHED_RESTRICT_UE_CAT_DL()+(Long) record.get("PER_SCHED_RESTRICT_UE_CAT_DL"));
							data.setTOTAL_SCHED_RESTRICT_UE_CAT_UL(data.getTOTAL_SCHED_RESTRICT_UE_CAT_UL()+(Long) record.get("PER_SCHED_RESTRICT_UE_CAT_UL"));
							data.setPACKET_FWD_DL(data.getPACKET_FWD_DL()+(Long) record.get("PER_UE_PACKET_FWD_DL"));
							data.setTOTAL_RLC_ACK_DL(data.getTOTAL_RLC_ACK_DL()+(Long) record.get("PER_UE_RLC_ACK_DL"));
							data.setTOTAL_RLC_ACK_UL(data.getTOTAL_RLC_ACK_UL()+(Long) record.get("PER_UE_RLC_ACK_UL"));
							data.setTOTAL_RLC_NACK_DL(data.getTOTAL_RLC_NACK_DL()+(Long) record.get("PER_UE_RLC_NACK_DL"));
							data.setTOTAL_RLC_NACK_UL(data.getTOTAL_RLC_NACK_UL()+(Long) record.get("PER_UE_RLC_NACK_UL"));
							data.setUL_RATE_SHAPING_TIME(data.getUL_RATE_SHAPING_TIME()+(Integer) record.get("PER_UE_RATE_SHAPE_TIME_UL"));
							data.setRADIOTHP_VOL_DL(data.getRADIOTHP_VOL_DL()+(Long) record.get("RADIOTHP_VOL_DL"));
							data.setRADIO_VOL_UL(data.getRADIO_VOL_UL()+(Long) record.get("RADIOTHP_VOL_UL"));
							data.setRADIOTHP_RES_DL(data.getRADIOTHP_RES_DL()+(Long) record.get("RADIOTHP_RES_DL"));
							data.setRADIOTHP_RES_UL(data.getRADIOTHP_RES_UL()+(Long) record.get("RADIOTHP_RES_UL"));

					}

					/**
					 *  This method sets the header for the output record. This method should be executed only once per key
					 * @param output Output record of type {@link LTE_USER_SESSION}
					 * @param neName Name of the UE connected node
					 * @param globalCellId, UE connected cell in the reported session
					 * @param racUeRef, RAC reference for the current user
					 * @param enbs1ApId  Radio session id for the UE
					 * @param mmeS1Apid  core session id for the UE
					 * @param gummei global MME identifier for this session
					 * @param timestamp time of the first event reported during the UE session.
					 */
					private void setHeader(LTE_USER_SESSION_EXT output, String neName,
							Long globalCellId, Long racUeRef, Long enbs1ApId,
							Long mmeS1Apid, byte[] gummei, long timestamp) {
						// check the is already set
							
							output.setNe(neName);
							output.setCELL_ID(globalCellId&255);
							output.setENB_ID(globalCellId>>8);
							output.setENBS1APID(enbs1ApId);
							output.setMMES1APID(mmeS1Apid);
							output.setGUMMEI(new String(gummei));
							output.setSTART_TIMESTAMP(timestamp);
							output.setEND_TIMESTAMP(timestamp);
							output.setTimestamp(timestamp);
					}
				});
				/*JavaDStream<Collection<GenericRecord>> mapPartitions = map.mapPartitions(new FlatMapper());
				
				mapPartitions.foreachRDD(new BatchProcessor());*/
		
		jssc.start();
		jssc.awaitTermination();

	}

}



 