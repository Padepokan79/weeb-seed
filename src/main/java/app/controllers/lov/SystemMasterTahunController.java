package app.controllers.lov;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.io.model.LOVModel;
import core.io.model.PagingParams;
import core.javalite.controllers.LOVController;

public class SystemMasterTahunController extends LOVController<Object> {

	@Override
	public List<Map<String,Object>> customOnLoad(PagingParams params) throws Exception{
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int yearMax = cal.get(Calendar.YEAR);
		int yearMin = cal.get(Calendar.YEAR) - 10;
			
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
			
			while ( yearMax >= yearMin) {				
				Map<String, Object> tanggal = new HashMap<String, Object>();
				tanggal.put("id", yearMax);
				tanggal.put("nama", yearMax);
				yearMax = yearMax - 1;
				result.add(tanggal);
			}			
		return result;		
	}

	@Override
	public void initListOfValueModel(LOVModel lovModel) {
		lovModel.setLovKey("id");
		lovModel.setLovValues("nama");
		
	}

}
