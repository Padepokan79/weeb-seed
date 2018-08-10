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
		public int projectId;
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
		params.setOrderBy("project_id");
		Long totalitems = this.getTotalItems(params);
	
		for(Project project : listProject) {
			Sdm sdm = project.parent(Sdm.class);		
			MengelolaProject dto = new MengelolaProject();
			dto.fromModelMap(project.toMap());
			dto.sdmName 				= Convert.toString(sdm.get("sdm_name"));
			dto.projectName 			= Convert.toString(project.get("project_name"));
			dto.projectDesc 			= Convert.toString(project.get("project_desc"));			
			dto.projectRole 			= Convert.toString(project.get("project_role"));			
			dto.projectApptype 			= Convert.toString(project.get("project_apptype"));			
			dto.projectDevtool 			= Convert.toString(project.get("project_devtool"));
			dto.projectEnddate 			= Convert.toString(project.get("project_enddate"));
			dto.projectDatabase 		= Convert.toString(project.get("project_database"));			
			dto.projectCustomer 		= Convert.toString(project.get("project_customer"));			
			dto.projectServeros 		= Convert.toString(project.get("project_serveros"));			
			dto.projectFramework 		= Convert.toString(project.get("project_framework"));
			dto.projectAppserver 		= Convert.toString(project.get("project_appserver"));			
			dto.projectOtherinfo 		= Convert.toString(project.get("project_otherinfo"));
			dto.projectStartdate 		= Convert.toString(project.get("project_startdate"));
			dto.projectDevlanguage 		= Convert.toString(project.get("project_devlanguage"));
			dto.projectProjectsite 		= Convert.toString(project.get("project_projectsite"));			
			dto.projectTechnicalinfo 	= Convert.toString(project.get("project_technicalinfo"));
			
			listMapProject.add(dto.toModelMap());
		}
		return new CorePage(listMapProject, totalitems);				
	}
	
	// @Override
	// public Map<String, Object> customOnInsert(SdmHiring item, Map<String, Object> mapRequest) throws Exception {
	// 	Map<String, Object> result = super.customOnInsert(item, mapRequest);
	// 	ProjectDTO dto = new ProjectDTO();
	// 	dto.fromModelMap(result);
		
	// 	Project project = item.parent(Project.class);
	// 	Sdm sdm = item.parent(Sdm.class);
	// 	dto.sdmId = Convert.toInteger(sdm.get("sdm_id"));
	// 	dto.projectName 			= Convert.toString(project.get("project_name"));
	// 	dto.projectDesc 			= Convert.toString(project.get("project_desc"));			
	// 	dto.projectRole 			= Convert.toString(project.get("project_role"));			
	// 	dto.projectApptype 			= Convert.toString(project.get("project_apptype"));			
	// 	dto.projectDevtool 			= Convert.toString(project.get("project_devtool"));
	// 	dto.projectEnddate 			= Convert.toString(project.get("project_enddate"));
	// 	dto.projectDatabase 		= Convert.toString(project.get("project_database"));			
	// 	dto.projectCustomer 		= Convert.toString(project.get("project_customer"));			
	// 	dto.projectServeros 		= Convert.toString(project.get("project_serveros"));			
	// 	dto.projectFramework 		= Convert.toString(project.get("project_framework"));
	// 	dto.projectAppserver 		= Convert.toString(project.get("project_appserver"));			
	// 	dto.projectOtherinfo 		= Convert.toString(project.get("project_otherinfo"));
	// 	dto.projectStartdate 		= Convert.toString(project.get("project_startdate"));
	// 	dto.projectDevlanguage 		= Convert.toString(project.get("project_devlanguage"));
	// 	dto.projectProjectsite 		= Convert.toString(project.get("project_projectsite"));			
	// 	dto.projectTechnicalinfo 	= Convert.toString(project.get("project_technicalinfo"));
	// 	dto.set("project_name", projectName);
	// 	dto.set("project_desc", projectDesc);
	// 	dto.set("project_role", projectRole);
	// 	dto.set("project_apptype", projectApptype);
	// 	dto.set("project_devtool", projectDevtool);
	// 	dto.set("project_enddate", projectEnddate);
	// 	dto.set("project_startdate," projectStartdate);
	// 	dto.set("project_database", projectDatabase);
	// 	dto.set("project_customer", projectCustomer);
	// 	dto.set("project_serveros", projectServeros);
	// 	dto.set("project_framework", projectFramework);
	// 	dto.set("project_appserver", projectAppserver);
	// 	dto.set("project_otherinfo", projectOtherinfo);
	// 	dto.set("project_devlanguage", projectDevlanguage);
	// 	dto.set("project_projectsite", projectProjectsite);
	// 	dto.set("project_technicalinfo", projectTechnicalinfo);
	// 	dto.save();
	// 	return dto.toModelMap();
	// }

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

	// public Map<String, Object> customOnDelete(SdmHiring item, Map<String, Object> mapRequest) throws Exception {
			
	// 	Map<String, Object> result = super.customOnDelete(item, mapRequest);		
	// 	ProjectDTO dto = new ProjectDTO();
	// 	dto.fromModelMap(result);
		
	// 	Sdm sdm = item.parent(Sdm.class);
	// 	dto.projectName 			= Convert.toString(project.get("project_name"));
	// 	dto.projectDesc 			= Convert.toString(project.get("project_desc"));			
	// 	dto.projectRole 			= Convert.toString(project.get("project_role"));			
	// 	dto.projectApptype 			= Convert.toString(project.get("project_apptype"));			
	// 	dto.projectDevtool 			= Convert.toString(project.get("project_devtool"));
	// 	dto.projectEnddate 			= Convert.toString(project.get("project_enddate"));
	// 	dto.projectDatabase 		= Convert.toString(project.get("project_database"));			
	// 	dto.projectCustomer 		= Convert.toString(project.get("project_customer"));			
	// 	dto.projectServeros 		= Convert.toString(project.get("project_serveros"));			
	// 	dto.projectFramework 		= Convert.toString(project.get("project_framework"));
	// 	dto.projectAppserver 		= Convert.toString(project.get("project_appserver"));			
	// 	dto.projectOtherinfo 		= Convert.toString(project.get("project_otherinfo"));
	// 	dto.projectStartdate 		= Convert.toString(project.get("project_startdate"));
	// 	dto.projectDevlanguage 		= Convert.toString(project.get("project_devlanguage"));
	// 	dto.projectProjectsite 		= Convert.toString(project.get("project_projectsite"));			
	// 	dto.projectTechnicalinfo 	= Convert.toString(project.get("project_technicalinfo"));
		
	// 	return dto.toModelMap();
	// }

	// @Override
	// public Map<String, Object> customOnUpdate(Project item, Map<String, Object> mapRequest) throws Exception {
	// 	Map<String, Object> result = super.customOnUpdate(item, mapRequest);
	// 	ProjectDTO dto = new ProjectDTO();
	// 	dto.fromModelMap(result);
		
	// 	sdm = item.parent(Sdm.class);
	// 	Project project = item.parent(Project.class);
	// 	dto.projectName 			= Convert.toString(project.get("project_name"));
	// 	dto.projectDesc 			= Convert.toString(project.get("project_desc"));			
	// 	dto.projectRole 			= Convert.toString(project.get("project_role"));			
	// 	dto.projectApptype 			= Convert.toString(project.get("project_apptype"));			
	// 	dto.projectDevtool 			= Convert.toString(project.get("project_devtool"));
	// 	dto.projectEnddate 			= Convert.toString(project.get("project_enddate"));
	// 	dto.projectDatabase 		= Convert.toString(project.get("project_database"));			
	// 	dto.projectCustomer 		= Convert.toString(project.get("project_customer"));			
	// 	dto.projectServeros 		= Convert.toString(project.get("project_serveros"));			
	// 	dto.projectFramework 		= Convert.toString(project.get("project_framework"));
	// 	dto.projectAppserver 		= Convert.toString(project.get("project_appserver"));			
	// 	dto.projectOtherinfo 		= Convert.toString(project.get("project_otherinfo"));
	// 	dto.projectStartdate 		= Convert.toString(project.get("project_startdate"));
	// 	dto.projectDevlanguage 		= Convert.toString(project.get("project_devlanguage"));
	// 	dto.projectProjectsite 		= Convert.toString(project.get("project_projectsite"));			
	// 	dto.projectTechnicalinfo 	= Convert.toString(project.get("project_technicalinfo"));
		
	// 	return dto.toModelMap();
	// }
	
	
}




