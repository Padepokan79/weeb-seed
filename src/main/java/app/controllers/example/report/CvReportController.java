package app.controllers.example.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.common.Convert;
import org.javalite.common.JsonHelper;

import app.models.Sdm;
import app.util.ConvertToLowerCase;
import core.javalite.controllers.ReportController;

public class CvReportController extends ReportController{

	public CvReportController() {
		// TODO nama file jrxml
		super("report-design/cetakCV.jrxml");
	}

	@Override
	public Map<String, Object> getData() throws Exception {
		//parameter
		int sdmId = Convert.toInteger(param("$sdmId"));
		
		
		List<Map> listData = new ArrayList<>();
		List<Map> listDataEduc = new ArrayList<>();
		Map<String, Object> newMapData = new HashMap<>();
		List<Map> dataFromQuery = Sdm.getDataCV(sdmId);

//		System.out.println("panjang data :"+dataFromQuery.size());
		
		//maping data
		for (Map map : dataFromQuery) {
			newMapData.put("SDM_NAME", map.get("SDM_NAME"));
			newMapData.put("GENDER_NAME", map.get("GENDER_NAME"));
			newMapData.put("RELIGION_NAME", map.get("RELIGION_NAME"));
			newMapData.put("SDM_PLACEBIRTH", map.get("SDM_PLACEBIRTH"));
			
			newMapData.put("SDM_DATEBIRTH", getConvertBulanSaja(map.get("SDM_DATEBIRTH").toString()));
			newMapData.put("HEALTH_STATUS", map.get("HEALTH_STATUS"));
			listData.add(newMapData);
		}
//		System.out.println(listData.size());
		List<Map> dataFromQueryEduc = Sdm.getDataEducation(sdmId);
		int n = 1;
		for (Map map : dataFromQueryEduc) { 
			map.put("norut", n);
			Map<String, Object> newMapDataEduc = new HashMap<>();
			newMapDataEduc.put("SDM_NAME", map.get("SDM_NAME"));
			newMapDataEduc.put("EDU_NAME", map.get("EDU_NAME"));
			
			newMapDataEduc.put("EDU_SUBJECT", map.get("EDU_SUBJECT"));
			newMapDataEduc.put("DEGREE_NAME", map.get("DEGREE_NAME"));
			newMapDataEduc.put("EDU_STARTDATE", getConvertTahunSaja(map.get("EDU_STARTDATE").toString()));
			newMapDataEduc.put("EDU_ENDDATE", getConvertTahunSaja(map.get("EDU_ENDDATE").toString()));
			listDataEduc.add(newMapDataEduc);
			
			n++;
		}
		
//		System.out.println(listDataEduc.size());
		Map<String , Object> newResult  = new HashMap<>();
		newResult.put("data", listData);
		newResult.put("dataEduc", listDataEduc);
		
		System.out.println(JsonHelper.toJsonString(newResult));
		return newResult;
	}

	public String getConvertTahunSaja(String now) {
		String bulan[] = {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","Nopember","Desember"};
		String tgl=now.substring(8, 10);
		String bln=now.substring(5, 7);
		String th= now.substring(0,4);
		String hasil =bulan[Integer.parseInt(bln)-1].toUpperCase();
		System.out.println("cek : " + th);
		return th;
	}
	
	public String getConvertBulanSaja(String now) {
		String bulan[] = {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","Nopember","Desember"};
		String tgl=now.substring(8, 10);
		String bln=now.substring(5, 7);
		String th= now.substring(0,4);
		String hasil =bulan[Integer.parseInt(bln)-1].toUpperCase()+" "+tgl+", "+th;
		System.out.println("cek : " + hasil);
		return hasil;
	}


}
