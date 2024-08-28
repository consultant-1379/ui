package com.ericsson.aia.asr.correlations.ups;

import com.ericsson.aia.model.eventbean.correlation.LTE_USER_SESSION;

public class LTE_USER_SESSION_EXT extends LTE_USER_SESSION {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8778993669112090707L;
	@Override
	public String toString() {
		return getCSVStringWithoutHeader();
	}
	public boolean headerSet;
	public boolean isHeaderSet() {
		return headerSet;
	}
	public void setHeader() {
		this.headerSet = true;
	}
	
}
