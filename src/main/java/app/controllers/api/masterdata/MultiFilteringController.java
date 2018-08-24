package app.controllers.api.masterdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;
import org.javalite.common.JsonHelper;

import com.ibm.icu.util.Calendar;

import app.controllers.allocation.MengelolaSdmSkillController.MengelolaSdmSkillDTO;
import app.models.Sdm;
import app.models.SdmSkill;
import app.models.Skill;
import app.models.SkillType;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;
import core.javalite.controllers.ReportController;

public class MultiFilteringController extends CRUDController<SdmSkill>{


	public class MultiFilteringDTO extends DTOModel{
		public int sdmskillId;
		public int skillId;
		public int skilltypeId;
		public int sdmId;
		public int sdmskillValue;
		public String skillName;
		public String skilltypeName;
		public String sdmName;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
		List<Map> listDataSdm = new ArrayList<>();
		LazyList<SdmSkill> dataSdmSkill = (LazyList<SdmSkill>)this.getItems(params);	
		Long totalItems = this.getTotalItems(params);
		List<Map> listData = new ArrayList<>();
		int sdmId = Convert.toInteger(param("$sdmId"));
		int skilltypeId = Convert.toInteger(param("$skilltypeId"));
		int skillId = Convert.toInteger(param("$skillId"));
		int value = Convert.toInteger(param("$value"));
		int oper = Convert.toInteger(param("$oper"));
		
		int jumlahData = 2;
		
//		int sdmId = Convert.toInteger(param("$sdmId"));
//		System.out.println(sdmId);
//		List<Map> listData = SdmSkill.getbySdm(sdmId);
//		
		
			listData = SdmSkill.takeMultifiltering(sdmId, skilltypeId, skillId, value, oper);
			for (Map map : listData) {
				
//				dto.fromMap(map);
//				
//				dto.sdmskillId = Convert.toInteger(map.get("sdmskill_id"));
//				dto.sdmId = Convert.toInteger(map.get("sdm_id"));
//				dto.skilltypeId = Convert.toInteger(map.get("skilltype_id"));
//				dto.skillId = Convert.toInteger(map.get("skill_id"));
//				dto.sdmskillValue = Convert.toInteger(map.get("sdmskill_value"));
//				dto.skillName = Convert.toString(map.get("skill_name"));
//				dto.skilltypeName =Convert.toString(map.get("skilltype_name"));
//				dto.sdmName = Convert.toString(map.get("sdm_name"));
				listMapSdmSkill.add(map);			
			}
		
//////		for(SdmSkill datasdmskill : dataSdmSkill ) {
////			MultiFilteringDTO dto = new MultiFilteringDTO();
////		
//			for (Map map : listData) {
////				dto.fromMap(map);
////				
////				dto.sdmskillId = Convert.toInteger(map.get("sdmskill_id"));
////				dto.sdmId = Convert.toInteger(map.get("sdm_id"));
////				dto.skilltypeId = Convert.toInteger(map.get("skilltype_id"));
////				dto.skillId = Convert.toInteger(map.get("skill_id"));
////				dto.sdmskillValue = Convert.toInteger(map.get("sdmskill_value"));
////				dto.skillName = Convert.toString(map.get("skill_name"));
////				dto.skilltypeName =Convert.toString(map.get("skilltype_name"));
////				dto.sdmName = Convert.toString(map.get("sdm_name"));
//				listMapSdmSkill.add(map);
//				
//				
//			}
//			System.out.println(listMapSdmSkill);
//			System.out.println(totalItems);
//			
////		}
		
		return new CorePage(listMapSdmSkill, totalItems);			
	}
	

}
