package com.ericsson.aia.asr.correlations.ups.beans;

import static com.ericsson.aia.model.eventbean.DefaultValues.DEFAULT_LONG_VALUE;

import org.apache.avro.generic.GenericRecord;

public class PER_RADIO_UE_SESSION {

	private long ENBS1APID = DEFAULT_LONG_VALUE;
	private long GLOBAL_CELL_ID = DEFAULT_LONG_VALUE;
	private long MMES1APID = DEFAULT_LONG_VALUE;
	private long PER_SCHED_ACTIVITY_UE_DL = DEFAULT_LONG_VALUE;
	private long PER_SCHED_ACTIVITY_UE_UL = DEFAULT_LONG_VALUE;
	private long PER_SCHED_RESTRICT_UE_CAT_DL = DEFAULT_LONG_VALUE;
	private long PER_SCHED_RESTRICT_UE_CAT_UL = DEFAULT_LONG_VALUE;
	private long PER_UE_PACKET_FWD_DL = DEFAULT_LONG_VALUE;
	private long PER_UE_RATE_SHAPE_TIME_UL = DEFAULT_LONG_VALUE;
	private long PER_UE_RLC_ACK_DL = DEFAULT_LONG_VALUE;
	private long PER_UE_RLC_ACK_UL = DEFAULT_LONG_VALUE;
	private long PER_UE_RLC_NACK_DL = DEFAULT_LONG_VALUE;
	private long PER_UE_RLC_NACK_UL = DEFAULT_LONG_VALUE;
	private long PER_UE_THP_DL_DRB = DEFAULT_LONG_VALUE;
	private long PER_UE_THP_PDCPVOL_TRUNK_DL = DEFAULT_LONG_VALUE;
	private long PER_UE_THP_PDCPVOL_TRUNK_UL = DEFAULT_LONG_VALUE;
	private long PER_UE_THP_TIME_DL = DEFAULT_LONG_VALUE;
	private long PER_UE_THP_TIME_UL = DEFAULT_LONG_VALUE;
	private long PER_UE_THP_UL_DRB = DEFAULT_LONG_VALUE;
	private long RAC_UE_REF = DEFAULT_LONG_VALUE;
	private long RADIOTHP_RES_DL = DEFAULT_LONG_VALUE;
	private long RADIOTHP_RES_UL = DEFAULT_LONG_VALUE;
	private long RADIOTHP_VOL_DL = DEFAULT_LONG_VALUE;
	private long RADIOTHP_VOL_UL = DEFAULT_LONG_VALUE;
	private   byte TIMESTAMP_MINUTE = 0;
	public static PER_RADIO_UE_SESSION convert(GenericRecord record){
		PER_RADIO_UE_SESSION object  = new PER_RADIO_UE_SESSION();
		object. TIMESTAMP_MINUTE = ((Integer) record.get("TIMESTAMP_MINUTE")).byteValue();
		object.ENBS1APID = (Integer) record.get("ENBS1APID");
		object.GLOBAL_CELL_ID = (Integer) record.get("GLOBAL_CELL_ID");
		object.MMES1APID = (Integer) record.get("MMES1APID");
		
		object.PER_SCHED_ACTIVITY_UE_DL = (Integer) record.get("PER_SCHED_ACTIVITY_UE_DL");
		object.PER_SCHED_ACTIVITY_UE_UL = (Integer) record.get("PER_SCHED_ACTIVITY_UE_UL");
		object.PER_SCHED_RESTRICT_UE_CAT_DL = (Integer) record.get("PER_SCHED_RESTRICT_UE_CAT_DL");
		
		object.PER_SCHED_RESTRICT_UE_CAT_UL = (Integer) record.get("PER_SCHED_RESTRICT_UE_CAT_UL");
		object.PER_UE_PACKET_FWD_DL = (Long) record.get("PER_UE_PACKET_FWD_DL");
		object.PER_UE_RATE_SHAPE_TIME_UL = (Integer) record.get("PER_UE_RATE_SHAPE_TIME_UL");
		
		object.PER_UE_RLC_ACK_DL = (Long) record.get("PER_UE_RLC_ACK_DL");
		object.PER_UE_RLC_ACK_UL = (Long) record.get("PER_UE_RLC_ACK_UL");
		object.PER_UE_RLC_NACK_DL = (Long) record.get("PER_UE_RLC_NACK_DL");
		
		object.PER_UE_RLC_NACK_UL = (Long) record.get("PER_UE_RLC_NACK_UL");
		object.PER_UE_THP_DL_DRB = (Integer) record.get("PER_UE_THP_DL_DRB");
		object.PER_UE_THP_PDCPVOL_TRUNK_DL = (Long) record.get("PER_UE_THP_PDCPVOL_TRUNK_DL");
		
		object.PER_UE_THP_PDCPVOL_TRUNK_UL = (Long) record.get("PER_UE_THP_PDCPVOL_TRUNK_UL");
		object.PER_UE_THP_TIME_DL = (Integer) record.get("PER_UE_THP_TIME_DL");
		object.PER_UE_THP_TIME_UL = (Integer) record.get("PER_UE_THP_TIME_UL");
		object.PER_UE_THP_UL_DRB = (Integer) record.get("PER_UE_THP_UL_DRB");
		object.RAC_UE_REF = (Integer) record.get("RAC_UE_REF");
		object.RADIOTHP_RES_DL = (Long) record.get("RADIOTHP_RES_DL");	
		object.RADIOTHP_RES_UL = (Long) record.get("RADIOTHP_RES_UL");
		object.RADIOTHP_VOL_DL = (Long) record.get("RADIOTHP_VOL_DL");
		object.RADIOTHP_VOL_UL = (Long) record.get("RADIOTHP_VOL_UL");	

		/*data.setUE_TRAF_REP_SAMPLES(data.getUE_TRAF_REP_SAMPLES()+1);
		int per_UE_THP_TIME_DL = (Integer) record.get("PER_UE_THP_TIME_DL");
		per_UE_THP_TIME_DL =per_UE_THP_TIME_DL >0?per_UE_THP_TIME_DL:1;// to avoid division by zero
        double vol_THP_DL= ((Integer) record.get("PER_UE_THP_DL_DRB"))/8.0*per_UE_THP_TIME_DL;
        data.setVOL_THP_DL( data.getVOL_THP_DL()+vol_THP_DL);
		long per_UE_THP_TIME_UL = (Integer) record.get("PER_UE_THP_TIME_UL");
		per_UE_THP_TIME_UL =per_UE_THP_TIME_UL >0?per_UE_THP_TIME_UL:1;// to avoid division by zero
        double vol_THP_UL=((Integer) record.get("PER_UE_THP_UL_DRB"))/8.0*per_UE_THP_TIME_UL;
        data.setVOL_THP_UL(data.getVOL_THP_UL()+vol_THP_UL);
        data.setVOL_TOTAL_DL(data.getVOL_TOTAL_DL()+(vol_THP_DL+(Long) record.get("PER_UE_THP_PDCPVOL_TRUNK_DL"))); // 1084418.375 + 3223716.0 + 1539244.75
        Double d = (Double) (data.getVOL_TOTAL_UL()+(vol_THP_UL+(Long) record.get("PER_UE_THP_PDCPVOL_TRUNK_UL")));
		data.setVOL_TOTAL_UL(d.longValue());
		
        data.setTIME_THP_DL(data.getTIME_THP_DL()+(Integer) record.get("PER_UE_THP_TIME_DL"));
        data.setTIME_THP_UL( data.getTIME_THP_UL()+(Integer) record.get("PER_UE_THP_TIME_UL"));
		//data.setTOTAL_PER_UE_THP_PDCPVOL_TRUNK_DL((Integer) record.get("PER_UE_THP_PDCPVOL_TRUNK_DL"));
		data.setTOTAL_SCHED_ACTIVITY_UE_DL(data.getTOTAL_SCHED_ACTIVITY_UE_DL()+(Integer) record.get("PER_SCHED_ACTIVITY_UE_DL"));
		data.setTOTAL_SCHED_ACTIVITY_UE_UL(data.getTOTAL_SCHED_ACTIVITY_UE_UL()+(Integer) record.get("PER_SCHED_ACTIVITY_UE_UL"));
		data.setTOTAL_SCHED_RESTRICT_UE_CAT_DL(data.getTOTAL_SCHED_RESTRICT_UE_CAT_DL()+(Integer) record.get("PER_SCHED_RESTRICT_UE_CAT_DL"));
		data.setTOTAL_SCHED_RESTRICT_UE_CAT_UL(data.getTOTAL_SCHED_RESTRICT_UE_CAT_UL()+(Integer) record.get("PER_SCHED_RESTRICT_UE_CAT_UL"));
		data.setPACKET_FWD_DL(data.getPACKET_FWD_DL()+(Long) record.get("PER_UE_PACKET_FWD_DL"));
		data.setTOTAL_RLC_ACK_DL(data.getTOTAL_RLC_ACK_DL()+(Long) record.get("PER_UE_RLC_ACK_DL"));
		data.setTOTAL_RLC_ACK_UL(data.getTOTAL_RLC_ACK_UL()+(Long) record.get("PER_UE_RLC_ACK_UL"));
		data.setTOTAL_RLC_NACK_DL(data.getTOTAL_RLC_NACK_DL()+(Long) record.get("PER_UE_RLC_NACK_DL"));
		data.setTOTAL_RLC_NACK_UL(data.getTOTAL_RLC_NACK_UL()+(Long) record.get("PER_UE_RLC_NACK_UL"));
		data.setUL_RATE_SHAPING_TIME(data.getUL_RATE_SHAPING_TIME()+(Integer) record.get("PER_UE_RATE_SHAPE_TIME_UL"));
		data.setRADIOTHP_VOL_DL(data.getRADIOTHP_VOL_DL()+(Long) record.get("RADIOTHP_VOL_DL"));
		data.setRADIO_VOL_UL(data.getRADIO_VOL_UL()+(Long) record.get("RADIOTHP_VOL_UL"));
		data.setRADIOTHP_RES_DL(data.getRADIOTHP_RES_DL()+(Long) record.get("RADIOTHP_RES_DL"));
		data.setRADIOTHP_RES_UL(data.getRADIOTHP_RES_UL()+(Long) record.get("RADIOTHP_RES_UL"));*/
		return object;
	}

