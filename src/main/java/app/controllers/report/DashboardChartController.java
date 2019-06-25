package app.controllers.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.javalite.activeweb.annotations.GET;
import org.javalite.common.Convert;
import app.models.SdmHiring;
import core.javalite.controllers.CommonController;

public class DashboardChartController extends CommonController {
	
	@SuppressWarnings("unchecked")
	@GET
	public void getData() {
		int tahun = Convert.toInteger(param("$tahun"));
		int chartDataType = Convert.toInteger(param("$chartDataType"));
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			
			switch (chartDataType) {
				case 1 : 
					result = summaryTalentOnSite(tahun);
					break;
				case 2 : 
					result = summaryTalentOnTest(tahun);
					break;
				case 3: 
					result = summaryTalentPassedTest(tahun);
					break;
				case 4: 
					result = summaryTalentFailedTest(tahun);
					break;
				case 5: 
					result = summaryTalentByHireState(tahun);
					break;
				default: 
					break;
			}
			
			
			response().setData(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		sendResponse();
	}
	
	public Map summaryTalentOnSite(int tahun) {
		Map result = new HashMap<String, Object>();
		List<Map> listResult = new ArrayList<Map>();
		List<Map> listData = new ArrayList<>();
		try {
			listData = SdmHiring.summaryTalentOnSite(tahun);
			for (Map data : listData) {
				Map map = new HashMap<>();
				map.put("name", data.get("CLIENT_NAME"));
				map.put("value", data.get("JML"));
				listResult.add(map);
			}
			result.put("series", listResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Map summaryTalentOnTest(int tahun) {
		Map result = new HashMap<String, Object>();
		List<Map> listResult = new ArrayList<Map>();
		List<Map> listData = new ArrayList<>();
		try {
			listData = SdmHiring.summaryTalentOnTest(tahun);
			for (Map data : listData) {
				Map map = new HashMap<>();
				map.put("name", data.get("CLIENT_NAME"));
				map.put("value", data.get("JML"));
				listResult.add(map);
			}
			result.put("series", listResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Map summaryTalentPassedTest(int tahun) {
		Map result = new HashMap<String, Object>();
		List<Map> listResult = new ArrayList<Map>();
		List<Map> listData = new ArrayList<>();
		try {
			listData = SdmHiring.summaryTalentPassedTest(tahun);
			for (Map data : listData) {
				Map map = new HashMap<>();
				map.put("name", data.get("CLIENT_NAME"));
				map.put("value", data.get("JML"));
				listResult.add(map);
			}
			result.put("series", listResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Map summaryTalentFailedTest(int tahun) {
		Map result = new HashMap<String, Object>();
		List<Map> listResult = new ArrayList<Map>();
		List<Map> listData = new ArrayList<>();
		try {
			listData = SdmHiring.summaryTalentFailedTest(tahun);
			for (Map data : listData) {
				Map map = new HashMap<>();
				map.put("name", data.get("CLIENT_NAME"));
				map.put("value", data.get("JML"));
				listResult.add(map);
			}
			result.put("series", listResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Map summaryTalentByHireState(int tahun) {
		Map result = new HashMap<String, Object>();
		List<Map> listResult = new ArrayList<Map>();
		List<Map> listAccept = new ArrayList<Map>();
		List<Map> listOff = new ArrayList<Map>();
		List<Map> listData = new ArrayList<>();
		try {
			listData = SdmHiring.summaryTalentByHireState(tahun);
			for (Map data : listData) {
				Map map = new HashMap<>();
				map.put("name", data.get("CLIENT_NAME"));
				map.put("value", data.get("JML"));
				map.put("state", data.get("HIRESTAT_NAME"));
				listResult.add(map);
			}
			System.out.println(listData);
			if (!listResult.isEmpty()) {
				listAccept = listResult.stream()
						 .filter(data -> data.get("state").equals("Diterima"))
						 .collect(Collectors.toList());
				listOff = listResult.stream()
						 .filter(data -> data.get("state").equals("Off"))
						 .collect(Collectors.toList());
			}
			result.put("series_accept", listAccept);
			result.put("series_off", listOff);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
