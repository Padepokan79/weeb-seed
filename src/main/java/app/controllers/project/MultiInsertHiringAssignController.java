/**
 * 
 */
package app.controllers.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import app.models.SdmSkill;
import app.models.Sdm;
import app.models.SdmHiring;
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
 * Created by Khairil Anwar
 * 21 Agustus 2018
 */

public class MultiInsertHiringAssignController extends CRUDController<SdmHiring>{
	
	public class InputAssignDTO extends DTOModel{
		public int client_id;
		public int sdm_id;
		public int hiringstat_id;
		public int sdmhiring_id;
        public String sdmassign_startdate;
        public String sdmassign_enddate;
        public String sdmassign_loc;
        public String sdmassign_picclient;
        public String sdmassign_picclientphone;
	}

	@POST
	public final void multiCreate() {
		try {
			Base.openTransaction();

			response().setActionType(ActionTypes.CREATE);
		
			Map<String, Object> mapRequest = getRequestBody();
			List<Map<String, Object>> listAssignment = MapHelper.castToListMap((List<Map>) mapRequest.get("listassignment"));
			
			for (Map<String, Object> sdm : listAssignment) {
				System.out.println("SDM Assignment : " + JsonHelper.toJson(sdm));
				InputAssignDTO sdmassignDto = new InputAssignDTO ();
				sdmassignDto.fromMap(sdm);

				System.out.println("SDM Assignment DTO : " + JsonHelper.toJson(sdmassignDto.toMap()));

				SdmSkill sdmModel = new SdmSkill();
				sdmModel.fromMap(sdmassignDto.toModelMap());
				if (sdmModel.insert()) {
					System.out.println("Inserted Assigning : " + sdmassignDto.sdm_id);
				}
			}
			
			response().setResponseBody(HttpResponses.ON_SUCCESS_CREATE, listAssignment);

			Base.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			response().setResponseBody(e);
			Base.rollbackTransaction();
		}

		sendResponse();
	}

}
