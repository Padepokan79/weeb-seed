package app.controllers.project;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *web-seed
 * MengelolaHiring.java
 ----------------------------
 * @author Vikri Ramdhani
 * 26 Jul 2018
 */

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.iterators.SkippingIterator;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activeweb.annotations.GET;
import org.javalite.activeweb.annotations.PUT;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

import app.controllers.api.masterdata.SkillController.SkillDTO;
import app.models.Clients;
import app.models.Sdm;
import app.models.SdmAssignment;
import app.models.SdmHiring;
import app.models.Skill;
import app.models.SkillType;
import app.models.StatusHiring;
import core.io.enums.HttpResponses;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;

import core.javalite.controllers.CRUDController;
public class MengelolaHiringController extends CRUDController<SdmHiring>{
	public class HiringDTO extends DTOModel {
		public int norut;
		public int sdmhiringId;
		public int hirestatId;
		public int sdmId;
		public int clientId;
		public String sdmName;
		public String sdmPhone;
		public String clientName;
		public String clientAddress;
		public String clientPicclient;
		public String clientMobileclient;
		public String hirestatName;
	}
	
	/*
	 * Updated by Nurdhiat Chaudhary Malik
	 * 07 Agustus 2018
	 */
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
    
		List<Map<String, Object>> listMapHiring = new ArrayList<Map<String, Object>>();
		LazyList<SdmHiring> listHiring = (LazyList<SdmHiring>)this.getItems(params);	
		params.setOrderBy("sdmhiring_id");
		
//		LazyList<? extends Model> items = this.getItems(params);
		Long totalItems = this.getTotalItems(params);
		
		// Modified : Hendra Kurniawan
		// Date 	: 12-09-2018
		List<Map> listdata = new ArrayList<>();
		List<Map> listdata2 = new ArrayList<>();
		
		listdata = SdmHiring.getDataSdmbyEndProject();
		
		int sdmId, clientId, sdmhiringId;
		
		for(Map dataSdm : listdata)
		{
			sdmId = Convert.toInteger(dataSdm.get("sdm_id"));
			clientId = Convert.toInteger(dataSdm.get("client_id"));
			sdmhiringId = Convert.toInteger(dataSdm.get("sdmhiring_id"));
//			SdmHiring.updateHireStatIdbyClient(sdmhiringId);
//			SdmHiring.updateHireStatIdbyClient79(sdmId, 1);
		}
		
//		for(Map dataSdm : listdata2) {
//			sdmId1 = Convert.toInteger(dataSdm.get("sdm_id"));
//			clientId1 = Convert.toInteger(dataSdm.get("client_id"));
//			sdmhiringId1 = Convert.toInteger(dataSdm.get("sdmhiring_id"));
//			hireId = Convert.toInteger(dataSdm.get("hirestat_id"));
//			System.out.println("Ini listdata2");
//			System.out.println(clientId1);
//			System.out.println(hireId);
//			
//			if((clientId1 == 1 && hireId != 4) || (clientId1 != 1 && hireId != 4)) {
//				System.out.println("Masuk sini gak");
//				SdmHiring.updateHireStat79(sdmhiringId1);
//				
//			}
////			else {
////				System.out.println("Si "+sdmhiringId1+" ngubah");
////			}
//		}
		
		/*
		* Created By  : Rizaldi
		* Date Assign : 30-08-2018 08:57
		*/
		int number=1;
		if(params.limit()!=null || params.offset()!=null)
		number = params.limit().intValue() * params.offset().intValue()+1;
			for (SdmHiring hiring : listHiring) {
				Sdm sdm = hiring.parent(Sdm.class);
				StatusHiring statushiring = hiring.parent(StatusHiring.class);
				Clients clients = hiring.parent(Clients.class);
				
				HiringDTO dto = new HiringDTO();
				dto.fromModelMap(hiring.toMap());
				// dto.clientId = Convert.toInteger(client.get("client_id"));
				// dto.sdmhiringId = Convert.toInteger(SdmHiring.get("sdmhiring_id"));
				String status = Convert.toString(sdm.get("sdm_status"));
				if(status.equals("1")) {
					dto.norut = number;
					number++;
					dto.sdmName = Convert.toString(sdm.get("sdm_name"));
					dto.sdmPhone = Convert.toString(sdm.get("sdm_phone"));
					dto.clientName = Convert.toString(clients.get("client_name"));
					dto.clientAddress = Convert.toString(clients.get("client_address"));
					dto.clientPicclient = Convert.toString(clients.get("client_picclient"));
					dto.hirestatName = Convert.toString(statushiring.get("hirestat_name"));
					dto.clientMobileclient = Convert.toString(clients.get("client_mobileclient"));
					dto.hirestatId = Convert.toInteger(statushiring.get("hirestat_id")).intValue();
					listMapHiring.add(dto.toModelMap());
				}else {
					System.out.println("non-aktif boss!!");
				}
				
			}
		