	public long getENBS1APID() {
		return ENBS1APID;
	}

	public long getGLOBAL_CELL_ID() {
		return GLOBAL_CELL_ID;
	}

	public long getMMES1APID() {
		return MMES1APID;
	}

	public long getPER_SCHED_ACTIVITY_UE_DL() {
		return PER_SCHED_ACTIVITY_UE_DL;
	}

	public long getPER_SCHED_ACTIVITY_UE_UL() {
		return PER_SCHED_ACTIVITY_UE_UL;
	}

	public long getPER_SCHED_RESTRICT_UE_CAT_DL() {
		return PER_SCHED_RESTRICT_UE_CAT_DL;
	}

	public long getPER_SCHED_RESTRICT_UE_CAT_UL() {
		return PER_SCHED_RESTRICT_UE_CAT_UL;
	}

	public long getPER_UE_PACKET_FWD_DL() {
		return PER_UE_PACKET_FWD_DL;
	}

	public long getPER_UE_RATE_SHAPE_TIME_UL() {
		return PER_UE_RATE_SHAPE_TIME_UL;
	}

	public long getPER_UE_RLC_ACK_DL() {
		return PER_UE_RLC_ACK_DL;
	}

	public long getPER_UE_RLC_ACK_UL() {
		return PER_UE_RLC_ACK_UL;
	}

