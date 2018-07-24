package app.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.javalite.activejdbc.Model;

public class Lib {
		
	public void  putToResultDirect(Model mT, Map<String, Object> result, String[] sFields){
    	for(String sfield: sFields)
    		{result.put(sfield, mT.toMap().get(sfield));}
    }
	
	public Map<String, Object> puToResult(Model mT, Map<String, Object> result, String[] sFields){
    	for(String sfield: sFields)
    		{result.put(sfield, mT.toMap().get(sfield));}
    	return result;
    }
	@SuppressWarnings("rawtypes")
	public Map<String, Object> puToResults(Map mT, Map<String, Object> result, String[] sFields){
    	for(String sfield: sFields)
    		{result.put(sfield, mT.get(sfield));}
    	return result;
    }
	public Map<String, Object> puToResult(Model mT, Map<String, Object> result, HashMap<String, String> sFields){
    	for(Entry<String, String> val: sFields.entrySet()){
			result.put(val.getKey(), mT.toMap().get(val.getValue()));
    	}
	
		return result;
    }	

}
