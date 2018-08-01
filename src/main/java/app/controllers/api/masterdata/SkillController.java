package app.controllers.api.masterdata;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.iterators.SkippingIterator;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;


import app.models.Skill;
import app.models.SkillType;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class SkillController extends CRUDController<Skill> {
	public class SkillDTO extends DTOModel {
		public int skillId;
		public String skilltypeName;
		public String skillName;		
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapSkill = new ArrayList<Map<String, Object>>();
		LazyList<Skill> listSkill = Skill.findAll();	
		params.setOrderBy("skill_name");
		
//		LazyList<? extends Model> items = this.getItems(params);
		Long totalItems = this.getTotalItems(params);
					
			for (Skill skill : listSkill) {
				SkillType skillType = skill.parent(SkillType.class);
				
				SkillDTO dto = new SkillDTO();
				dto.fromModelMap(skill.toMap());
				dto.skilltypeName = Convert.toString(skillType.get("skilltype_name"));
				listMapSkill.add(dto.toModelMap());
			}
		
		return new CorePage(listMapSkill, totalItems);		
	}
	
	@Override
	public Skill customInsertValidation(Skill item) throws Exception {
		String skillName = item.getString("skill_name");
		String skillType = item.getString("skilltype_id");
		
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(skillName, "Nilai Nama Skill tidak boleh kosong!");
		Validation.required(skillType, "Nilai Skill Type tidak boleh kosong!");


		return super.customInsertValidation(item);
	}
	
//	@Override
//	public Map<String, Object> customOnInsert(Skill item, Map<String, Object> mapRequest) throws Exception {
//				
//		Map<String, Object> result = super.customOnInsert(item, mapRequest);
//		SkillDTO dto = new SkillDTO();
//		dto.fromMap(result);
//		
//		
//		return dto.toModelMap();
//	}
//	
	@Override
	public Map<String, Object> customOnUpdate(Skill item, Map<String, Object> mapRequest) throws Exception {
				
		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
		SkillDTO dto = new SkillDTO();
		dto.fromModelMap(result);

				
		return dto.toModelMap();
	}
	
	@Override
	public Map<String, Object> customOnDelete(Skill item, Map<String, Object> mapRequest) throws Exception {
		
		
		Map<String, Object> result = super.customOnDelete(item, mapRequest);		
		SkillDTO dto = new SkillDTO();
		dto.fromModelMap(result);
		
		
		return dto.toModelMap();
	}	
}
