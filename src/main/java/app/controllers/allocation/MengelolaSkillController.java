package app.controllers.allocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

import app.models.SdmSkill;
import app.models.Skill;
import app.models.SkillType;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class MengelolaSkillController extends CRUDController<Skill>{
	public class KelolaSkill extends DTOModel{
		public int skillId;
		public String skilltypeName;
		public String skillName;
	}

//	Update By 	: Malik Chaudhary
//	Update Date	: 02 September 2018
	
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
		public String sdm_notification;
	}
	 
//	Update By 	: Nurdhiat Chaudhary Malik
//	Update Date	: 02 Agustus 2018
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		List<Map<String, Object>> listMapSkill = new ArrayList<Map<String, Object>>();
		LazyList<Skill> listSkill = (LazyList<Skill>)this.getItems(params);
		
		Long totalitems = this.getTotalItems(params);
		
		for(Skill skill : listSkill) {
			SkillType skilltype = skill.parent(SkillType.class);
			
			KelolaSkill dto = new KelolaSkill();
			dto.fromModelMap(skill.toMap());
			dto.skilltypeName = Convert.toString(skilltype.get("skilltype_name"));
			listMapSkill.add(dto.toModelMap());
			
		}
		
		return new CorePage(listMapSkill, totalitems);
	}

	@Override
	public Skill customInsertValidation(Skill item) throws Exception {
		LazyList<Skill> listSkillVal = Skill.findAll();
		String name = item.getString("skill_name");
		
		for(Skill skill : listSkillVal) {
			KelolaSkill dto = new KelolaSkill();
			dto.fromModelMap(skill.toMap());
			if (name.equalsIgnoreCase(dto.skillName)) {
				Validation.required(null, "Nama skill sudah ada");
			}	
		}
		Validation.required(name, "Nama skill harus diisi");
		return super.customInsertValidation(item);
	}


//	Update By 	: Malik Chaudhary
//	Update Date	: 02 September 2018
	
	/* (non-Javadoc)
	 * @see core.javalite.controllers.CRUDController#customOnDelete(org.javalite.activejdbc.Model, java.util.Map)
	 */
	@Override
	public Map<String, Object> customOnDelete(Skill item, Map<String, Object> mapRequest) throws Exception {
		LazyList<SdmSkill> list = SdmSkill.findAll();
		Map<String, Object> result = null;
		for(SdmSkill type: list) {
			MengelolaSdmSkillDTO dto = new MengelolaSdmSkillDTO();
			dto.fromModelMap(type.toMap());
			if (item.getString("skill_id").equalsIgnoreCase(Convert.toString(dto.skillId))) {
				Validation.required(null, "Skill tidak bisa dihapus, skill ini masih terdata pada sdm skill");
			}
		}
		result = super.customOnDelete(item, mapRequest);
		return result;
	}
	
}
