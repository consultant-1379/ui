package com.ericsson.aia.asr.correlations.util;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.generic.GenericRecord;

import com.google.common.collect.ImmutableMap;



public final class Util {
	public static String getKey(GenericRecord record ){
	   
	int cellId = (Integer)record.get("GLOBAL_CELL_ID");
	return ((Integer)record.get("ENBS1APID")).toString()+((Integer)record.get("MMES1APID")).toString()
			+(cellId>>8)+((Integer)record.get("RAC_UE_REF")).toString();
	}
	public static String getCTUMKey(GenericRecord record ){
		   
		int cellId = (Integer)record.get("GLOBAL_CELL_ID");
		return ((Integer)record.get("ENB_UE_S1AP_ID")).toString()+((Integer)record.get("MME_UE_S1AP_ID")).toString()
				+(cellId>>8);
		}
 
	/**
	 * @param global_cell_id Global cell identity 
	 * @return cell id only
	 */
	public static long getLTECellId(long global_cell_id){
		return global_cell_id&255;
	}
	/**
	 * @param global_cell_id Global cell identity 
	 * @return enod B identity
	 */
	public static long getEnodeBId(long global_cell_id){
		return global_cell_id>>8;
	} 
    
	/**
	 * Method convert a delimiter separated string to String []
	 * @param data, String type data
	 * @param  Delimiter, string type delimiter
	 * @return String []
	 */
	public static String [] slitByDelimiter(String data,String delimiter){
		checkNotNull(data);
		checkNotNull(delimiter);
		if(!data.contains(delimiter)) return new String[]{data.trim()};
	    checkState(data.trim().length()!=0 || delimiter.trim().length()!=0);
		return data.trim().split(delimiter);
	}
	/**
	 * @return {@link Map} type static key value
	 */
	public static Map<Integer, String> getSourceTargetType() {
		 Map<Integer,String> source_or_target_type= new HashMap<>();
		String [] sttype = new String[]{"LTE_INTRA_FREQ" ,"LTE_INTER_FREQ","WCDMA",
				"GERAN", "CDMA2000","EVE,NT_VALUE_TDSCDMA","UTRA_SOURCE" ,
				"CDMA2000_1XRTT" 
			};
			for (int i = 0; i < sttype.length; i++) {
				String type = sttype[i];
				source_or_target_type.put(i,type );
			}
	         
		return source_or_target_type;
	}
	/**
	 * @return {@link Map} type static key value
	 */
	public static Map<Integer, String> getHOEXECIN_RES() {
		 Map<Integer,String> source_or_target_type= new HashMap<>();
		String [] sttype = new String[]{"SUCCESSFUL" ,"FAILED_TRRCCONNECTIONRECONFIGURATION_EXPIRED","FAILED_PATH_SWITCH",
				"FAILED_TIME_OUT_OF_PATH_SWITCH_REQUEST"
			};
			for (int i = 0; i < sttype.length; i++) {
				String type = sttype[i];
				source_or_target_type.put(i,type );
			}
	         
		return source_or_target_type;
	}
	/**
	 * @return {@link Map} type static key value
	 */
	public static Map<Integer, String> getPROC_HO_PREP_IN_RESULT() {
		return  ImmutableMap.<Integer, String>builder()
				.put(0,"SUCCESSFUL")
				.put(1,"FAILED_RESOURCE_RESERVATION")
				.put(2,"FAILED_LICENSE_NUMBER_OF_ACTIVE_USERS")
				.put(3,"FAILED_LICENSE_INTRA_LTE_INTRA_FREQ_HANDOVER")
				.put(4,"MULTIPLE_ERAB_LICENSE_EXCEEDED")
				.put(5,"RLCUM_LICENSE_MISSING")
				.put(6,"FAILED_MME_SELECTION")
				.put(7,"FAILED_LICENSE_INTRA_LTE_INTER_FREQ_HANDOVER")
				.put(8,"FAILED_LICENSE_CDMA2000_HANDOVER")
				.put(9,"FAILED_LICENSE_GERAN_HANDOVER")
				.put(10,"FAILED_LICENSE_WCDMA_HANDOVER")
				.put(11,"ERAB_ID_CONFLICT").build();
			
	}
	public static Map<Integer, String> getPROC_HO_PREP_OUT_RESULT() {
		 Map<Integer,String> source_or_target_type= new HashMap<>();
				source_or_target_type.put(0,"SUCCESSFUL" );
				source_or_target_type.put(0,"FAILED_HANDOVER_PREPARATION" );
				source_or_target_type.put(0,"FAILED_TRELOCPREP_EXPIRED" );
				
		return source_or_target_type;
	}
	public static String getUETac(String imeisv){
		if(imeisv==null || imeisv.isEmpty()){
			return "0";
		}
		if(imeisv.trim().length()<=8){
			return (imeisv);
		}
		return imeisv.trim().substring(0, 8);
     
	}
	/**
     * Extract all values from the array data where the values are greater, i.e. removes negative values from the array
     * @param data
     * @return array excluding negative values
     */
    public static Byte[] getSetValuesFromByteArray( final byte [] data){
        final List<Byte> result = new ArrayList<Byte>();
        for( Byte value : data ){
            if( value>=0 ){
                result.add( value );
            }
        }
        return result.toArray( new Byte[result.size()] );
    }

}
