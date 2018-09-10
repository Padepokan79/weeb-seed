/**
 * 
 */
package app.controllers.allocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

import app.models.SdmSkill;
import app.models.Project;
import app.models.Sdm;
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

/**
 * @author rifanandrian
 * @date Jul 25, 2018
 * @time 8:29:28 AM
 * email : muhamadrifanandrian@gmail.com
 */
public class MengelolaSdmSkillController extends CRUDController<SdmSkill>{
	
	public class MengelolaSdmSkillDTO extends DTOModel{

		public String skilltypeName;
		public String skillName;
		public String sdmNik;
		public String sdmName;
		public int sdmskillId;
		public int skillId;
		public int skilltypeId;
		public int sdmId;
		public int sdmskillValue;
		public String endContractproject;
		public String sdm_notification;
		public int norut;
		public int sdmStatus;
		
	}
	
	/*
	 * Updated by Nurdhiat Chaudhary Malik
	 * 07 Agustus 2018
	 */
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();
//		params.setFilter("sdm_status = 1");
		params.setOrderBy("sdm_id");
		
		
		LazyList<SdmSkill> asd = (LazyList<SdmSkill>)this.getItems(params);	
		Long totalItems = this.getTotalItems(params);
			
		DateFormat dateAwal = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dateAkhir = new SimpleDateFormat("dd/MM/yyyy");
//		LazyList<? extends Model> items = this.getItems(params);
		
//		  Updated by Hendra Kurniawan
//		  16 Agustus 2018 9:02 AM
		 
		
		List<Map> listData = new ArrayList<>();			
		String endDate="";
		
		int number = 1;
		if(params.limit() != null)
			number = params.limit().intValue() * params.offset().intValue() + 1;
		
			for (SdmSkill mengelolaSdmSkill : asd) {
				SkillType skillType = mengelolaSdmSkill.parent(SkillType.class);
				Skill kemampuan = mengelolaSdmSkill.parent(Skill.class);
				Sdm sdm = mengelolaSdmSkill.parent(Sdm.class);
				endDate = "-";
				
				int sdmId = Convert.toInteger(mengelolaSdmSkill.get("sdm_id"));	
				List<Map> dataFromQuery = SdmSkill.getEndContract(sdmId);
					for(Map map : dataFromQuery) {
						endDate = Convert.toString(map.get("project_enddate"));
					}
				
					
				MengelolaSdmSkillDTO dto = new MengelolaSdmSkillDTO();
				dto.fromModelMap(mengelolaSdmSkill.toMap());
				dto.skilltypeName = Convert.toString(skillType.get("skilltype_name"));
				dto.skillName = Convert.toString(kemampuan.get("skill_name"));
				dto.sdmName = Convert.toString(sdm.get("sdm_name"));
				dto.sdmStatus = Convert.toInteger(sdm.get("sdm_status"));
				dto.sdmNik = Convert.toString(sdm.get("sdm_nik"));
				dto.endContractproject = getConvertEndProject(endDate);
				dto.norut = number;
				number++;
				
				java.util.Date judAwl = dateAwal.parse(getCurrentDate());
				java.util.Date judAkhir = dateAkhir.parse(getConvertBulan(sdm.get("sdm_endcontract").toString()));
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

				if(Convert.toInteger(sdm.get("sdm_status")) != 0) {
					listMapSdmSkill.add(dto.toModelMap());
				}
				
			}
		
		return new CorePage(listMapSdmSkill, totalItems);			
	}
	
	/*
	 * Updated by Hendra Kurniawan
	 * 17 Agustus 2018 : 9:33 PM
	 */
	
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
	
	@Override
	public SdmSkill customInsertValidation(SdmSkill item) throws Exception {
		String skillId = item.getString("skill_id");
		String skilltypeId = item.getString("skilltype_id");
		String sdmskillValue = item.getString("sdmskill_value");
		String sdmId = item.getString("sdm_id");

		
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(sdmId, "Sdm tidak boleh kosong");
		Validation.required(skillId, "Skill tidak boleh kosong!");
		Validation.required(skilltypeId, "Skill Type tidak boleh kosong!");
		Validation.required(sdmskillValue, "Nilai tidak boleh kosong!");


		return super.customInsertValidation(item);
	}


}
