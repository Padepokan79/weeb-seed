package app.controllers.project;

import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

import java.util.Date;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import com.google.common.base.Strings;
import com.ibm.icu.util.Calendar;

import app.controllers.example.crud.CustomAllInOneDTOController.CustomAllInOneDTO;
import app.models.Project;
import app.models.core.master.MasterUser;
import app.models.core.master.MasterUserActivity;

public class MengelolaProjectController extends CRUDController<Project> {
	
	public class MengelolaProject extends DTOModel{
		public int sdmId;
		public int sdmNIK;
		public String sdmName;
//		public String projectName;
//		public String projectDesc;
//		public String projectRole;
//		public String projectStartdate;
//		public String projectEnddate;
//		public String projectProjectsite;
//		public String projectCustomer;
//		public String projectApptype;
//		public String projectServeros;
//		public String projectDevlanguage;
//		public String projectFramework;
//		public String projectDatabase;
//		public String projectAppserver;
//		public String projectDevtool;
//		public String projectTechnicalinfo;
//		public String projectOtherinfo;
	}
	
//	@Override
//	public CorePage customOnReadAll(PagingParams params) throws Exception {		
//		params.setOrderBy("sdm_id");
//		LazyList<? extends Model> items = this.getItems(params);
//		Long totalItems = this.getTotalItems(params);
//
//		return new CorePage(items.toMaps(), totalItems);				
//	}
	
//	@Override
//	public Map<String, Object> customOnInsert(Project item, Map<String, Object> mapRequest) throws Exception {
//		System.out.println("Masuk lagi !!!");
//		
//		return null;
//
//	}	
//	@Override
//	public Map<String, Object> customOnInsert(Project item, Map<String, Object> mapRequest) throws Exception {
//		System.out.println("Masuk Sini Bro !!!");
//		Map<String, Object> result = super.customOnInsert(item, mapRequest);
//		MengelolaProject dto = new MengelolaProject();
//		dto.fromMap(result);
//
//
//		return dto.toModelMap();
//	}
	@Override
	public Project customInsertValidation(Project item) throws Exception {
		String projName = item.getString("project_name");
		
		
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(projName, "project name tidak boleh kosong!");

		// Contoh Validasi untuk variable yang harus bernilai 0 / 1
//		Validation.booleanOnly(isActive, "Nilai is_active hanya bernilai 0/1!");
		
		return super.customInsertValidation(item);
	}
	
	
}




