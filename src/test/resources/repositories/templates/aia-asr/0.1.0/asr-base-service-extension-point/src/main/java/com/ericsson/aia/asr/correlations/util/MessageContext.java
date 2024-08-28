package com.ericsson.aia.asr.correlations.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.avro.generic.GenericRecord;

public class MessageContext {
    private int expectedEntries; 
    
	private List<GenericRecord> records = new ArrayList<>();

	public int getExpectedEntries() {
		return expectedEntries;
	}
    
	

	public List<GenericRecord> getRecords() {
		return records;
	}

	public void setRecords(GenericRecord records) {
		this.records.add(records);
	}
     
}
