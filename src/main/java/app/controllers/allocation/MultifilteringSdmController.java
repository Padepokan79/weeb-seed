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

import app.controllers.allocation.MengelolaSdmSkillController.MengelolaSdmSkillDTO;
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
 * @update : Khairil Anwar
 * @Date   : Aug 16, 2018 15:06 PM
 */

public class MultifilteringSdmController extends CRUDController<SdmSkill>{
	
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
		Long totalItems = this.getTotalItems(params);
		List<Map> listData = SdmSkill.getGroupSdmSkill(params.filterQuery());
		for(Map map: listData) {
			map.put("SDMSKILL_ID", Convert.toString(map.get("SDMSKILL_ID")));
			map.put("SDM_NIK", Convert.toString(map.get("SDM_NIK")));
			map.put("SDM_NAME", Convert.toString(map.get("SDM_NAME")));
			map.put("SKILLTYPE_NAME", Convert.toString(map.get("SKILLTYPE_NAME")).replace("-", System.getProperty("line.separator")));
			map.put("SKILL_NAME", Convert.toString(map.get("SKILL_NAME")).replace("-", System.getProperty("line.separator")));
			map.put("SDMSKILL_VALUE", Convert.toString(map.get("SDMSKILL_VALUE")).replace(",", System.getProperty("line.separator")));
			listMapSdmSkill.add(map);
		}
		
		return new CorePage(listMapSdmSkill, totalItems);			
	}	

}
