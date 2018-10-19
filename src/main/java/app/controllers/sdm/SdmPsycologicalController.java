package app.controllers.sdm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

import app.controllers.sdm.PsychologicalController.PsychologicalDTO;
import app.models.Psychologicals;
import app.models.Religion;
import app.models.Sdm;
import app.models.SdmHiring;
import app.models.SdmPsycological;
import core.io.helper.MapHelper;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/*
 * Created by Alifhar Juliansyah
 * 27-07-2018, 07:38
 */
public class SdmPsycologicalController extends CRUDController<SdmPsycological> {
	public class SdmPsycologicalDTO extends DTOModel {
		public int sdmpsycologicalId;
		public int sdmId;
		public int psycoId;
		public int norut;
		public String sdmName;
		public String psycoName;
		public String sdmpsycologicalDesc;
		public String psycologicalDate;
		public int clientId;
		public String clientName;
		public int sdmhiringId;
	}
	
	
	/*
	 * Updated by Nurdhiat Chaudhary Malik
	 * 07 Agustus 2018
	 */
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		int clientId=1;
		LazyList<SdmPsycological> items = (LazyList<SdmPsycological>)this.getItems(params);
		Long totalItems = this.getTotalItems(params);
//		clientId = Convert.toInteger(param("client_id"));
		List<Map<String, Object>> ListMapSdmPsy = new ArrayList<Map<String,Object>>();
		System.out.println("Read All");
//        System.out.println(param("clientId"));
        if(param("clientId") == null){
        	clientId = 1;
        } else {
        	clientId = Convert.toInteger(param("client_id"));
        }
		/*
		 * Updated by Alifhar Juliansyah
		 * 29/08/2018
		 */
		int number = 1;
		if(params.limit() != null)
			number = params.limit().intValue() * params.offset().intValue() + 1;
		List<Map> listData = SdmPsycological.getAllData(clientId);
		System.out.println(listData);
//		
//		for(SdmPsycological sdmPsy : items) {
//			Sdm sdm = sdmPsy.parent(Sdm.class);
//			Psychologicals psy = sdmPsy.parent(Psychologicals.class);
//			SdmHiring hiring = sdmPsy.parent(SdmHiring.class);
		
		for(Map data : listData) {
		
			SdmPsycologicalDTO dto = new SdmPsycologicalDTO();
			
			//			System.out.println(sdmPsy.toMap());
			dto.norut = number;
			number++;
			dto.sdmName = Convert.toString(data.get("sdm_name"));
			dto.psycoName = Convert.toString(data.get("psyco_name"));
			dto.clientId = Convert.toInteger(data.get("client_id"));
			dto.psycologicalDate = Convert.toString(data.get("psycological_date"));
			dto.sdmpsycologicalDesc = Convert.toString(data.get("sdmpsycological_desc"));
			ListMapSdmPsy.add(dto.toModelMap());
		}
		return new CorePage(ListMapSdmPsy, totalItems);
	}
	
	@Override
	public SdmPsycological customInsertValidation(SdmPsycological item) throws Exception {
		Integer sdm = item.getInteger("sdm_id");
		List<Map> listData = new ArrayList<>();
		listData = SdmPsycological.getStatus(sdm);
		
		for(Map sd: listData) {
			int status = Convert.toInteger(sd.get("sdm_status"));
			if(status == 0) {
			Validation.required(null, "Status SDM harus Active!!");
			Validation.required(null, "woi");
			}
			
  		}
		return super.customInsertValidation(item);
	}
	
	@Override
	public Map<String, Object> customOnUpdate(SdmPsycological item, Map<String, Object> mapRequest) throws Exception {
		String desc = mapRequest.get("sdmpsycological_desc").toString();
		
		Validation.required(desc, "Valued of Description can't be empty");
		
		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
		
		SdmPsycologicalDTO dto = new SdmPsycologicalDTO();
		dto.fromModelMap(result);
		
		Sdm sdm = item.parent(Sdm.class);
		Psychologicals psy = item.parent(Psychologicals.class);
		dto.sdmName = Convert.toString(sdm.get("sdm_name"));
		dto.psycoName = Convert.toString(psy.get("psyco_name"));
		
		return dto.toModelMap();
	}
	
	//Modified by : Hendra Kurniawan
	//Date        : 18-10-2018
	
	/* (non-Javadoc)
	 * @see core.javalite.controllers.CRUDController#customOnInsert(org.javalite.activejdbc.Model, java.util.Map)
	 */
	@Override
	public Map<String, Object> customOnInsert(SdmPsycological item, Map<String, Object> mapRequest) throws Exception {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listSDM = MapHelper.castToListMap((List<Map>) mapRequest.get("listpsychology"));
		int sdmhiringId = 0;
		int sdmId =0;
		int sdmpsycologicalId = 0;
		System.out.println("Insert Psycology");
		for(Map<String, Object> sdm : listSDM ) {
			sdmhiringId = Convert.toInteger(sdm.get("sdmhiring_id"));
			sdmId = Convert.toInteger(sdm.get("sdm_id"));
		}
        
		String psycologicalDate = getCurrentDate();
		String sdmpsycologicalDesc = "-";
		int psycoId = 2;
		
		List<Map> listdata = SdmPsycological.getDataSdmPsycoId();
		for(Map data : listdata) {
			sdmpsycologicalId = Convert.toInteger(data.get("sdmpsycological_id"));
		}
		
		item.set("sdmpsycological_id", sdmpsycologicalId + 1);
		item.set("sdmhiring_id", sdmhiringId);
		item.set("sdm_id", sdmId);
		item.set("psycological_date",psycologicalDate);
		item.set("sdmpsycological_desc" , sdmpsycologicalDesc);
		item.set("psyco_id" , psycoId);
		item.save();
		
		return super.customOnInsert(item, mapRequest);
		
	}
	
	public String getCurrentDate(){
	    final Calendar c = Calendar.getInstance();
	    int year, month, day;
	    year = c.get(Calendar.YEAR);
	    month = c.get(Calendar.MONTH);
	    day = c.get(Calendar.DATE);
	    return year + "-" + (month+1) + "-" + day;
	}
}