		return new CorePage(listMapHiring, totalItems);		
	}
	
	public String getCurrentDate(){
	    final Calendar c = Calendar.getInstance();
	    int year, month, day;
	    year = c.get(Calendar.YEAR);
	    month = c.get(Calendar.MONTH);
	    day = c.get(Calendar.DATE);
	    return year + "-" + (month+1) + "-" + day ;
	}
	
	@SuppressWarnings("rawtypes")
	@PUT
	public void updateSdm() throws ParseException { 
	int sdmhiring_id = Convert.toInteger(param("sdmhiring_id"));
	int hireStat_id = Convert.toInteger(param("hirestat_id"));
	int client_id = Convert.toInteger(param("client_id"));
	System.out.println("aku update sdm");
	
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	Date curren = new Date();
//	System.out.println(client_id);
	String currentDate = getCurrentDate();
//	System.out.println(currentDate);
//	Date current = sdf.parse(currentDate);
//	System.out.println(client_id);
//	String endDate = Convert.toString(current);
	
	System.out.println(sdmhiring_id);
		List<Map> listdata2 = new ArrayList<>();
		int sdmId1, clientId1, sdmhiringId1, hireId;
		try {
		SdmHiring.updateHire(sdmhiring_id, hireStat_id);
		if(hireStat_id == 9) {
			System.out.println("Update Masuk");
			System.out.println(currentDate);
			System.out.println(sdmhiring_id);
			System.out.println(client_id);
			SdmAssignment.updateEndDateCv79(currentDate, sdmhiring_id, client_id);
			if(client_id != 1 && hireStat_id == 9) {
				System.out.println("Masuk sini gak 2");
				
			}
		}
		listdata2 = SdmHiring.getDataSdmbyOnProject(sdmhiring_id);
		for(Map dataSdm : listdata2) {
			clientId1 = Convert.toInteger(dataSdm.get("client_id"));
			sdmhiringId1 = Convert.toInteger(dataSdm.get("sdmhiring_id"));
			hireId = Convert.toInteger(dataSdm.get("hirestat_id"));
			sdmId1 = Convert.toInteger(dataSdm.get("sdm_id"));
			String akhirKontrak = Convert.toString(dataSdm.get("sdm_endcontract"));
			System.out.println("Ini listdata2");
			System.out.println(clientId1);
			System.out.println(hireId);
			
			if(hireId != 4) {
				if(clientId1 == 1 && hireId != 4) {
					System.out.println("Masuk sini gak");
					SdmAssignment.updateStartDateEnddateAssignCv79(clientId1,  sdmhiringId1, currentDate, akhirKontrak);
					if(hireStat_id == 9 && client_id != 1) {
						SdmHiring.updateHireStat79(sdmhiringId1);
					}
						
				}
				
			}
			else {
				
			}
			response().setResponseBody(HttpResponses.ON_SUCCESS);
		}
		}catch(Exception e) {
			response().setResponseBody(e, 400);
		}
		sendResponse();
		
		//return result;
	}
	//Modified by : Dewi Roaza
    //Date        : 10/09/2018 
    @Override
	public Map<String, Object> customOnDelete(SdmHiring item, Map<String, Object> mapRequest) throws Exception {
			LazyList<SdmAssignment> list = SdmAssignment.findAll();
			Map<String, Object> result = null;
			for(SdmAssignment type: list) {
					HiringDTO dto = new HiringDTO();
					dto.fromModelMap(type.toMap());
					if (item.getString("sdmhiring_id").equalsIgnoreCase(Convert.toString(dto.sdmhiringId))) {
							Validation.required(null, "Hiring SDM tidak bisa dihapus, masih terdata pada SDM Assignment");
					}
			}
			result = super.customOnDelete(item, mapRequest);
			return result;
			
	}
	/* (non-Javadoc)
	 * @see core.javalite.controllers.CRUDController#customOnUpdate(org.javalite.activejdbc.Model, java.util.Map)
	 */
	@Override
	public Map<String, Object> customOnUpdate(SdmHiring item, Map<String, Object> mapRequest) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("aku custom on update");
		return super.customOnUpdate(item, mapRequest);
	}
}
