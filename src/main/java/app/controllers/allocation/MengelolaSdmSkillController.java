/**
 * 
 */
package app.controllers.allocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import app.models.SdmSkill;
import app.models.Project;
import app.models.Sdm;
import app.models.Skill;
import app.models.SkillType;
import core.io.enums.ActionTypes;
import core.io.enums.HttpResponses;
import core.io.helper.JsonHelper;
import core.io.helper.MapHelper;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/**
 * @author rifanandrian
 * @date Jul 25, 2018
 * @time 8:29:28 AM
 * email : muhamadrifanandrian@gmail.com
 */
public class MengelolaSdmSkillController extends CRUDController<SdmSkill>{
	
	public class MengelolaSdmSkillDTO extends DTOModel{

		public String skilltypeName;
		public String skillName;
		public String sdmNik;
		public String sdmName;
		public int sdmskillId;
		public int skillId;
		public int skilltypeId;
		public int sdmId;
		public int sdmskillValue;
		public String projectEnddate;
		
		
	}
	
	/*
	 * Updated by Nurdhiat Chaudhary Malik
	 * 07 Agustus 2018
	 */
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
		LazyList<SdmSkill> asd = (LazyList<SdmSkill>)this.getItems(params);	
		Long totalItems = this.getTotalItems(params);
		
//		LazyList<? extends Model> items = this.getItems(params);
		
//		  Updated by Hendra Kurniawan
//		  16 Agustus 2018 9:02 AM
		 
		
		List<Map> listData = new ArrayList<>();			
		String endDate="";
			for (SdmSkill mengelolaSdmSkill : asd) {
				SkillType skillType = mengelolaSdmSkill.parent(SkillType.class);
				Skill kemampuan = mengelolaSdmSkill.parent(Skill.class);
				Sdm manusia = mengelolaSdmSkill.parent(Sdm.class);
				endDate = "-";
				
				int sdmId = Convert.toInteger(mengelolaSdmSkill.get("sdm_id"));	
				List<Map> dataFromQuery = SdmSkill.getEndContract(sdmId);
					for(Map map : dataFromQuery) {
						endDate = Convert.toString(map.get("project_enddate"));
					}
					
				MengelolaSdmSkillDTO dto = new MengelolaSdmSkillDTO();
				dto.fromModelMap(mengelolaSdmSkill.toMap());
				dto.skilltypeName = Convert.toString(skillType.get("skilltype_name"));
				dto.skillName = Convert.toString(kemampuan.get("skill_name"));
				dto.sdmName = Convert.toString(manusia.get("sdm_name"));
				dto.sdmNik = Convert.toString(manusia.get("sdm_nik"));
				dto.projectEnddate = endDate;
				listMapSdmSkill.add(dto.toModelMap());
			}
		
		return new CorePage(listMapSdmSkill, totalItems);			
	}
	
//	@Override
//	public SdmSkill customInsertValidation(SdmSkill item) throws Exception {
//		String skillId = item.getString("skill_id");
//		String skillType = item.getString("skilltype_id");
//		String sdmskillValue = item.getString("sdmskill_value");
//
//		
//		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
//		Validation.required(skillId, "Skill tidak boleh kosong!");
//		Validation.required(skillType, "Skill Type tidak boleh kosong!");
//		Validation.required(sdmskillValue, "Nilai tidak boleh kosong!");
//
//
//		return super.customInsertValidation(item);
//	}


}
