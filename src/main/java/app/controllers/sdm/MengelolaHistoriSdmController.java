/**
 * 
 */
package app.controllers.sdm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import app.controllers.sdm.CourseController.CourseDTO;
import app.models.Course;
import app.models.Sdm;
import app.models.SdmHistory;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/**
 *web-seed
 * MengelolaHistoriSdmController.java
 ----------------------------
 * @author Ryan Ahmad Nuriana
 * 14.18.44 24 Jul 2018
 */
public class MengelolaHistoriSdmController extends CRUDController<SdmHistory> {
	public class HistoryDTO extends DTOModel {
		public int sdmhistory_id;
		public int sdm_id;
		public int norut;
		public String sdm_name;
		public String sdm_nik;
		public String sdm_address;
		public String sdm_phone;
		public String sdm_startcontract;
		public String sdm_endcontract;
		public String sdm_status;
	}
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		List<Map<String, Object>> listMapCourse = new ArrayList<Map<String, Object>>();
		LazyList<SdmHistory> listHistory = (LazyList<SdmHistory>)this.getItems(params);	
		params.setOrderBy("sdmhistory_id");

		cekStatusSdm();
		
		Long totalItems = this.getTotalItems(params);
		int number=1;
		if(params.limit()!=null || params.offset()!=null)
			number = params.limit().intValue() * params.offset().intValue()+1;
		
			for (SdmHistory history : listHistory) {
				Sdm sdm = history.parent(Sdm.class);
				HistoryDTO dto = new HistoryDTO();
				dto.fromModelMap(history.toMap());
				dto.norut = number;
				number++;
				dto.sdm_name = Convert.toString(sdm.get("sdm_name"));
				dto.sdm_nik = Convert.toString(sdm.get("sdm_nik"));
				dto.sdm_address = Convert.toString(sdm.get("sdm_address"));
				dto.sdm_phone = Convert.toString(sdm.get("sdm_phone"));
				dto.sdm_startcontract = Convert.toString(history.get("sdmhistory_startdate"));
				dto.sdm_endcontract = Convert.toString(history.get("sdmhistory_enddate"));
				String status = Convert.toString(sdm.get("sdm_status"));
				if(status.equals("1")) {
					dto.sdm_status = "Aktif";
				}
				else {
					dto.sdm_status = "Non-Aktif";
				}
				listMapCourse.add(dto.toModelMap());
			}
		
		return new CorePage(listMapCourse, totalItems);				
	}
	
	
	public void cekStatusSdm() {
		List<Map> listData = new ArrayList<>();
		listData = SdmHistory.getSdmActive();
		int sdmId;
		boolean sukses = false;
		String startContract;
		String endContract;
		
		for(Map sdm : listData) {
			sdmId = Convert.toInteger(sdm.get("sdm_id"));
			
			startContract =Convert.toString(sdm.get("SDM_STARTCONTRACT"));
			endContract = Convert.toString(sdm.get("SDM_ENDCONTRACT"));
			SdmHistory.insertSdmHistory(sdmId, startContract, endContract);
			SdmHistory.updateSdmStatus(sdmId);
			sukses = true;
		}
		System.out.println("Cek status SDM berhasil");
	}	
}
