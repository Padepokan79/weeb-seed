package app.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.CaseInsensitiveMap;

public class ConvertToLowerCase {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map> set(List<Map> listMap){
		List<Map> result = new ArrayList<Map>();
		
		for (Map map : listMap) {
			result.add(new CaseInsensitiveMap<String, Object>(map));
		}
		
		return result;
}

}
