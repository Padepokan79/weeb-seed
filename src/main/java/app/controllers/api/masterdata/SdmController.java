package app.controllers.api.masterdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import app.controllers.api.masterdata.SkillController.SkillDTO;
import app.models.Sdm;
import app.models.SdmSkill;
import app.models.Skill;
import app.models.SkillType;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class SdmController extends CRUDController<Sdm> {
	public class SdmDTO extends DTOModel {
		public int sdmId;
		public String sdmName;
		public String sdmPlacebirth;
		public String sdmDatebirth;
		public String sdmStartcontract;
		public String sdmEndcontract;
//		public int skilltypeId;
//		public int skillId;
//		public int sdmskillValue;
		
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapSkill = new ArrayList<Map<String, Object>>();
		LazyList<Sdm> listSdm = Sdm.findAll();	
		params.setOrderBy("sdm_name");
		
		LazyList<? extends Model> items = this.getItems(params);
		Long totalItems = this.getTotalItems(params);
					
//			for (Sdm sdm : listSdm) {
////				SkillType skillType = sdm.parent(SkillType.class);
//				
//				SdmDTO dto = new SdmDTO();
//				dto.fromModelMap(sdm.toMap());
////				dto.sdmDatebirth = Convert.toString(skillType.get("skilltype_name"));
//				listMapSkill.add(dto.toModelMap());
//			}
		
		return new CorePage(items.toMaps(), totalItems);		
	}
	
	@Override
	public Sdm customInsertValidation(Sdm item) throws Exception {
		String sdmName = item.getString("sdm_name");
//		String skillType = item.getString("skilltype_id");
		
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(sdmName, "Nilai Nama SDM tidak boleh kosong!");
//		Validation.required(skillType, "Nilai Skill Type tidak boleh kosong!");


		return super.customInsertValidation(item);
	}
	
	@Override
	public Map<String, Object> customOnInsert(Sdm item, Map<String, Object> mapRequest) throws Exception {
//		int skilltypeId = Convert.toInteger("$skilltype_id");
//		int skillId = Convert.toInteger("$skill_id");
//		int sdmskillValue = Convert.toInteger("$sdmskill_value");
		Map<String, Object> result = super.customOnInsert(item, mapRequest);
//		SkillDTO dto = new SkillDTO();
//		dto.fromMap(result);
		
		SdmSkill sdmSkill = new SdmSkill();
		sdmSkill
			.set("sdm_id", Sdm.getLastSdmId())
			.set("skilltype_id", "skilltype_id")
			.set("sdmskill_value", "sdmskill_value")
			.saveIt();
		
		
		return result;
	}
//	
//	@Override
//	public Map<String, Object> customOnUpdate(Skill item, Map<String, Object> mapRequest) throws Exception {
//				
//		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
//		SkillDTO dto = new SkillDTO();
//		dto.fromModelMap(result);
//
//				
//		return dto.toModelMap();
//	}
//	
//	@Override
//	public Map<String, Object> customOnDelete(Skill item, Map<String, Object> mapRequest) throws Exception {
//		
//		
//		Map<String, Object> result = super.customOnDelete(item, mapRequest);		
//		SkillDTO dto = new SkillDTO();
//		dto.fromModelMap(result);
//		
//		
//		return dto.toModelMap();
//	}	
}
