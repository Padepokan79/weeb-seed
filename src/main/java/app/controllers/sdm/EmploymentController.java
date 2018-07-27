/*
 * File           : EmploymentController.java
 * Project Name   : sdm
 * Project Path   : d:\xampp\htdocs\work\web-seed\src\main\java\app\controllers\sdm
 * ---------------
 * Author         : Rizaldi R_Nensia
 * Email          : rizaldi.95@gmail.com
 * File Created   : Wednesday, 25th July 2018 4:44:09 pm
 * ---------------
 * Modified By    : Rizaldi R_Nensia
 * Last Modified  : Friday, 27th July 2018 2:03:07 pm
 * ---------------
 * Copyright Rizaldi R_Nensia - >R<
 */




package app.controllers.sdm;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

import app.models.Sdm;
import app.models.Employment;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class EmploymentController extends CRUDController<Employment>{
	public class KelolaEmployment extends DTOModel{
		public int employmentId;
		public int sdmId;
		public String sdmName;
		public String employmentCorpname;
		public String employmentStartdate;
		public String employmentEnddate;
		public String employmentRolejob;		
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		List<Map<String, Object>> listMapEmployment = new ArrayList<Map<String, Object>>();
		LazyList<Employment> listEmployment = Employment.findAll();
		
		Long totalitems = this.getTotalItems(params);
		
		for(Employment employment : listEmployment) {
			Sdm sdm = employment.parent(Sdm.class);
			
			KelolaEmployment dto = new KelolaEmployment();
			dto.fromModelMap(employment.toMap());
			dto.fromModelMap(sdm.toMap());
			dto.sdmName = Convert.toString(sdm.get("sdm_name"));
			dto.employmentStartdate = Convert.toString("employment_startdate");
			dto.employmentEnddate = Convert.toString("employment_enddate");
			listMapEmployment.add(dto.toModelMap());
			
		}		
		return new CorePage(listMapEmployment, totalitems);
	}
}
