package app.controllers.example.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.common.Convert;
import org.javalite.common.JsonHelper;

import app.models.DataPegawai;
import core.javalite.controllers.ReportController;

public class CetakDataPegawaiController extends ReportController{

	public CetakDataPegawaiController() {
		super("report-design/cetakdatapegawai.jrxml");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Map<String, Object> getData() throws Exception {
		// TODO Auto-generated method stub
		String nama = Convert.toString(param("$nama"));
//		int posisi = Convert.toInteger(param("$posisi"));
		List<DataPegawai> listItem = DataPegawai.where("nama = ?", nama);
		List<Map> listIsi = new ArrayList<>();
		for (DataPegawai dataPegawai : listItem) {
			Map<String, Object> mapTamp = new HashMap<>();
			mapTamp.put("nik", dataPegawai.get("nik"));
			mapTamp.put("nama", dataPegawai.get("nama"));
			mapTamp.put("gapok", dataPegawai.get("gapok"));
			mapTamp.put("tunjangan", dataPegawai.get("tunjangan"));
			listIsi.add(mapTamp);
		}
		
		if(listIsi.size()>0) {	
			Map newResult = new HashMap<>();
			newResult.put("data", listIsi);
			System.out.println(JsonHelper.toJsonString(newResult));

			return newResult;
		}else {
			
		}
		return null;
	}

}
