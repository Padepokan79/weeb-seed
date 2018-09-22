/**
 * @author Hendra Kurniawan
 *
 * Date : Sep 21, 2018
 */
package app.controllers.allocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

import app.controllers.allocation.DetailSdmSkillController.DetailSdmSkillDTO;
import app.controllers.api.masterdata.MultiFilteringController.MultiFilteringDTO;
import app.models.SdmSkill;
import core.io.enums.ActionTypes;
import core.io.enums.HttpResponses;
import core.io.helper.MapHelper;
import core.io.model.DTOModel;
import core.javalite.controllers.CRUDController;

public class RicianSdmSkillController extends CRUDController<SdmSkill>{

public class DetailSdmSkillDTO extends DTOModel{
		
		public int sdmId;
		public int sdmskillId;
		public String sdmNik;
		public String sdmName;
		public String skilltypeName;
		public String sdmSkillValue ;
		
	}

	@POST
	public final void filter() {
		
		List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
//		LazyList<SdmSkill> asd = (LazyList<SdmSkill>)this.getItems(params);	
//		Long totalItems = this.getTotalItems(params);
		DateFormat dateAwal = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateAkhir = new SimpleDateFormat("dd/MM/yyyy");
		
		try {
			response().setActionType(ActionTypes.READ_ALL);
			
			Map<String, Object> mapRequest = getRequestBody();
			
			List<Map<String, Object>> listParams = MapHelper.castToListMap((List<Map>) mapRequest.get("listsdmskill"));
			int sdm=0;
			for (Map<String, Object> sdmId : listParams) {
				 sdm = Convert.toInteger(sdmId.get("sdm_id"));
			}
			List<Map> listData = new ArrayList<>();
			
			String skillType;
			String sdmSkill;
			String tampungSkill;
			String tampungValue;
			String tampungSkillValue = "";
			String huruf;
			String dataGabungan="";
			String dataGabungan2="";
			
			int index1=0, index2=0, jumlahDataSkill=0, jumlahDataValue=0, jumlahdata=1;
			
			listData = SdmSkill.getDataSdmSkillConcat(sdm);
			
			for(Map list: listData) {
				dataGabungan2="";
				skillType = Convert.toString(list.get("skilltype_name"));
				tampungSkill = Convert.toString(list.get("sdmskill"));
				tampungValue = Convert.toString(list.get("sdmskillvalue"));
				jumlahdata = 1;

				jumlahDataSkill = tampungSkill.length();
				jumlahDataValue = tampungValue.length();
				System.out.println("data 1 " + jumlahDataValue);
				
				for(index1=0; index1 < jumlahDataValue; index1 ++ ) {
					huruf = tampungValue.substring(index1, index1 + 1);
					if(huruf.equals(",")) {
						jumlahdata++;
					} 
				}
				String [] tampungKataSkill = new String [jumlahdata];
				String [] tampungKataValue = new String [jumlahdata];
				
				index2=0;
				tampungSkillValue="";
				for(index1=0; index1 < jumlahDataSkill; index1 ++ ) {
					huruf = tampungSkill.substring(index1, index1 + 1);
					if(jumlahdata == 1) {
						tampungKataSkill[index2] =  Convert.toString(list.get("sdmskill"));
					}
					if(huruf.equals(",")) {
						tampungKataSkill[index2] = tampungSkillValue;
						tampungSkillValue="";
						index2++;
					} else {
						tampungSkillValue = tampungSkillValue + huruf ;	
						if(index2 == (jumlahdata-1)) {
							tampungKataSkill[index2] = tampungSkillValue;
						}
					} 
				}
				
				index2=0;
				tampungSkillValue="";
				for(index1=0; index1 < jumlahDataValue; index1 ++ ) {
					huruf = tampungValue.substring(index1, index1 + 1);
					if(jumlahdata == 1) {
						tampungKataValue[index2] =  Convert.toString(list.get("sdmskillvalue"));
					}
					if(huruf.equals(",")) {
						tampungKataValue[index2] = tampungSkillValue;
						tampungSkillValue="";
						index2++;
					} else {
						tampungSkillValue = tampungSkillValue + huruf ;	
						if(index2 == (jumlahdata-1)) {
							tampungKataValue[index2] = tampungSkillValue;
						}
					} 
				}
		
				int index=0;
				for(index=0; index < jumlahdata; index++) {
					String separator = ", ";
					if((jumlahdata-1)==index) {
						separator = ".";
					}
					dataGabungan = tampungKataSkill[index] + " (" + tampungKataValue[index] + ")" + separator ;
					dataGabungan2 = dataGabungan2 + dataGabungan;
				}
				
				DetailSdmSkillDTO dto = new DetailSdmSkillDTO();
				dto.sdmId = Convert.toInteger(list.get("sdm_id"));
				dto.skilltypeName = Convert.toString(list.get("skilltype_name"));
				dto.sdmSkillValue = dataGabungan2;
				dto.sdmNik = Convert.toString(list.get("sdm_nik"));
				dto.sdmName = Convert.toString(list.get("sdm_name"));
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
