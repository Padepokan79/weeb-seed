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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
		public int norut;
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
		public String project_notification;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		
		DateFormat dateAwal = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateAkhir = new SimpleDateFormat("dd/MM/yyyy");
		
		List<Map<String, Object>> listMapProject = new ArrayList<Map<String, Object>>();
		LazyList<Project> listProject = Project.findAll();
		params.setOrderBy("project_id");
		Long totalitems = this.getTotalItems(params);
		int number = 1;
		for(Project project : listProject) {
			Sdm sdm = project.parent(Sdm.class);		
			MengelolaProject dto = new MengelolaProject();
			dto.fromModelMap(project.toMap());
			dto.norut					= number;
			number++;
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
			
//			Updated by Vikri
//			09/08/2018
			
			java.util.Date judAwl = dateAwal.parse(getCurrentDate());
			java.util.Date judAkhir = dateAkhir.parse(getConvertBulan(project.get("project_enddate").toString()));
			
			java.sql.Date tglAwal = new java.sql.Date(judAwl.getTime());
			java.sql.Date tglAkhir = new java.sql.Date(judAkhir.getTime());
			
			Date TGLAwal = tglAwal;
            Date TGLAkhir = tglAkhir;
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(TGLAwal);
            cal2.setTime(TGLAkhir);
            String diff = Convert.toString(mothsBetween(cal1, cal2));
            
            if (Convert.toDouble(diff) == 0) {
            	dto.project_notification = "black"; // notif warna hitam
            	
			}else if(Convert.toDouble(diff) <= 1) {
				dto.project_notification  = "red"; // notif warna merah
				
			}else if(Convert.toDouble(diff) <= 2) {
				dto.project_notification  = "yellow"; // notif warna kuning
				
			}else if(Convert.toDouble(diff) <= 4) {
				dto.project_notification  = "green"; // notif warna hijau
				
			}else if(Convert.toDouble(diff) > 4) {
				dto.project_notification  = "grey"; // notif warna grey
			}
			
			listMapProject.add(dto.toModelMap());
		}
		return new CorePage(listMapProject, totalitems);				
	}
	
	private static double mothsBetween(Calendar tanggalAwal, Calendar tanggalAkhir) {
        long lama = 0;
        double hasil = 0;
        Calendar tanggal = (Calendar) tanggalAwal.clone();
        while (tanggal.before(tanggalAkhir)) {
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }
        hasil = (lama)/30.0;
        return hasil;
    }
	
	public String getCurrentDate(){
	    final Calendar c = Calendar.getInstance();
	    int year, month, day;
	    year = c.get(Calendar.YEAR);
	    month = c.get(Calendar.MONTH);
	    day = c.get(Calendar.DATE);
	    return day + "/" + (month+1) + "/" + year;
	}
	
	public String getConvertBulan(String now) {
		String bulan[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		String tanggal = now.substring(8,10);
		String bln = now.substring(5,7);
		String tahun = now.substring(0,4);
		String hasil =tanggal+"/"+bulan[Integer.parseInt(bln)-1]+"/"+tahun;
		System.out.println("cek : " + hasil);
		return hasil;
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




