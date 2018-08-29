/**
 * 
 */
package app.controllers.allocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import app.models.SdmSkill;
import app.controllers.api.masterdata.SdmController.SdmDTO;
import app.models.Psychologicals;
import app.models.Sdm;
import app.models.Skill;
import app.models.SkillType;
import core.io.enums.ActionTypes;
import core.io.enums.HttpResponses;
import core.io.helper.JsonHelper;
import core.io.helper.MapHelper;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;


/*
 * Created by Vikri Ramdhani
 * 14 Agustus 2018
 */


/*
 * Modified By Vikri Ramdhani
 * 29 Agustus 2018, 14:40
 * */

public class MultiInsertSdmController extends CRUDController<SdmSkill>{
	
	public class InputSdmSkillDTO extends DTOModel{
		public int skill_id;
		public int skilltype_id;
		public int sdmskill_value;
		public int sdm_id;
	}

	@POST
	public final void multiCreate() {
		boolean input = true;
		ArrayList al = new ArrayList();
		try {
			Base.openTransaction();

			response().setActionType(ActionTypes.CREATE);
		
			Map<String, Object> mapRequest = getRequestBody();
			List<Map<String, Object>> listSDM = MapHelper.castToListMap((List<Map>) mapRequest.get("listsdm"));
			LazyList<SdmSkill> items=SdmSkill.findAll();

			for (Map<String, Object> sdm : listSDM) {
//				System.out.println("SDM : " + JsonHelper.toJson(sdm));
				InputSdmSkillDTO sdmDto = new InputSdmSkillDTO ();
				sdmDto.fromMap(sdm);
				
				System.out.println("SDM DTO : " + JsonHelper.toJson(sdmDto.toMap()));

				SdmSkill sdmModel = new SdmSkill();
				for(SdmSkill asd : items) {
					int id = Convert.toInteger(asd.get("sdm_id"));
					int skillId = Convert.toInteger(asd.get("skill_id"));
					if (sdmDto.sdm_id == id && sdmDto.skill_id == skillId) {
						input = false;
					}
				}
				
				if (input == true) {
					sdmModel.fromMap(sdmDto.toMap());
					if (sdmModel.insert()) {
						System.out.println("Inserted SDM : " + sdmDto.sdm_id);
					}
				}
			}
			
			if (input == true) {
				response().setResponseBody(HttpResponses.ON_SUCCESS_CREATE, listSDM);
			} else {
				response().setResponseBody(HttpResponses.ON_CREATE_REDUNDANT_DATA);
			}
			
			Base.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			response().setResponseBody(e);
			Base.rollbackTransaction();
		}

		sendResponse();
	}

}
