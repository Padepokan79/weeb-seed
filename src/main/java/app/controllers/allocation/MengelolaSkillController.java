package app.controllers.allocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

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
	 
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		List<Map<String, Object>> listMapSkill = new ArrayList<Map<String, Object>>();
		LazyList<Skill> listSkill = Skill.findAll();
		
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
	
}
