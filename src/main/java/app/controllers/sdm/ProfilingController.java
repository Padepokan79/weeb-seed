/*
 * File           : ProfilingController.java
 * Project Name   : sdm
 * Project Path   : d:\xampp\htdocs\work\web-seed\src\main\java\app\controllers\sdm
 * ---------------
 * Author         : Rizaldi R_Nensia
 * Email          : rizaldi.95@gmail.com
 * File Created   : Wednesday, 25th July 2018 4:46:53 pm
 * ---------------
 * Modified By    : Rizaldi R_Nensia
 * Last Modified  : Friday, 27th July 2018 2:04:01 pm
 * ---------------
 * Copyright Rizaldi R_Nensia - >R<
 */




package app.controllers.sdm;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;


import app.models.Profiling;
import app.models.Sdm;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class ProfilingController extends CRUDController <Profiling> {
	
	public class KelolaProfiling extends DTOModel{
		public int profilingId;
		public int sdmId;
		public String sdmName;
		public String profilingName;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		List<Map<String, Object>> listMapProfiling = new ArrayList<Map<String, Object>>();
		LazyList<Profiling> listProfiling = Profiling.findAll();
		
		Long totalitems = this.getTotalItems(params);
		
		for(Profiling profiling : listProfiling) {
			Sdm sdm = profiling.parent(Sdm.class);
			
			KelolaProfiling dto = new KelolaProfiling();
			dto.fromModelMap(profiling.toMap());
			dto.fromModelMap(sdm.toMap());
			dto.sdmName = Convert.toString(sdm.get("sdm_name"));
			dto.profilingName = Convert.toString(profiling.get("profiling_name"));
			
			listMapProfiling.add(dto.toModelMap());
			
		}		
		return new CorePage(listMapProfiling, totalitems);
	}
	
}
