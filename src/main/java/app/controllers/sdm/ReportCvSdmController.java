package app.controllers.sdm;
import java.io.File;
/**
 * @author NURDHIAT CHAUDHARY MALIK
 * @date   25 July 2018
 * @update 26 July 2018 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.common.Convert;
import org.javalite.common.JsonHelper;
import org.jaxen.function.SubstringFunction;

import app.models.Sdm;
import app.util.ConvertToLowerCase;
import core.javalite.controllers.ReportController;

public class ReportCvSdmController extends ReportController{

	public ReportCvSdmController() {
		super("report-design/ReportCvSdm.jrxml");
	}

	@Override
	public Map<String, Object> getData() throws Exception {

		int sdmId = Convert.toInteger(param("$sdmId"));
		int nourut;
		String imgPhoto = ""; 
		
		List<Map> listData = new ArrayList<>();
		List<Map> listDataLanguage = new ArrayList<>();
		List<Map> listDataProfiling = new ArrayList<>();
		List<Map> listDataSkillSdm = new ArrayList<>();
		List<Map> listDataEduc = new ArrayList<>();
		List<Map> listDataCourse = new ArrayList<>();
		List<Map> listDataEmployment = new ArrayList<>();
		List<Map> listDataProject = new ArrayList<>();
		
		Map<String, Object> tampil = new HashMap<>();
		
		List<Map> dataFromQuery = Sdm.getDataCV(sdmId);
		for (Map map : dataFromQuery) {
//			tampil.put("HEALTH_STATUS", map.get("HEALTH_STATUS"));
			map.put("SDM_DATEBIRTH", getConvertBulan(map.get("SDM_DATEBIRTH").toString()));
			
			//report foto pada cv
			imgPhoto = map.get("SDM_IMAGE") == "" ? null : Convert.toString(map.get("SDM_IMAGE"));
			File folder = new File ("assets/" + imgPhoto );
			if (!folder.exists()) {
				map.put("SDM_IMAGE","assets/no-photo.png");
			}else {
				map.put("SDM_IMAGE", "assets/" + imgPhoto );
			}
			
			listData.add(map);
		}
		
		List<Map> dataFromQueryLanguage = Sdm.getDataLanguage(sdmId);
		for (Map map : dataFromQueryLanguage) { 
			listDataLanguage.add(map);
		}
		
		List<Map> dataFromQueryProfiling = Sdm.getDataProfiling(sdmId);
		for (Map map : dataFromQueryProfiling) { 
			listDataProfiling.add(map);
		}
		
		List<Map> dataFromQuerySkillSdm = Sdm.getDataSkillSdm(sdmId);
		nourut = 1;
		for (Map map : dataFromQuerySkillSdm) { 
			map.put("nourut", nourut);
			listDataSkillSdm.add(map);
			nourut++;
		}
		
		List<Map> dataFromQueryEduc = Sdm.getDataEducation(sdmId);
		nourut = 1;
		for (Map map : dataFromQueryEduc) { 
			String startdate = Convert.toString(map.get("EDU_STARTDATE"));
			String enddate = Convert.toString(map.get("EDU_ENDDATE"));
			map.put("nourut", nourut);
			map.put("EDU_STARTDATE", startdate.substring(0, 4));
			map.put("EDU_ENDDATE", enddate.substring(0, 4));
			listDataEduc.add(map);
			nourut++;
		}
		
		List<Map> dataFromQueryCourse = Sdm.getDataCourse(sdmId);
		nourut = 1;
		for (Map map : dataFromQueryCourse) {
			map.put("nourut", nourut);
			listDataCourse.add(map);
			nourut++;
		}
		
		List<Map> dataFromQueryEmployment = Sdm.getDataEmployment(sdmId);
		nourut = 1;
		for (Map map : dataFromQueryEmployment) { 
			map.put("nourut", nourut);
			listDataEmployment.add(map);
			nourut++;
		}
		
		List<Map> dataFromQueryProject = Sdm.getDataProject(sdmId);
		nourut = 1;
		for (Map map : dataFromQueryProject) { 
			map.put("nourut", nourut);
			listDataProject.add(map);
			nourut++;
		}
		
		Map<String , Object> dataTampil  = new HashMap<>();
		dataTampil.put("data", listData);
		dataTampil.put("dataLanguage", listDataLanguage);
		dataTampil.put("dataProfiling", listDataProfiling);
		dataTampil.put("dataSkillSdm", listDataSkillSdm);
		dataTampil.put("dataEduc", listDataEduc);
		dataTampil.put("dataCourse", listDataCourse);
		dataTampil.put("dataEmployment", listDataEmployment);
		dataTampil.put("dataProject", listDataProject);
		
		System.out.println(JsonHelper.toJsonString(dataTampil));
		return dataTampil;
	}
		public String getConvertBulan(String now) {
			String bulan[] = {"Januari","Februari","Maret","April","Mei","Juni","Juli","Agustus","September","Oktober","Nopember","Desember"};
			String tanggal = now.substring(8,10);
			String bln = now.substring(5,7);
			String tahun = now.substring(0,4);
			String hasil =bulan[Integer.parseInt(bln)-1]+" "+tanggal+", "+tahun;
			System.out.println("cek : " + hasil);
			return hasil;
		}

}
