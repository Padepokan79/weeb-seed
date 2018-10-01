/**
 * 
 */
package app.controllers.allocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.DELETE;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import app.models.SdmSkill;
import core.io.enums.ActionTypes;
import core.io.enums.HttpResponses;
import core.io.helper.MapHelper;
import core.io.model.DTOModel;
import core.javalite.controllers.CRUDController;

/**
 * @author rifanandrian
 * @date Sep 28, 2018
 * @time 11:24:47 AM
 * email : muhamadrifanandrian@gmail.com
 */
public class MultiDeleteSdmSkillController extends CRUDController<SdmSkill>{
	
	public class deleteSdmSkillDTO extends DTOModel {
		public int skill_id;
		public int skilltype_id;
		public int sdmskill_value;
		public int sdm_id;
	}
	
	@POST
	public final void multiDelete() {
		boolean delete = true;
		ArrayList al = new ArrayList();
		try {
			Base.openTransaction();
			response().setActionType(ActionTypes.DELETE);
			
			Map<String, Object> mapRequest = getRequestBody();
			List<Map<String, Object>> listSDM = MapHelper.castToListMap((List<Map>) mapRequest.get("listsdm"));
			LazyList<SdmSkill> items=SdmSkill.findAll();
			int sdmId = 0;
			
			for (Map<String, Object> sdm : listSDM) {
				sdmId = Convert.toInteger(sdm.get("sdm_id"));
			}
			
			SdmSkill.deleteDataSkillSdm(sdmId);
			
			if (delete == true) {
				response().setResponseBody(HttpResponses.ON_SUCCESS_DELETE, listSDM);
			} else {
				response().setResponseBody(HttpResponses.ON_FAILED_DELETE);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response().setResponseBody(e);
			Base.rollbackTransaction();
		}

		sendResponse();
	}
	

}
