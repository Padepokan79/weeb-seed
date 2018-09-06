/**
 * 
 */
package app.controllers.project;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.util.HashSet;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;
import com.mysql.fabric.xmlrpc.base.Params;

import app.models.SdmAssignment;
import app.controllers.project.MultiHiringController.InputHiringDTO;
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
 * Updated by Khairil Anwar
 * 29 Agustus 2018 09:30:00
 */

public class MultiInsertHiringAssignController extends CRUDController<SdmAssignment>{
	
	public class InputAssignDTO extends DTOModel{
		public Integer client_id;
		public Integer hirestat_id;
		public Integer method_id;
		public Integer sdm_id;
		public Long sdmhiring_id;
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
			List<Map<String, Object>> params = MapHelper.castToListMap((List<Map>) mapRequest.get("listassignment"));
			List<Map<String, Object>> listHiring = new ArrayList<>();
			List<Map> listData = new ArrayList<>();
			listHiring = validateRedudantInput(params);
			
			int clientId;
			String clientPIC="";
			String clientPhone="";
			Integer workPlace= 0;
			
			Date startdate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR, 1);
			Date enddate = cal.getTime();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			for (Map<String, Object> hiring : listHiring) {
				
				InputAssignDTO sdmhiringDto = new InputAssignDTO();
				sdmhiringDto.fromMap(hiring);


				SdmHiring sdmAssignModel = new SdmHiring();
				
				sdmAssignModel.fromMap(sdmhiringDto.toModelMap());
				System.out.println("SDM Hiring : " + JsonHelper.toJson(hiring));
				if (sdmAssignModel.insert()) {
					System.out.println("Inserted Hiring : " + sdmhiringDto);
				}
				workPlace = Convert.toInteger(hiring.get("method_id"));
				SdmAssignment sdmAssign = new SdmAssignment();
				clientId = Convert.toInteger(hiring.get("client_id"));
				listData = sdmAssign.getClientdata(clientId);
				for (Map mapClient : listData) {
					clientPhone = Convert.toString(mapClient.get("client_mobileclient"));
					clientPIC = Convert.toString(mapClient.get("client_picclient"));
				}
				sdmhiringDto.sdmhiring_id = (Long) sdmAssignModel.getId();
				sdmhiringDto.sdmassign_picclient = clientPIC;
				sdmhiringDto.sdmassign_picclientphone = clientPhone;
				sdmhiringDto.sdmassign_startdate = format.format(startdate);
				sdmhiringDto.sdmassign_enddate = format.format(enddate);
				if(workPlace == 1) {
					sdmhiringDto.sdmassign_loc = "Bandung";
				} else {
					sdmhiringDto.sdmassign_loc = "Luar Bandung";
				}
				
				System.out.println("SDM Hiring DTO : " + JsonHelper.toJson(sdmhiringDto.toMap()));
				sdmAssign.fromMap(sdmhiringDto.toModelMap());
				if (sdmAssign.insert()) {
					System.out.println("Inserted Assignment : " + sdmhiringDto);
				}
			}
			
			response().setResponseBody(HttpResponses.ON_SUCCESS_CREATE, listHiring);

			Base.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			response().setResponseBody(e);
			Base.rollbackTransaction();
		}

		sendResponse();
	}
	
	public static List<Map<String, Object>> validateRedudantInput(List<Map<String, Object>> listHiring){
		List<Map<String, Object>> newListHiring = new ArrayList<>();
		
		int banyakData = listHiring.size();
		int [] sdmIdRedudant = new int[banyakData];
		int [] sdmId = new int[banyakData];
		int index=0;
		int methodId = 0;
		int clientId = 0;
	    int sdmIds = 0;
	    int hirestatId = 0;
	    
		for (Map<String, Object> list : listHiring) {
			sdmIdRedudant[index] = Convert.toInteger(list.get("sdm_id"));
		index++;	
		}
		
		index=0;
		Set<Integer> store = new HashSet<>(); 
		
		for(Integer sdm : sdmIdRedudant) {
			if(store.add(sdm) == true) {
				sdmId[index] = sdm;
				index++;
			}
		}
		
		int [] sdmIdFilter = new int[index];
		for(index=0; index < sdmIdFilter.length; index++) {
			sdmIdFilter[index] = sdmId[index];
			System.out.println("sdm filter :" + sdmIdFilter[index]);
		}
		index=0;
		for(Map<String, Object> list : listHiring) {
			int sdm = Convert.toInteger(list.get("sdm_id"));
			if(sdmId[index] == sdm) {
				newListHiring.add(list); 
						index++;
			}
			
		}
		
		return newListHiring;
	}
}
