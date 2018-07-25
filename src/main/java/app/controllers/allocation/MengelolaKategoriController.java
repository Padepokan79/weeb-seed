package app.controllers.allocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
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
	}
	
	public CorePage customOnReadAll(PagingParams params) throws Exception {
	    List<Map<String, Object>> listMapCategory = new ArrayList<Map<String, Object>>();
	    LazyList<SkillType> listCategory = SkillType.findAll();
	    params.setOrderBy("skilltype_id");
	    
	    Long totalItems = this.getTotalItems(params);
		for (SkillType catg : listCategory) {
					
			MengelolaCategoryDTO dto = new MengelolaCategoryDTO();
			dto.fromModelMap(catg.toMap());
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
	
	
//	@Override
//	public Map<String, Object> customOnInsert(SkillType item, Map<String, Object> mapRequest) throws Exception {
//				
//		Map<String, Object> result = super.customOnInsert(item, mapRequest);
//		MengelolaCategoryDTO dto = new MengelolaCategoryDTO();
//		dto.fromMap(result);
//		
//		return dto.toModelMap();
//	}
	
//	@Override
//	public Map<String, Object> customOnUpdate(SkillType item, Map<String, Object> mapRequest) throws Exception {
//				
//		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
//		MengelolaCategoryDTO dto = new MengelolaCategoryDTO();
//		dto.fromModelMap(result);
//
//		return dto.toModelMap();
//	}
//	
//	@Override
//	public Map<String, Object> customOnDelete(SkillType item, Map<String, Object> mapRequest) throws Exception {
//		
//		Map<String, Object> result = super.customOnDelete(item, mapRequest);		
//		MengelolaCategoryDTO dto = new MengelolaCategoryDTO();
//		dto.fromModelMap(result);
//		
//		return dto.toModelMap();
//	}
	
}
