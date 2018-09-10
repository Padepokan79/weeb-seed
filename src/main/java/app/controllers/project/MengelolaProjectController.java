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

/*
 * ---------------
 * Modified By    : Malik Chaudhary
 * Last Modified  : Kamis, 16th Aug 2018 09:25 AM
 * ---------------
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
import app.models.Clients;
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
		public String sdmNik;
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
		LazyList<Project> listProject = (LazyList<Project>)this.getItems(params);
		params.setOrderBy("project_id");
		Long totalitems = this.getTotalItems(params);
		/*
		 * Edited : Muhamad Rifan Andrian & Alifhar
		 * Date	: 29/08/2018
		 * */
		int number=1;
		if(params.limit()!=null || params.offset()!=null)
			number = params.limit().intValue() * params.offset().intValue()+1;
		
		for(Project project : listProject) {
			
			Map<String, Object> temp = project.toMap();
			temp.remove("project_startdate");
			temp.remove("project_enddate");

			Sdm sdm = project.parent(Sdm.class);
			MengelolaProject dto = new MengelolaProject();
			dto.fromModelMap(temp);
			
			dto.norut					= number;
			number++;
			dto.sdmName 				= Convert.toString(sdm.get("sdm_name"));
			dto.sdmNik 					= Convert.toString(sdm.get("sdm_nik"));
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
/* (non-Javadoc)
 * @see core.javalite.controllers.CRUDController#customOnDelete(org.javalite.activejdbc.Model, java.util.Map)
 */

	@Override
	public Project customInsertValidation(Project item) throws Exception {
		LazyList<Project> listProjVal = Project.findAll();
		String project_name = item.getString("project_name");
		
		String sdm_name = item.getString("sdm_id");
		String p_role = item.getString("project_role");
		String p_site = item.getString("project_projectsite");
		String p_enddate = item.getString("project_enddate");
		String p_startdate = item.getString("project_startdate");
		String p_desc = item.getString("project_desc");
		
//		for(Project project : listProjVal) {
//			MengelolaProject dto = new MengelolaProject();
//			dto.fromModelMap(project.toMap());
//			if (name.equalsIgnoreCase(dto.projectName)) {
//				Validation.required(null, "Nama project sudah ada");
//			}
//			
//		}
		Validation.required(sdm_name, "SDM name harus diisi");
		Validation.required(project_name, "Project name harus diisi");
		
		Validation.required(p_site, "Project site harus diisi");
		Validation.required(p_role, "Project role harus diisi");
		Validation.required(p_startdate, "Project start date harus diisi");
		Validation.required(p_enddate, "Project end date harus diisi");
		
		return super.customInsertValidation(item);
	}

	//Modified by : Hendra Kurniawan
    //Date        : 15/08/2018
	@Override
	public Map<String, Object> customOnInsert(Project item, Map<String, Object> mapRequest) throws Exception{		
		Map<String, Object> result = super.customOnInsert(item, mapRequest);
		
		String p_apptype = Convert.toString(mapRequest.get("project_apptype"));
		String p_devtool = Convert.toString(mapRequest.get("project_devtool"));
		String p_database = Convert.toString(mapRequest.get("project_database"));
		String p_customer = Convert.toString(mapRequest.get("project_customer"));
		String p_serveros = Convert.toString(mapRequest.get("project_serveros"));
		String p_framework = Convert.toString(mapRequest.get("project_framework"));
		String p_appserver = Convert.toString(mapRequest.get("project_appserver"));
		String p_otherinfo = Convert.toString(mapRequest.get("project_otherinfo"));
		String p_devlanguage = Convert.toString(mapRequest.get("project_devlanguage"));
		String p_technicalinfo = Convert.toString(mapRequest.get("project_technicalinfo"));
		
		if(p_apptype == "") {
			p_apptype = "-";
		}
		if(p_devtool == "") {
			p_devtool = "-";
		}
		if(p_database == "") {
			p_database = "-";
		}
		if(p_customer == "") {
			p_customer = "-";
		}
		if(p_serveros == "") {
			p_serveros = "-";
		}
		if(p_framework == "") {
			p_framework = "-";
		}	
		if(p_appserver == "") {
			p_appserver = "-";
		}
		if(p_otherinfo == "") {
			p_otherinfo = "-";
		}
		if(p_devlanguage == "") {
			p_devlanguage = "-";
		}
		if(p_technicalinfo == "") {
			p_technicalinfo = "-";
		}
		
		item.set("project_apptype", p_apptype);
		item.set("project_devtool", p_devtool);
		item.set("project_database", p_database);
		item.set("project_customer", p_customer);
		item.set("project_serveros", p_serveros);
		item.set("project_framework"	, p_framework);
		item.set("project_appserver", p_appserver);
		item.set("project_otherinfo", p_otherinfo);
		item.set("project_devlanguage", p_devlanguage);
		item.set("project_technicalinfo", p_technicalinfo);
		item.save();
		return result;
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