	public long getPER_UE_RLC_NACK_DL() {
		return PER_UE_RLC_NACK_DL;
	}

	public long getPER_UE_RLC_NACK_UL() {
		return PER_UE_RLC_NACK_UL;
	}

	public long getPER_UE_THP_DL_DRB() {
		return PER_UE_THP_DL_DRB;
	}

	public long getPER_UE_THP_PDCPVOL_TRUNK_DL() {
		return PER_UE_THP_PDCPVOL_TRUNK_DL;
	}

	public long getPER_UE_THP_PDCPVOL_TRUNK_UL() {
		return PER_UE_THP_PDCPVOL_TRUNK_UL;
	}

	public long getPER_UE_THP_TIME_DL() {
		return PER_UE_THP_TIME_DL;
	}

	public long getPER_UE_THP_TIME_UL() {
		return PER_UE_THP_TIME_UL;
	}

	public long getPER_UE_THP_UL_DRB() {
		return PER_UE_THP_UL_DRB;
	}

	public long getRAC_UE_REF() {
		return RAC_UE_REF;
	}

	public long getRADIOTHP_RES_DL() {
		return RADIOTHP_RES_DL;
	}

	public long getRADIOTHP_RES_UL() {
		return RADIOTHP_RES_UL;
	}

	public long getRADIOTHP_VOL_DL() {
		return RADIOTHP_VOL_DL;
	}

	public long getRADIOTHP_VOL_UL() {
		return RADIOTHP_VOL_UL;
	}

	public byte getTIMESTAMP_MINUTE() {
		return TIMESTAMP_MINUTE;
	}

	public void setENBS1APID(long eNBS1APID) {
		ENBS1APID = eNBS1APID;
	}

	public void setGLOBAL_CELL_ID(long gLOBAL_CELL_ID) {
		GLOBAL_CELL_ID = gLOBAL_CELL_ID;
	}

	public void setMMES1APID(long mMES1APID) {
		MMES1APID = mMES1APID;
	}

