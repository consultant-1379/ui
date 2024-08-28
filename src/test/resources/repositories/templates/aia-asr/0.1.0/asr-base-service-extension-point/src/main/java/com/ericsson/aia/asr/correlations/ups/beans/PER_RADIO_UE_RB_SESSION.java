package com.ericsson.aia.asr.correlations.ups.beans;

import static com.ericsson.aia.model.eventbean.DefaultValues.DEFAULT_LONG_VALUE;

import org.apache.avro.generic.GenericRecord;

public class PER_RADIO_UE_RB_SESSION {
	
	

	private long ENBS1APID = DEFAULT_LONG_VALUE;
	private long GLOBAL_CELL_ID = DEFAULT_LONG_VALUE;
	private long MMES1APID = DEFAULT_LONG_VALUE;
	private long PER_DRB_PDCP_TRANSVOL_DL = DEFAULT_LONG_VALUE;
	private long PER_DRB_PDCP_ACKVOL_DL = DEFAULT_LONG_VALUE;
	private long PER_DRB_PACKET_LOST_HO_DL = DEFAULT_LONG_VALUE;
	private long PER_DRB_PACKET_LOST_PELR_DL = DEFAULT_LONG_VALUE;
	private long PER_DRB_PACKET_REC_UL = DEFAULT_LONG_VALUE;
	private long PER_DRB_PACKET_LOST_UL = DEFAULT_LONG_VALUE;
	private long PER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM = DEFAULT_LONG_VALUE;
	private long PER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM = DEFAULT_LONG_VALUE;
	private int TIMESTAMP_MINUTE=-1;
	private int RAC_UE_REF;
	
	
	private long PER_DRB_PDCP_RECVOL_UL =DEFAULT_LONG_VALUE;
	private int PER_DRB_LAT_TIME_DL =0;
	private int PER_DRB_LAT_SAMPL_DL=0;
	private int PER_DRB_DL_RLC_DELAY=0;
	private int PER_DRB_DL_RLC_DELAY_SAMPL=0;
	private int BEARER_ID=0;
	private int BEARER_TYPE=0;
	private int ERAB_ID=0;
	
