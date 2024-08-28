package com.ericsson.aia.asr.correlations.ups;

import com.ericsson.aia.model.eventbean.correlation.LTE_ERAB_SESSION;

/**
 * A temporary schema for holding the session key information, this data will be
 * discarded at serialization.
 * 
 * @author eachsaj
 *
 */
public class LTE_ERAB_SESSION_EXT extends LTE_ERAB_SESSION {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4230133877697530465L;
	protected long CELL_ID = 0;
	protected long ENB_ID = 0;
	protected long ENBS1APID = 0;
	protected String GUMMEI = "";
	public boolean headerSet;
	protected long MMES1APID = 0;
	protected long RAC_UE_REF = 0;

	private long SUM_PER_DRB_DL_RLC_DELAY = 0;
	private long SUM_PER_DRB_DL_RLC_DELAY_SAMPL = 0;

	private long SUM_PER_DRB_LAT_TIME_DL = 0;
	private long SUM_PER_DRB_LAT_SAMPL_DL = 0;
	{
		
	}
	
	

	public long getCELL_ID() {
		return CELL_ID;
	}

	public long getENB_ID() {
		return ENB_ID;
	}

	public long getENBS1APID() {
		return ENBS1APID;
	}

	public String getGUMMEI() {
		return GUMMEI;
	}

	public long getMMES1APID() {
		return MMES1APID;
	}

	public long getRAC_UE_REF() {
		return RAC_UE_REF;
	}

	public boolean isHeaderSet() {
		return headerSet;
	}

	public void setCELL_ID(long cELL_ID) {
		CELL_ID = cELL_ID;
	}

	public void setENB_ID(long eNB_ID) {
		ENB_ID = eNB_ID;
	}

	public void setENBS1APID(long eNBS1APID) {
		ENBS1APID = eNBS1APID;
	}

	public void setGUMMEI(String gUMMEI) {
		GUMMEI = gUMMEI;
	}

	public void setHeader() {
		this.headerSet = true;
	}

	public void setHeaderSet(boolean headerSet) {
		this.headerSet = headerSet;
	}

	public void setMMES1APID(long mMES1APID) {
		MMES1APID = mMES1APID;
	}

	public void setRAC_UE_REF(long rAC_UE_REF) {
		RAC_UE_REF = rAC_UE_REF;
	}

	@Override
	public String toString() {
		return getDecodedString();
	}


	public long getSUM_PER_DRB_DL_RLC_DELAY() {
		return SUM_PER_DRB_DL_RLC_DELAY;
	}

	public void setSUM_PER_DRB_DL_RLC_DELAY(long sUM_PER_DRB_DL_RLC_DELAY) {
		SUM_PER_DRB_DL_RLC_DELAY = sUM_PER_DRB_DL_RLC_DELAY;
	}

	public long getSUM_PER_DRB_DL_RLC_DELAY_SAMPL() {
		return SUM_PER_DRB_DL_RLC_DELAY_SAMPL;
	}

	public void setSUM_PER_DRB_DL_RLC_DELAY_SAMPL(
			long sUM_PER_DRB_DL_RLC_DELAY_SAMPL) {
		SUM_PER_DRB_DL_RLC_DELAY_SAMPL = sUM_PER_DRB_DL_RLC_DELAY_SAMPL;
	}

	public long getSUM_PER_DRB_LAT_TIME_DL() {
		return SUM_PER_DRB_LAT_TIME_DL;
	}

	public void setSUM_PER_DRB_LAT_TIME_DL(long sUM_PER_DRB_LAT_TIME_DL) {
		SUM_PER_DRB_LAT_TIME_DL = sUM_PER_DRB_LAT_TIME_DL;
	}

	public long getSUM_PER_DRB_LAT_SAMPL_DL() {
		return SUM_PER_DRB_LAT_SAMPL_DL;
	}

	public void setSUM_PER_DRB_LAT_SAMPL_DL(long sUM_PER_DRB_LAT_SAMPL_DL) {
		SUM_PER_DRB_LAT_SAMPL_DL = sUM_PER_DRB_LAT_SAMPL_DL;
	}
	 public void postProcess(){
	        // Division done here at end to prevent loss of precision
	        RLC_DELAY_DL_PER_RB = SUM_PER_DRB_DL_RLC_DELAY/(double)SUM_PER_DRB_DL_RLC_DELAY_SAMPL;
	        LATENCY_DL_PER_RB = SUM_PER_DRB_LAT_TIME_DL/(double)SUM_PER_DRB_LAT_SAMPL_DL;
	    }
}
