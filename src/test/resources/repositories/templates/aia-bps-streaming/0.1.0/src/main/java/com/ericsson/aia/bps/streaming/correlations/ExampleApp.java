/**
 *
 * (C) Copyright LM Ericsson System Expertise AT/LMI, 2016
 *
 * The copyright to the computer program(s) herein is the property of Ericsson  System Expertise EEI, Sweden.
 * The program(s) may be used and/or copied only with the written permission from Ericsson System Expertise
 * AT/LMI or in  * accordance with the terms and conditions stipulated in the agreement/contract under which
 * the program(s) have been supplied.
 *
 */
package com.ericsson.aia.bps.streaming.correlations;


import org.apache.avro.generic.GenericRecord;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;

import com.ericsson.aia.bps.service.handler.SparkingStreamingHandler;
import com.ericsson.aia.bps.service.stream.spark.SparkStreams;

/**
* <h1>${metaInfo.pbaNameInCamelCase}</h1>
* The ${metaInfo.pbaNameInCamelCase} implements an application that
* simply streams messages from Kafka topic and displays it in the Spark console.
* 
*
* @author  
* @version 1.0
* @since   2016-06-07
*/
public class ${metaInfo.pbaNameInCamelCase}  extends SparkingStreamingHandler {

	private static final long serialVersionUID = 5914257776162053954L;

	@Override
	public void executeJob() {
        final SparkStreams streams = getStreams();
		
		//Gets Kafka Stream as defined in flow.xml
        JavaPairInputDStream<String, GenericRecord> stream = streams.getKafkaStream("radio-stream");
        stream.print();
		
		
		
		/*
		 *To save output to Hdfs, file, Alluxio etc as defined in flow.xml
		 *use persistRDD or persistRDDSinglePartition methods 	
		*/
		//persistRDD(JavaRDD<V>)
	}
}
