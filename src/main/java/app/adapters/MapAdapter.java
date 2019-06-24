package app.adapters;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.javalite.activejdbc.Model;

public class MapAdapter {
	
	public void  putToResultDirect(Model mT, Map<String, Object> result, String[] sFields){
    	for(String sfield: sFields)
    		{result.put(sfield, mT.toMap().get(sfield));}
    }
	
	public Map<String, Object> putToResult(Model mT, Map<String, Object> result, String[] sFields){
    	for(String sfield: sFields)
    		{result.put(sfield, mT.toMap().get(sfield));}
    	return result;
    }

	public Map<String, Object> putToResults(Map<?, ?> mT, Map<String, Object> result, String[] sFields){
    	for(String sfield: sFields)
    		{result.put(sfield, mT.get(sfield));}
    	return result;
    }
	
	public Map<String, Object> putToResult(Model mT, Map<String, Object> result, HashMap<String, String> sFields){
    	for(Entry<String, String> val: sFields.entrySet()){
			result.put(val.getKey(), mT.toMap().get(val.getValue()));
    	}
	
		return result;
    }	
	
	public static Map<String, Object> convertNullToZero(Map<String, Object> map, String... convertedField) {
		List<String> listConvertedField = Arrays.asList(convertedField);

		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() == null && listConvertedField.contains(entry.getKey())) {
				map.put(entry.getKey(), 0);
			}
		}
		
		return map;
	}

	public static Map<String, Object> convertEmptyStringToNull(Map<String, Object> map, String... convertedField) {
		List<String> listConvertedField = Arrays.asList(convertedField);

		for (Entry<String, Object> entry : map.entrySet()) {
			if ("".equals(entry.getValue()) && listConvertedField.contains(entry.getKey())) {
				map.put(entry.getKey(), null);
			}
		}
		
		return map;
	}
	
	public static Map<String, Object> convertNullStringToEmptyString(Map<String, Object> map, String... convertedField) {
		List<String> listConvertedField = Arrays.asList(convertedField);

		for (Entry<String, Object> entry : map.entrySet()) {
			if ("".equals(entry.getValue()) && listConvertedField.contains(entry.getKey())) {
				map.put(entry.getKey(), "");
			}
		}
		
		return map;
	}

	public static Map<String, Object> convertEmptyStringToZero(Map<String, Object> map, String... convertedField) {
		List<String> listConvertedField = Arrays.asList(convertedField);

		for (Entry<String, Object> entry : map.entrySet()) {
			if ("".equals(entry.getValue()) && listConvertedField.contains(entry.getKey())) {
				map.put(entry.getKey(), 0);
			}
		}
		
		return map;
	}
	
	public static Map<String, Object> convertNullOrEmptyStringToZero(Map<String, Object> map) {
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() == null || "".equals(entry.getValue())) {
				map.put(entry.getKey(), 0);
			}
		}
		
		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map> convertToLowerCaseKey(List<Map> listMap){
		List<Map> result = new ArrayList<Map>();
		
		for (Map map : listMap) {
			result.add(new CaseInsensitiveMap<String, Object>(map));
		}
		
		return result;
	}	

	public static void addHasilTanggalBatas(Map<String, Object> map, int jumlahBulan) {
		LocalDate futureDate = LocalDate.now().plusMonths(jumlahBulan);
		int month = futureDate.getMonthValue();
		int year = futureDate.getYear();  
		
		map.put("hasiltglbatas", "1" + "-" + month + "-" + year);
	}
	
	public static Map<String, Object> buildValueNameMap(String name, Object value) {
		Map<String, Object> result = new HashMap<>();
		result.put("name", name);
		result.put("value", value);
		
		return result;
	}
}