	public static PER_RADIO_UE_RB_SESSION convert(GenericRecord record){
		PER_RADIO_UE_RB_SESSION object  = new PER_RADIO_UE_RB_SESSION();
		object. TIMESTAMP_MINUTE = ((Integer) record.get("TIMESTAMP_MINUTE")).byteValue();
		object.ENBS1APID = (Integer) record.get("ENBS1APID");
		object.GLOBAL_CELL_ID = (Integer) record.get("GLOBAL_CELL_ID");
		object.MMES1APID = (Integer) record.get("MMES1APID");
		object.RAC_UE_REF = (Integer) record.get("RAC_UE_REF");
		
		object.PER_DRB_PDCP_TRANSVOL_DL = (Long) record.get("PER_DRB_PDCP_TRANSVOL_DL");
		object.PER_DRB_PDCP_ACKVOL_DL = (Long) record.get("PER_DRB_PDCP_ACKVOL_DL");
		object.PER_DRB_PACKET_LOST_HO_DL = (Long) record.get("PER_DRB_PACKET_LOST_HO_DL");
		
		object.PER_DRB_PACKET_LOST_PELR_DL = (Long) record.get("PER_DRB_PACKET_LOST_PELR_DL");
		object.PER_DRB_PACKET_REC_UL = (Long) record.get("PER_DRB_PACKET_REC_UL");
		object.PER_DRB_PACKET_LOST_UL = (Long) record.get("PER_DRB_PACKET_LOST_UL");
		
		object.PER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM = (Long) record.get("PER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM");
		object.PER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM = (Long) record.get("PER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM");
		object.PER_DRB_PDCP_RECVOL_UL = (Long) record.get("PER_DRB_PDCP_RECVOL_UL");
		object.PER_DRB_LAT_TIME_DL = (Integer) record.get("PER_DRB_LAT_TIME_DL");
		object.PER_DRB_LAT_SAMPL_DL = (Integer) record.get("PER_DRB_LAT_SAMPL_DL");
		object.PER_DRB_DL_RLC_DELAY = (Integer) record.get("PER_DRB_DL_RLC_DELAY");
		object.PER_DRB_DL_RLC_DELAY_SAMPL = (Integer) record.get("PER_DRB_DL_RLC_DELAY_SAMPL");
		object.BEARER_ID = (Integer) record.get("BEARER_ID");
		object.BEARER_TYPE = (Integer) record.get("BEARER_TYPE");
		object.ERAB_ID = (Integer) record.get("ERAB_ID");
		return object;
		
	}
	public long getENBS1APID() {
		return ENBS1APID;
	}
	public void setENBS1APID(long eNBS1APID) {
		ENBS1APID = eNBS1APID;
	}
	public long getGLOBAL_CELL_ID() {
		return GLOBAL_CELL_ID;
	}
	public void setGLOBAL_CELL_ID(long gLOBAL_CELL_ID) {
		GLOBAL_CELL_ID = gLOBAL_CELL_ID;
	}
	public long getMMES1APID() {
		return MMES1APID;
	}
	public void setMMES1APID(long mMES1APID) {
		MMES1APID = mMES1APID;
	}
	public long getPER_DRB_PDCP_TRANSVOL_DL() {
		return PER_DRB_PDCP_TRANSVOL_DL;
	}
	public void setPER_DRB_PDCP_TRANSVOL_DL(long pER_DRB_PDCP_TRANSVOL_DL) {
		PER_DRB_PDCP_TRANSVOL_DL = pER_DRB_PDCP_TRANSVOL_DL;
	}
	public long getPER_DRB_PDCP_ACKVOL_DL() {
		return PER_DRB_PDCP_ACKVOL_DL;
	}
	public void setPER_DRB_PDCP_ACKVOL_DL(long pER_DRB_PDCP_ACKVOL_DL) {
		PER_DRB_PDCP_ACKVOL_DL = pER_DRB_PDCP_ACKVOL_DL;
	}
	public long getPER_DRB_PACKET_LOST_HO_DL() {
		return PER_DRB_PACKET_LOST_HO_DL;
	}
	public void setPER_DRB_PACKET_LOST_HO_DL(long pER_DRB_PACKET_LOST_HO_DL) {
		PER_DRB_PACKET_LOST_HO_DL = pER_DRB_PACKET_LOST_HO_DL;
	}
	public long getPER_DRB_PACKET_LOST_PELR_DL() {
		return PER_DRB_PACKET_LOST_PELR_DL;
	}
	public void setPER_DRB_PACKET_LOST_PELR_DL(long pER_DRB_PACKET_LOST_PELR_DL) {
		PER_DRB_PACKET_LOST_PELR_DL = pER_DRB_PACKET_LOST_PELR_DL;
	}
	public long getPER_DRB_PACKET_REC_UL() {
		return PER_DRB_PACKET_REC_UL;
	}
	public void setPER_DRB_PACKET_REC_UL(long pER_DRB_PACKET_REC_UL) {
		PER_DRB_PACKET_REC_UL = pER_DRB_PACKET_REC_UL;
	}
	public long getPER_DRB_PACKET_LOST_UL() {
		return PER_DRB_PACKET_LOST_UL;
	}
	public void setPER_DRB_PACKET_LOST_UL(long pER_DRB_PACKET_LOST_UL) {
		PER_DRB_PACKET_LOST_UL = pER_DRB_PACKET_LOST_UL;
	}
	public long getPER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM() {
		return PER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM;
	}
	public void setPER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM(long pER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM) {
		PER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM = pER_DRB_PACKET_LOST_PELR_UU_DL_RLCUM;
	}
	public long getPER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM() {
		return PER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM;
	}
	public void setPER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM(long pER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM) {
		PER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM = pER_DRB_PACKET_LOST_PELR_UU_DL_RLCAM;
	}
	public int getTIMESTAMP_MINUTE() {
		return TIMESTAMP_MINUTE;
	}
	public void setTIMESTAMP_MINUTE(int tIMESTAMP_MINUTE) {
		TIMESTAMP_MINUTE = tIMESTAMP_MINUTE;
	}
	public int getRAC_UE_REF() {
		return RAC_UE_REF;
	}
	public void setRAC_UE_REF(int rAC_UE_REF) {
		RAC_UE_REF = rAC_UE_REF;
	}
	public long getPER_DRB_PDCP_RECVOL_UL() {
		return PER_DRB_PDCP_RECVOL_UL;
	}
	public void setPER_DRB_PDCP_RECVOL_UL(long pER_DRB_PDCP_RECVOL_UL) {
		PER_DRB_PDCP_RECVOL_UL = pER_DRB_PDCP_RECVOL_UL;
	}
	public int getPER_DRB_LAT_TIME_DL() {
		return PER_DRB_LAT_TIME_DL;
	}
	public void setPER_DRB_LAT_TIME_DL(int pER_DRB_LAT_TIME_DL) {
		PER_DRB_LAT_TIME_DL = pER_DRB_LAT_TIME_DL;
	}
	public int getPER_DRB_LAT_SAMPL_DL() {
		return PER_DRB_LAT_SAMPL_DL;
	}
	public void setPER_DRB_LAT_SAMPL_DL(int pER_DRB_LAT_SAMPL_DL) {
		PER_DRB_LAT_SAMPL_DL = pER_DRB_LAT_SAMPL_DL;
	}
	public int getPER_DRB_DL_RLC_DELAY() {
		return PER_DRB_DL_RLC_DELAY;
	}
	public void setPER_DRB_DL_RLC_DELAY(int pER_DRB_DL_RLC_DELAY) {
		PER_DRB_DL_RLC_DELAY = pER_DRB_DL_RLC_DELAY;
	}
	public int getPER_DRB_DL_RLC_DELAY_SAMPL() {
		return PER_DRB_DL_RLC_DELAY_SAMPL;
	}
	public void setPER_DRB_DL_RLC_DELAY_SAMPL(int pER_DRB_DL_RLC_DELAY_SAMPL) {
		PER_DRB_DL_RLC_DELAY_SAMPL = pER_DRB_DL_RLC_DELAY_SAMPL;
	}
	public int getBEARER_ID() {
		return BEARER_ID;
	}
	public void setBEARER_ID(int bEARER_ID) {
		BEARER_ID = bEARER_ID;
	}
	public int getBEARER_TYPE() {
		return BEARER_TYPE;
	}
	public void setBEARER_TYPE(int bEARER_TYPE) {
		BEARER_TYPE = bEARER_TYPE;
	}
	public int getERAB_ID() {
		return ERAB_ID;
	}
	public void setERAB_ID(int eRAB_ID) {
		ERAB_ID = eRAB_ID;
	}

	

}