	public void setPER_SCHED_ACTIVITY_UE_DL(long pER_SCHED_ACTIVITY_UE_DL) {
		PER_SCHED_ACTIVITY_UE_DL = pER_SCHED_ACTIVITY_UE_DL;
	}

	public void setPER_SCHED_ACTIVITY_UE_UL(long pER_SCHED_ACTIVITY_UE_UL) {
		PER_SCHED_ACTIVITY_UE_UL = pER_SCHED_ACTIVITY_UE_UL;
	}

	public void setPER_SCHED_RESTRICT_UE_CAT_DL(long pER_SCHED_RESTRICT_UE_CAT_DL) {
		PER_SCHED_RESTRICT_UE_CAT_DL = pER_SCHED_RESTRICT_UE_CAT_DL;
	}

	public void setPER_SCHED_RESTRICT_UE_CAT_UL(long pER_SCHED_RESTRICT_UE_CAT_UL) {
		PER_SCHED_RESTRICT_UE_CAT_UL = pER_SCHED_RESTRICT_UE_CAT_UL;
	}

	public void setPER_UE_PACKET_FWD_DL(long pER_UE_PACKET_FWD_DL) {
		PER_UE_PACKET_FWD_DL = pER_UE_PACKET_FWD_DL;
	}

	public void setPER_UE_RATE_SHAPE_TIME_UL(long pER_UE_RATE_SHAPE_TIME_UL) {
		PER_UE_RATE_SHAPE_TIME_UL = pER_UE_RATE_SHAPE_TIME_UL;
	}

	public void setPER_UE_RLC_ACK_DL(long pER_UE_RLC_ACK_DL) {
		PER_UE_RLC_ACK_DL = pER_UE_RLC_ACK_DL;
	}

	public void setPER_UE_RLC_ACK_UL(long pER_UE_RLC_ACK_UL) {
		PER_UE_RLC_ACK_UL = pER_UE_RLC_ACK_UL;
	}

	public void setPER_UE_RLC_NACK_DL(long pER_UE_RLC_NACK_DL) {
		PER_UE_RLC_NACK_DL = pER_UE_RLC_NACK_DL;
	}

	public void setPER_UE_RLC_NACK_UL(long pER_UE_RLC_NACK_UL) {
		PER_UE_RLC_NACK_UL = pER_UE_RLC_NACK_UL;
	}

	public void setPER_UE_THP_DL_DRB(long pER_UE_THP_DL_DRB) {
		PER_UE_THP_DL_DRB = pER_UE_THP_DL_DRB;
	}

	public void setPER_UE_THP_PDCPVOL_TRUNK_DL(long pER_UE_THP_PDCPVOL_TRUNK_DL) {
		PER_UE_THP_PDCPVOL_TRUNK_DL = pER_UE_THP_PDCPVOL_TRUNK_DL;
	}

	public void setPER_UE_THP_PDCPVOL_TRUNK_UL(long pER_UE_THP_PDCPVOL_TRUNK_UL) {
		PER_UE_THP_PDCPVOL_TRUNK_UL = pER_UE_THP_PDCPVOL_TRUNK_UL;
	}

	public void setPER_UE_THP_TIME_DL(long pER_UE_THP_TIME_DL) {
		PER_UE_THP_TIME_DL = pER_UE_THP_TIME_DL;
	}

	public void setPER_UE_THP_TIME_UL(long pER_UE_THP_TIME_UL) {
		PER_UE_THP_TIME_UL = pER_UE_THP_TIME_UL;
	}

	public void setPER_UE_THP_UL_DRB(long pER_UE_THP_UL_DRB) {
		PER_UE_THP_UL_DRB = pER_UE_THP_UL_DRB;
	}

	public void setRAC_UE_REF(long rAC_UE_REF) {
		RAC_UE_REF = rAC_UE_REF;
	}

	public void setRADIOTHP_RES_DL(long rADIOTHP_RES_DL) {
		RADIOTHP_RES_DL = rADIOTHP_RES_DL;
	}

	public void setRADIOTHP_RES_UL(long rADIOTHP_RES_UL) {
		RADIOTHP_RES_UL = rADIOTHP_RES_UL;
	}

	public void setRADIOTHP_VOL_DL(long rADIOTHP_VOL_DL) {
		RADIOTHP_VOL_DL = rADIOTHP_VOL_DL;
	}

	public void setRADIOTHP_VOL_UL(long rADIOTHP_VOL_UL) {
		RADIOTHP_VOL_UL = rADIOTHP_VOL_UL;
	}

	public void setTIMESTAMP_MINUTE(byte tIMESTAMP_MINUTE) {
		TIMESTAMP_MINUTE = tIMESTAMP_MINUTE;
	}

}
