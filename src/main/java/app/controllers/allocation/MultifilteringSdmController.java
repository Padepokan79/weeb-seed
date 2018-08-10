/**
 * 
 */
package app.controllers.allocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;
import org.javalite.common.JsonHelper;

import com.google.common.base.Strings;

import app.models.Sdm;
import app.models.SdmSkill;
import app.models.Skill;
import app.models.SkillType;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/**
 * @author : Malik Chaudhary
 * @Date   : Aug 10, 2018 07:51 AM
 * 
 */
public class MultifilteringSdmController extends CRUDController<SdmSkill>{
	public class FilteringDTO extends DTOModel{
		public int sdmskillId;
		public String sdmNik;
		public String sdmName;
		public String skilltypeName;
		public String skillName;
		public int sdmskillValue;
	}
	
	
	
//	public Map<String, Object> getData() throws Exception {
//
//		int skillId = 5;
//		int sdmskillValue = 7;
//		List<Map<String, Object>> listMapFilter = new ArrayList<Map<String, Object>>();
//		List<Map> listData = new ArrayList<>();
//		
//		Map<String, Object> tampil = new HashMap<>();
//		
//		List<Map> dataFromQuery = SdmSkill.getMultifiltering(skillId,sdmskillValue);
//		for (Map map : dataFromQuery) { 
//			listMapFilter.add(map);
//		}
//		LazyList<SdmSkill> listFilter = (LazyList<SdmSkill>)this.getItems(params);
//		
//		Map<String , Object> listMapFilter  = new HashMap<>();
//		listMapFilter.put(null, listData);
//		System.out.println(JsonHelper.toJsonString(listMapFilter));
//		return listMapFilter;
//	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		int skillId = 5;
		int sdmskillValue = 7;
		
		List<Map<String, Object>> listMapFilter = new ArrayList<Map<String, Object>>();
		
		Long totalItems = this.getTotalItems(params);
		
		List<Map> dataFromQuery = SdmSkill.getMultifiltering();
		for (Map map : dataFromQuery) { 
			//FilteringDTO dto = new FilteringDTO();
			//dto.fromModelMap( ((DTOModel) map).toMap());
			listMapFilter.add(map);
			
		}

		return new CorePage(listMapFilter, totalItems);			
	}
	
	/* (non-Javadoc)
	 * @see core.javalite.controllers.CRUDController#customOnReadAll(core.io.model.PagingParams)
	 */
//	@Override
//	public CorePage customOnReadAll(PagingParams params) throws Exception {
//		List<Map<String, Object>> listMapFilter = new ArrayList<Map<String, Object>>();
//		LazyList<SdmSkill> listFilter = (LazyList<SdmSkill>)this.getItems(params);
//		
//		Long totalItems = this.getTotalItems(params);
//		
//		for(SdmSkill filter: listFilter) {
//			Sdm sdm 			= filter.parent(Sdm.class);
//			Skill skill 		= filter.parent(Skill.class);
//			SkillType skilltype = filter.parent(SkillType.class);
//			
//			FilteringDTO dto = new FilteringDTO();
//			dto.fromModelMap(filter.toMap());
//			dto.sdmNik 			= Convert.toString(sdm.get("sdm_nik"));
//			dto.sdmName 		= Convert.toString(sdm.get("sdm_name"));
//			dto.skilltypeName 	= Convert.toString(skilltype.get("skilltype_name"));
//			dto.skillName 		= Convert.toString(skill.get("skill_name"));
//			listMapFilter.add(dto.toModelMap());
//		}
//		
//		return new CorePage(listMapFilter, totalItems);
//	}

}
