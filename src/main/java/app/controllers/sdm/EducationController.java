/**
 * 
 */
package app.controllers.sdm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

import app.controllers.api.masterdata.SkillController.SkillDTO;
import app.models.Degree;
import app.models.Education;
import app.models.Sdm;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/**
 *web-seed
 * EducationController.java
 ----------------------------
 * @author Ryan Ahmad Nuriana
 * 16.37.15 25 Jul 2018
 */
public class EducationController extends CRUDController<Education>{
	
	public class EducationDTO extends DTOModel {
		public int edu_id;
		public String nama;
		public String edu_name;
		public String edu_subject;
		public String gelar;
		public String edu_startdate;
		public String edu_enddate;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapEducation = new ArrayList<Map<String, Object>>();
		LazyList<Education> listEducation = Education.findAll();	
		params.setOrderBy("edu_id");
		
		Long totalItems = this.getTotalItems(params);
					
			for (Education edu : listEducation) {
				Sdm sdm = edu.parent(Sdm.class);
				Degree degree = edu.parent(Degree.class);
				
				EducationDTO dto = new EducationDTO();
				dto.fromModelMap(edu.toMap());
				dto.nama = Convert.toString(sdm.get("sdm_name"));
				dto.gelar = Convert.toString(degree.get("degree_name"));
				listMapEducation.add(dto.toModelMap());
			}
		
		return new CorePage(listMapEducation, totalItems);		
	}
}
