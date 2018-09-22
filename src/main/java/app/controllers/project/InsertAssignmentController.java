/**
 * 
 */
package app.controllers.project;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;


import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

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
 * Hendra Kuniawan
 * 5 September 2018
 */

public class InsertAssignmentController extends CRUDController<SdmAssignment>{
	
	public class InputAssignDTO extends DTOModel{
		public Integer client_id;
		public Integer hirestat_id;
		public Integer method_id;
		public Integer sdmhiring_id;
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
			List<Map<String, Object>> listAssign = MapHelper.castToListMap((List<Map>) mapRequest.get("listassignment"));
			List<Map> listData = new ArrayList<>();
			System.out.println("datalistassign " + listAssign);
			int clientId;
			int sdmassignnId=0;
			int sdmId1;
			String clientPIC="";
			String clientPhone="";
			Integer workPlace= 0;
			boolean cekHiring = false;
			int sdmassignId=0;
			Date startdate = new Date();
			Date enddate = new Date();
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			
			for (Map<String, Object> assign : listAssign) {
				
				InputAssignDTO sdmassignDto = new InputAssignDTO();
				sdmassignDto.fromMap(assign);

				SdmHiring sdmAssignModel = new SdmHiring();
				sdmAssignModel.fromMap(sdmassignDto.toModelMap());
				
				SdmAssignment sdmAssign = new SdmAssignment();
				
				clientId = Convert.toInteger(assign.get("client_id"));
				listData = sdmAssign.getClientdata(clientId);
				for (Map mapClient : listData) {
					clientPhone = Convert.toString(mapClient.get("client_mobileclient"));
					clientPIC = Convert.toString(mapClient.get("client_picclient"));
				}
				
				//update	: Dewi Roaza
				//Date 		: 2018-09-19
				//mengambil enddate dari contract
				int client = 1;
				Sdm sdm = new Sdm();
				sdmId1 = Convert.toInteger(assign.get("sdm_id"));
				listData = sdm.getDataEndContract(sdmId1);
				for (Map mapSdm : listData) {
					startdate = Convert.toSqlDate(mapSdm.get("sdm_startcontract"));
					enddate = Convert.toSqlDate(mapSdm.get("sdm_endcontract"));
				}
				listData = SdmAssignment.getSdmassignCv79(client, sdmId1);
				for (Map mapSdmass : listData) {
					sdmassignnId = Convert.toInteger(mapSdmass.get("SDMASSIGN_ID"));
				}
				
				sdmassignDto.sdmhiring_id = Convert.toInteger(assign.get("sdmhiring_id"));
				sdmassignDto.sdmassign_picclient = clientPIC;
				sdmassignDto.sdmassign_picclientphone = clientPhone;
				sdmassignDto.sdmassign_startdate = format.format(startdate);
				sdmassignDto.sdmassign_enddate = format.format(enddate);
				workPlace = Convert.toInteger(assign.get("method_id"));
				if(workPlace == 1) {
					sdmassignDto.sdmassign_loc = "Bandung";
				} else {
					sdmassignDto.sdmassign_loc = "Luar Bandung";
				}
				
				System.out.println();
				int sdmId = Convert.toInteger(assign.get("sdm_id"));
				
//				int sdmassign = Convert.toInteger(assign.get("sdmassign_id"));
				System.out.println("ini cleint : " + clientId);
				if(clientId!=1) {
					sdmAssign.updateHireStatIdWhenOutsource(sdmId, client);
					System.out.println(sdmassignDto.sdmassign_startdate);
					System.out.println(Convert.toInteger(assign.get("sdmassign_id")));
					
					System.out.println(client);
					sdmAssign.updateEndDateWhenOutsource(sdmassignDto.sdmassign_startdate, sdmassignnId, client);
				}
				sdmAssign.fromMap(sdmassignDto.toModelMap());
				System.out.println("SDM Hiring DTO : " + JsonHelper.toJson(sdmassignDto.toMap()));
				
				//cek data sdm hiring 
				listData = sdmAssign.getSdmHiringId(clientId);
				for(Map dataHiring : listData) {
					int cekSdmHiringId = Convert.toInteger(dataHiring.get("sdmhiring_id"));
					int inputSdmHiringId = Convert.toInteger(assign.get("sdmhiring_id"));
					sdmassignId = Convert.toInteger(dataHiring.get("sdmassign_id"));
//					System.out.println(cekSdmHiringId == inputSdmHiringId);
//					System.out.println(cekSdmHiringId);
//					System.out.println(inputSdmHiringId);
					if(cekSdmHiringId == inputSdmHiringId) {
						cekHiring = true;
					}
				}
				
				if (cekHiring == false) {	
					//apabila data belum ada yang berelasi dengan sdmhiring_id, maka insert data baru
					sdmAssign.insert();
					System.out.println("Inserted Assignment : " + sdmassignDto);
					response().setResponseBody(HttpResponses.ON_SUCCESS_CREATE, listAssign);
				}
				else {
					//apabila data assign ada yang berelasi dengan sdmhiring_id, maka update data
					sdmAssign.updateDataAssign(sdmassignId, sdmassignDto.sdmassign_startdate, 	sdmassignDto.sdmassign_enddate);
					System.out.println("Update disini");
					response().setResponseBody(HttpResponses.ON_SUCCESS_UPDATE, listAssign);
				}
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
