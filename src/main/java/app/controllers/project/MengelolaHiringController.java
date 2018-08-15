package app.controllers.project;

/**
 *web-seed
 * MengelolaHiring.java
 ----------------------------
 * @author Vikri Ramdhani
 * 26 Jul 2018
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.iterators.SkippingIterator;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import app.controllers.api.masterdata.SkillController.SkillDTO;
import app.models.Clients;
import app.models.Sdm;
import app.models.SdmHiring;
import app.models.Skill;
import app.models.SkillType;
import app.models.StatusHiring;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;

import core.javalite.controllers.CRUDController;
public class MengelolaHiringController extends CRUDController<SdmHiring>{
	public class HiringDTO extends DTOModel {
		public int sdmhiringId;
		public int hirestatId;
		public int sdmId;
		public int clientId;
		public String sdmName;
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
					
			for (SdmHiring hiring : listHiring) {
				Sdm sdm = hiring.parent(Sdm.class);
				StatusHiring statushiring = hiring.parent(StatusHiring.class);
				Clients clients = hiring.parent(Clients.class);
				
				HiringDTO dto = new HiringDTO();
				dto.fromModelMap(hiring.toMap());
				// dto.clientId = Convert.toInteger(client.get("client_id"));
				// dto.sdmhiringId = Convert.toInteger(SdmHiring.get("sdmhiring_id"));
				dto.sdmName = Convert.toString(sdm.get("sdm_name"));
				dto.clientName = Convert.toString(clients.get("client_name"));
				dto.clientAddress = Convert.toString(clients.get("client_address"));
				dto.clientPicclient = Convert.toString(clients.get("client_picclient"));
				dto.hirestatName = Convert.toString(statushiring.get("hirestat_name"));
				dto.clientMobileclient = Convert.toString(clients.get("client_mobileclient"));
				dto.hirestatId = Convert.toInteger(statushiring.get("hirestat_id")).intValue();
				listMapHiring.add(dto.toModelMap());
			}
		
		return new CorePage(listMapHiring, totalItems);		
	}
	
	
	public Map<String, Object> customOnInsert(SdmHiring item, Map<String, Object> mapRequest) throws Exception {
		Map<String, Object> result = super.customOnInsert(item, mapRequest);
		HiringDTO dto = new HiringDTO();
		dto.fromModelMap(result);
		
		Sdm sdm = item.parent(Sdm.class);
		StatusHiring hirestat = item.parent(StatusHiring.class);
		Clients client = item.parent(Clients.class);
		dto.sdmName = Convert.toString(sdm.get("sdm_name"));
		dto.hirestatName = Convert.toString(hirestat.get("hirestat_name"));
		dto.clientName = Convert.toString(client.get("client_name"));

		return dto.toModelMap();
	}
	
	public SdmHiring customInsertValidation(SdmHiring item) throws Exception {
		Integer sdmId = item.getInteger("sdm_id");
		Integer hirestatId = item.getInteger("hirestat_id");
		Integer clientId = item.getInteger("client_id");
		Integer sdmhiringId = item.getInteger("sdmhiring_id");
		
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(sdmId, "Id Sdm Tidak Boleh Kosong");
		Validation.required(hirestatId, "Id Status Hire tidak boleh kosong");
		Validation.required(clientId, "Id Client tidak boleh kosong");
		Validation.required(sdmhiringId, "Id hiring tidak boleh kosong");

		return super.customInsertValidation(item);
	}
	
//	public Map<String, Object> customOnDelete(SdmHiring item, Map<String, Object> mapRequest) throws Exception {
//			
//		Map<String, Object> result = super.customOnDelete(item, mapRequest);		
//		HiringDTO dto = new HiringDTO();
//		dto.fromModelMap(result);
//		
//		Sdm sdm = item.parent(Sdm.class);
//		StatusHiring hirestat = item.parent(StatusHiring.class);
//		Clients client = item.parent(Clients.class);
//		dto.sdmName = Convert.toString(sdm.get("sdm_name"));
//		dto.hirestatName = Convert.toString(hirestat.get("hirestat_name"));
//		dto.clientName = Convert.toString(client.get("client_name"));
//		
//		return dto.toModelMap();
//	}
	
//	@Override
//	public Map<String, Object> customOnUpdate(SdmHiring item, Map<String, Object> mapRequest) throws Exception {
//				
//		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
//		HiringDTO dto = new HiringDTO();
//		dto.fromModelMap(result);
//		
//		Sdm sdm = item.parent(Sdm.class);
//		StatusHiring hirestat = item.parent(StatusHiring.class);
//		Clients client = item.parent(Clients.class);
//		dto.sdmhiringId = Convert.toInteger(hirestat.get("sdmhiring_id"));
//		dto.sdmName = Convert.toString(sdm.get("sdm_name"));
//		dto.hirestatName = Convert.toString(hirestat.get("hirestat_name"));
//		dto.clientName = Convert.toString(client.get("client_name"));
//		
//		return dto.toModelMap();
//	}
	
}
