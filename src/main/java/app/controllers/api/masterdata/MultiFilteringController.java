package app.controllers.api.masterdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;
import core.io.helper.JsonHelper;


import com.ibm.icu.util.Calendar;

import app.controllers.allocation.MultiInsertSdmController.InputSdmSkillDTO;
import app.controllers.api.masterdata.MengelolaSdmSkillController.MengelolaSdmSkillDTO;
import app.models.Sdm;
import app.models.SdmSkill;
import app.models.Skill;
import app.models.SkillType;
import core.io.enums.ActionTypes;
import core.io.enums.HttpResponses;
import core.io.helper.MapHelper;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;


public class MultiFilteringController extends CRUDController<SdmSkill>{


	public class MultiFilteringDTO extends DTOModel{
		public int sdmskillId;
//		public int skillId;
//		public int skilltypeId;
//		public int sdmId;
		public int sdmskillValue;
		public String skillName;
		public String skilltypeName;
		public String sdmName;
	}

	
	@POST
	public final void multiFilter() {
		List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
//		LazyList<SdmSkill> asd = (LazyList<SdmSkill>)this.getItems(params);	
//		Long totalItems = this.getTotalItems(params);
		try {
			response().setActionType(ActionTypes.READ_ALL);
		
			Map<String, Object> mapRequest = getRequestBody();
			List<Map<String, Object>> listParams = MapHelper.castToListMap((List<Map>) mapRequest.get("listsdm"));
			List<Map> listData = new ArrayList<>();
//			List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
			int sdmId = 0;
			int skilltypeId = 0;
			int skillId = 0;
			int value = 0;
			int operator = 0;
			
			for (Map<String, Object> sdm : listParams) {
				sdmId = Convert.toInteger(sdm.get("sdm_id"));
				skilltypeId = Convert.toInteger(sdm.get("skilltype_id"));
				skillId = Convert.toInteger(sdm.get("skill_id"));
				value = Convert.toInteger(sdm.get("sdmskill_value"));
				operator = Convert.toInteger(sdm.get("operator"));
			}
			//1
			if(sdmId != 0 && skilltypeId == 0 && skillId == 0 && value == 0) {
				listData = SdmSkill.getbySdm(sdmId);
			}//2
			else if(sdmId == 0 && skilltypeId != 0 && skillId == 0 && value == 0) {
				if(operator == 1) {
					listData = SdmSkill.getbyCategoryOR(listParams);
				} else if(operator == 2) {
					listData = SdmSkill.getbyCategoryAND(listParams);
				}
			}//3
			else if(sdmId == 0 && skilltypeId != 0 && skillId != 0 && value != 0) {
				if(operator == 1) {
					listData = SdmSkill.getbySkillValueOR(listParams);
				} else if(operator == 2) {
					//cari query	
					listData = SdmSkill.getbySkillValueAND(listParams);
				}
			}
			//4
			else if(sdmId != 0 && skilltypeId != 0 && skillId == 0 && value == 0) {
				if(operator == 1) {
					listData = SdmSkill.getbySdmCategoryOR(listParams);
				}else if(operator == 2) {
					listData = SdmSkill.getbySdmCategoryAND(listParams);
				}
			}
			//5
			else if(sdmId != 0 && skilltypeId != 0 && skillId != 0 && value != 0) {
				
			}
			MultiFilteringDTO dto = new MultiFilteringDTO();
			
				for (Map map : listData) {
					dto.sdmskillId = Convert.toInteger(map.get("sdmskill_id"));
					dto.sdmskillValue = Convert.toInteger(map.get("sdmskill_value"));;
					dto.skillName = Convert.toString(map.get("skill_name"));;
					dto.skilltypeName = Convert.toString(map.get("skilltype_name"));;
					dto.sdmName = Convert.toString(map.get("sdm_name"));
					
					listMapSdmSkill.add(dto.toModelMap());
				}
				
			response().setResponseBody(HttpResponses.ON_SUCCESS_READ_ALL, listMapSdmSkill);

			Base.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			response().setResponseBody(e);
			Base.rollbackTransaction();
		}

		sendResponse();Base.openTransaction();
		
	}
	
	
}
