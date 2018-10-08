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

public class MengelolaKategoriController extends CRUDController<SkillType>{
	public class MengelolaCategoryDTO extends DTOModel {
		public int skilltypeId;
		public String skilltypeName;
		public int norut;
	}

//	Update By 	: Malik Chaudhary
//	Update Date	: 02 September 2018
	
	public class KelolaSkill extends DTOModel{
		public int skillId;
		public String skilltypeName;
		public int skilltypeId;
		public String skillName;
		
	}
	
//	Update By 	: Nurdhiat Chaudhary Malik
//	Update Date	: 02 Agustus 2018
	
	public CorePage customOnReadAll(PagingParams params) throws Exception {
	    List<Map<String, Object>> listMapCategory = new ArrayList<Map<String, Object>>();
	    LazyList<SkillType> listCategory = (LazyList<SkillType>)this.getItems(params);
	    params.setOrderBy("skilltype_id");
	    
	    Long totalItems = this.getTotalItems(params);
	    
	    int number =1 ;
	    if(params.limit()!=null)
	    	number = params.limit().intValue() * params.offset().intValue() + 1;
		for (SkillType catg : listCategory) {
					
			MengelolaCategoryDTO dto = new MengelolaCategoryDTO();
			dto.fromModelMap(catg.toMap());
			dto.norut = number;
			number++;
			
			listMapCategory.add(dto.toModelMap());
		}
	    return new CorePage(listMapCategory, totalItems);
    }
	
	@Override
	public SkillType customInsertValidation(SkillType item) throws Exception {
		String skilltypeName = item.getString("skilltype_name");
		
		LazyList<SkillType> listCategory = SkillType.findAll();
        for(SkillType catg : listCategory) {
        	MengelolaCategoryDTO dto = new MengelolaCategoryDTO();
            dto.fromModelMap(catg.toMap());
            if(item.getString("skilltype_name").equalsIgnoreCase(dto.skilltypeName))
            Validation.required(null, "Data sudah ada");
        }
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(skilltypeName, "Nilai Nama Skill tidak boleh kosong!");

		return super.customInsertValidation(item);
	}

//	Update By 	: Malik Chaudhary
//	Update Date	: 02 September 2018
	
	@Override
	public Map<String, Object> customOnDelete(SkillType item, Map<String, Object> mapRequest) throws Exception {
		LazyList<Skill> list = Skill.findAll();
		Map<String, Object> result = null;
		for(Skill type: list) {
			KelolaSkill dto = new KelolaSkill();
			dto.fromModelMap(type.toMap());
			if (item.getString("skilltype_id").equalsIgnoreCase(Convert.toString(dto.skilltypeId))) {
				Validation.required(null, "Kategori tidak bisa dihapus, Kategori ini masih terdata pada skill");
			}
		}
		result = super.customOnDelete(item, mapRequest);
		return result;
		
	}
	
}
