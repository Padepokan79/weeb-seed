package app.controllers.api.masterdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;
import core.io.helper.JsonHelper;


import com.ibm.icu.util.Calendar;

import app.controllers.allocation.MultiInsertSdmController.InputSdmSkillDTO;
import app.controllers.api.masterdata.MengelolaSdmSkillController.MengelolaSdmSkillDTO;
import app.models.Sdm;
import app.models.SdmSkill;
import app.models.Skill;
import app.models.SkillType;
import core.io.enums.ActionTypes;
import core.io.enums.HttpResponses;
import core.io.helper.MapHelper;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;


public class MultiFilteringController extends CRUDController<SdmSkill>{


	public class MultiFilteringDTO extends DTOModel{
		public int sdmskillId;
//		public int skillId;
//		public int skilltypeId;
		public int sdmId;
		public int sdmskillValue;
		public String skillName;
		public String skilltypeName;
		public String sdmName;
		public String sdmNik;
		public String endContractproject;
		public String sdm_notification;
		
	}

	
	@POST
	public final void multiFilter() {
		
		List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
//		LazyList<SdmSkill> asd = (LazyList<SdmSkill>)this.getItems(params);	
//		Long totalItems = this.getTotalItems(params);
		DateFormat dateAwal = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateAkhir = new SimpleDateFormat("dd/MM/yyyy");
		try {
			response().setActionType(ActionTypes.READ_ALL);
			
			Map<String, Object> mapRequest = getRequestBody();
			
			List<Map<String, Object>> listParams = MapHelper.castToListMap((List<Map>) mapRequest.get("listsdm"));
			
			List<Map> listData = new ArrayList<>();
			
//			List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
			
			String sdmId = null;
			String skilltypeId = null;
			String skillId = null;
			String value = null;
			String operator = null;
			int jumlahData= listParams.size();
			System.out.println("Jumlah data :" + jumlahData);
			for (Map<String, Object> sdm : listParams) {
				sdmId = Convert.toString(sdm.get("sdm_id"));
				skilltypeId = Convert.toString(sdm.get("skilltype_id"));
				skillId = Convert.toString(sdm.get("skill_id"));
				value = Convert.toString(sdm.get("sdmskill_value"));
				operator = Convert.toString(sdm.get("operator"));
			}
			
			//1
			if(sdmId != null && skilltypeId == null && skillId == null && value == null) {
				int sdm_id = Convert.toInteger(sdmId);
				listData = SdmSkill.getbySdm(sdm_id);
				
			}//2
			else if(sdmId == null && skilltypeId != null && skillId == null && value == null) {
				if(operator.equals("1") || jumlahData == 1) {
					listData = SdmSkill.getbyCategoryOR(listParams);
				} else if(operator.equals("2") && jumlahData != 1) {
					listData = SdmSkill.getbyCategoryAND(listParams);
				}
			}//3
			else if(sdmId == null && skilltypeId != null && skillId != null && value != null) {
				if(operator.equals("1") || jumlahData == 1) {
					listData = SdmSkill.getbySkillValueOR(listParams);
				} else if(operator.equals("2") && jumlahData != 1) {
					//cari query	
					listData = SdmSkill.getbySkillValueAND(listParams);
				}
			}
			//4
			else if(sdmId != null && skilltypeId != null && skillId == null && value == null) {
				if(operator.equals("1") || jumlahData == 1) {
					listData = SdmSkill.getbySdmCategoryOR(listParams);
				}else if(operator.equals("2") && jumlahData != 1) {
					listData = SdmSkill.getbySdmCategoryAND(listParams);
				}
			}
			//5
			else if(sdmId != null && skilltypeId != null && skillId != null && value != null) {
				if(operator.equals("1") || jumlahData == 1) {
					listData = SdmSkill.getbySdmCategoryValueOR(listParams);
				}else if(operator.equals("2") && jumlahData != 1) {
					listData = SdmSkill.getbySdmCategoryValueAND(listParams);
				}
			}
			else if(sdmId == null && skilltypeId == null && skillId == null && value == null){
				listData = SdmSkill.getAllSdmSkill();
			}
			String endContractProject="";
			MultiFilteringDTO dto = new MultiFilteringDTO();
			
				for (Map map : listData) {
					int sdm_id = Convert.toInteger(map.get("sdm_id"));	
					List<Map> dataFromQuery = SdmSkill.getEndContract(sdm_id);
						for(Map mapproject : dataFromQuery) {
							endContractProject = Convert.toString(mapproject.get("project_enddate"));
						}
					
					dto.sdmskillId = Convert.toInteger(map.get("sdmskill_id"));
					dto.sdmskillValue = Convert.toInteger(map.get("sdmskill_value"));;
					dto.skillName = Convert.toString(map.get("skill_name"));;
					dto.skilltypeName = Convert.toString(map.get("skilltype_name"));;
					dto.sdmName = Convert.toString(map.get("sdm_name"));
					dto.sdmNik = Convert.toString(map.get("sdm_nik"));
					dto.endContractproject = getConvertEndProject(endContractProject);
					dto.sdmId = Convert.toInteger(map.get("sdm_id"));
					
					java.util.Date judAwl = dateAwal.parse(getCurrentDate());
					java.util.Date judAkhir = dateAkhir.parse(getConvertBulan(map.get("sdm_endcontract").toString()));
					java.sql.Date tglAwal = new java.sql.Date(judAwl.getTime());
					java.sql.Date tglAkhir = new java.sql.Date(judAkhir.getTime());
					Date TGLAwal = tglAwal;
		            Date TGLAkhir = tglAkhir;
		            Calendar cal1 = Calendar.getInstance();
		            Calendar cal2 = Calendar.getInstance();
		            cal1.setTime(TGLAwal);
		            cal2.setTime(TGLAkhir);
		            String diff = Convert.toString(mothsBetween(cal1, cal2));
		            
		            if (Integer.parseInt(diff) == 0) {
		            	dto.sdm_notification = "black"; // notif warna hitam
		            	
					}else if(Integer.parseInt(diff) <= 1) {
						dto.sdm_notification = "red"; // notif warna merah
						
					}else if(Integer.parseInt(diff) <= 2) {
						dto.sdm_notification = "yellow"; // notif warna kuning
						
					}else if(Integer.parseInt(diff) <= 4) {
						dto.sdm_notification = "green"; // notif warna hijau
						
					}else if(Integer.parseInt(diff) > 4) {
						dto.sdm_notification = "grey"; // notif warna grey
					}
					
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
	
	private static long mothsBetween(Calendar tanggalAwal, Calendar tanggalAkhir) {
        long lama = 0;
        Calendar tanggal = (Calendar) tanggalAwal.clone();
        while (tanggal.before(tanggalAkhir)) {
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }
        
        if (lama > 30) {
        	lama = (lama)/30;
        	
		}else if(lama < 30 && lama > 0) {
			lama = 1;
		
		}else {
			lama = 0;
		}
        return lama;
        
    }
	
	
	public String getCurrentDate(){
	    final Calendar c = Calendar.getInstance();
	    int year, month, day;
	    year = c.get(Calendar.YEAR);
	    month = c.get(Calendar.MONTH);
	    day = c.get(Calendar.DATE);
	    return day + "/" + (month+1) + "/" + year;
	}
	
	public String getConvertBulan(String now) {
		String bulan[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		String tanggal = now.substring(8,10);
		String bln = now.substring(5,7);
		String tahun = now.substring(0,4);
		String hasil =tanggal+"/"+bulan[Integer.parseInt(bln)-1]+"/"+tahun;
		
		return hasil;
	}
	
	public String  getConvertEndProject(String endProject) {
		
		
		if(endProject.equals("-")) {
			
		} else {
			String tanggal= endProject.substring(8,10);
			String bulan = endProject.substring(5,7);
			String tahun = endProject.substring(0,4);
			
			if(bulan.equals("01")) {
				bulan = "Januari";
			} else if(bulan == "02") {
				bulan = "Februaru";
			} else if(bulan.equals("03")) {
				bulan = "Maret";
			} else if(bulan.equals("04")) {
				bulan = "April";
			} else if(bulan.equals("05")) {
				bulan = "Mei";
			} else if(bulan.equals("06")) {
				bulan = "Juni";
			} else if(bulan.equals("07")) {
				bulan = "Juli";
			} else if(bulan.equals("08")) {
				bulan = "Agustus";
			} else if(bulan.equals("09")) {
				bulan = "September";
			} else if(bulan.equals("10")) {
				bulan = "Oktober";
			} else if(bulan.equals("11")) {
				bulan = "November";
			} else if(bulan.equals("12")) {
				bulan = "Desember";
			}
			
			return tanggal + " " + bulan + " " + tahun;
			}
		return endProject;
		}
}
