/*
 * File           : MengelolaProjectController.java
 * Project Name   : project
 * Project Path   : d:\xampp\htdocs\work\web-seed\src\main\java\app\controllers\project
 * ---------------
 * Author         : Rizaldi R_Nensia
 * Email          : rizaldi.95@gmail.com
 * File Created   : Tuesday, 24th July 2018 2:27:51 pm
 * ---------------
 * Modified By    : Rizaldi R_Nensia
 * Last Modified  : Friday, 27th July 2018 2:02:18 pm
 * ---------------
 * Copyright Rizaldi R_Nensia - >R<
 */




package app.controllers.project;

import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import com.google.common.base.Strings;
import com.ibm.icu.util.Calendar;

import app.controllers.allocation.MengelolaSkillController.KelolaSkill;
import app.controllers.example.crud.CustomAllInOneDTOController.CustomAllInOneDTO;
import app.models.Project;
import app.models.Sdm;
import app.models.Skill;
import app.models.SkillType;
import app.models.core.master.MasterUser;
import app.models.core.master.MasterUserActivity;

public class MengelolaProjectController extends CRUDController<Project> {
	
	public class MengelolaProject extends DTOModel{
		public int sdmId;
		public int sdmNIK;
		public String sdmName;
		public String projectName;
		public String projectDesc;
		public String projectRole;
		public String projectProjectsite;
		public String projectCustomer;
		public String projectApptype;
		public String projectServeros;
		public String projectDevlanguage;
		public String projectFramework;
		public String projectDatabase;
		public String projectAppserver;
		public String projectDevtool;
		public String projectTechnicalinfo;
		public String projectOtherinfo;
		public String projectStartdate;
		public String projectEnddate;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapProject = new ArrayList<Map<String, Object>>();
		LazyList<Project> listProject = Project.findAll();
		
		Long totalitems = this.getTotalItems(params);
		
		for(Project project : listProject) {
			Sdm sdm = project.parent(Sdm.class);
			
			MengelolaProject dto = new MengelolaProject();
			dto.fromModelMap(project.toMap());
			dto.sdmName = Convert.toString(sdm.get("sdm_name"));
			dto.projectStartdate = Convert.toString(project.get("project_startdate"));
			dto.projectEnddate = Convert.toString(project.get("project_enddate"));
			listMapProject.add(dto.toModelMap());
			
		}
		
		return new CorePage(listMapProject, totalitems);				
	}
	

	@Override
	public Project customInsertValidation(Project item) throws Exception {
		LazyList<Project> listProjVal = Project.findAll();
		String name = item.getString("project_name");
		
		for(Project project : listProjVal) {
			MengelolaProject dto = new MengelolaProject();
			dto.fromModelMap(project.toMap());
			if (name.equalsIgnoreCase(dto.projectName)) {
				Validation.required(null, "Nama project sudah ada");
			}
			
		}
		
		Validation.required(name, "Nama project harus diisi");
		return super.customInsertValidation(item);
	}
	
	
}




