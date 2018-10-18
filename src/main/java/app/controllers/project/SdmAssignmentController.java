/*
 Creted by 	: Yana
 Time		:
 Update by	: Yana
 Time 		: 27-07-2018
 */

package app.controllers.project;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activeweb.annotations.GET;
import org.apache.poi.util.CloseIgnoringInputStream;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

import app.models.ProjectMethod;
import app.models.Sdm;
import app.models.SdmAssignment;
import app.models.SdmHiring;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class SdmAssignmentController extends CRUDController<SdmAssignment>{
	public class SdmAssignmentDTO extends DTOModel{
 		public int sdmassignId;
		public int sdmhiringId;
 		public int methodId;
		public int sdmId;
		public int norut;
		public String sdmassignLoc;
		public String sdmassignPicclient;
		public String sdmassignPicclientphone;
		public String methodName;
		public String sdmName;
		public String sdmPhone;
		public String sdmEndcontract;
		
		/*
		 * Updated by Alifhar Juliansyah
		 * 10 August 2018
		 */
		public Date sdmassignStartdate;
 		public Date sdmassignEnddate;

 		public String sdmassign_notification;

	}
	
	/*
	 * Updated by Nurdhiat Chaudhary Malik
	 * 07 Agustus 2018
	 */
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception{
		cekDataAssign();
		DateFormat date = new SimpleDateFormat("dd/MM/yyyy"); 
		
		List<Map<String, Object>> listMapSdmAssignment 	= new ArrayList<Map<String, Object>>();
		LazyList<SdmAssignment> listSdmAssignment 		= (LazyList<SdmAssignment>)this.getItems(params);	
		List<Map<String, Object>> listMapSdmHiring 		= new ArrayList<Map<String, Object>>();
		LazyList<SdmHiring> listSdmHiring				= SdmHiring.findAll();
		List<Map> listdata = new ArrayList<>();
		

		params.setOrderBy("sdmassign_id");

		Long totalItems = this.getTotalItems(params);
	
		/*
		 * Updated by Alifhar Juliansyah	
		 * 30/08/2018
		 */
		int number = 1;
		if(params.limit() != null)
			number = params.limit().intValue()*params.offset().intValue()+1;
	
		for(SdmAssignment sdmassign : listSdmAssignment) {
			ProjectMethod method 	= sdmassign.parent(ProjectMethod.class);
			SdmHiring hiring 		= sdmassign.parent(SdmHiring.class);
			Sdm sdm					= hiring.parent(Sdm.class);
			SdmAssignmentDTO dto = new SdmAssignmentDTO();
			dto.fromModelMap(sdmassign.toMap());
			dto.norut = number;
			number++;
			dto.methodName 	= Convert.toString(method.get("method_name"));
			dto.sdmName 	= Convert.toString(sdm.get("sdm_name"));
			dto.sdmPhone 	= Convert.toString(sdm.get("sdm_phone"));
			int hiringId = Convert.toInteger(sdmassign.get("sdmhiring_id"));
			listdata = SdmAssignment.getEndcontractSdm(hiringId);
			for(Map datasdm : listdata) {
				dto.sdmEndcontract = Convert.toString(datasdm.get("sdm_endcontract"));
				dto.sdmId = Convert.toInteger(datasdm.get("sdm_id"));
			}
//			int asmAssId = Convert.toInteger(sdmassign.get("sdmassign_id"));
//			
//			listData = Sdm.getDataSdmId(sdmAssId);
//			for (Map mapSdmass : listData) {
//				sdmassignnId = Convert.toInteger(mapSdmass.get("SDMASSIGN_ID"));
//			}
//			
			/*
			 * Updated by Alifhar Juliansyah
			 * 10 August 2018
			 */

			java.util.Date currDate = date.parse(getCurrentDate());
			java.util.Date endDate = date.parse(getConvertBulan(sdmassign.get("sdmassign_enddate").toString()));
			java.sql.Date currDate2 = new java.sql.Date(currDate.getTime());
			java.sql.Date endDate2 = new java.sql.Date(endDate.getTime());
			
			Date currentDate = currDate2;
            Date endProject = endDate2;
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(currentDate);
            cal2.setTime(endProject);
            String diff = Convert.toString(mothsBetween(cal1, cal2));
            
//            System.out.println("\nSekarang : "+currentDate);
//            System.out.println("Habis    : "+endProject);
//            System.out.println("---------------------------> dif : "+diff);
            
            if (Double.parseDouble(diff) == 0) {
            	dto.sdmassign_notification = "black"; // notif warna hitam
			}else if(Double.parseDouble(diff) <= 1) {
				dto.sdmassign_notification = "red"; // notif warna merah
			}else if(Double.parseDouble(diff) <= 2) {
				dto.sdmassign_notification = "yellow"; // notif warna kuning
			}else if(Double.parseDouble(diff) <= 4) {
				dto.sdmassign_notification = "green"; // notif warna hijau
			}else if(Double.parseDouble(diff) > 4) {
				dto.sdmassign_notification = "grey"; // notif warna grey
			}
			else {
				dto.sdmassign_notification = "black"; // notif warna hitam
			}
           
//			System.out.println("----------------------------> "+dto.sdmassign_notification+"\n");

			listMapSdmAssignment.add(dto.toModelMap());
			// Modified : Hendra Kurniawan
			// Date 	: 12-09-2018
			// update otomatis hirestat di hiring ketika endproject habis 
			
			List<Map> listdatahirestat = new ArrayList<>();
			
			listdata = SdmHiring.getDataSdmbyEndProject();
			
			int sdmId, clientId, sdmhiringId, countstatusOff=0, statusoff=9, cv79=1;
			System.out.println(listdata);
			for(Map dataSdm : listdata)
			{
				sdmId = Convert.toInteger(dataSdm.get("sdm_id"));
				clientId = Convert.toInteger(dataSdm.get("client_id"));
				sdmhiringId = Convert.toInteger(dataSdm.get("sdmhiring_id"));
				if(clientId != cv79) {
					SdmHiring.updateHireStatIdbyClient(sdmhiringId);	
				}
				
				listdatahirestat = SdmHiring.getStatusHireSDM(sdmId);
				for(Map hirestatSdm : listdatahirestat) {
					if(listdatahirestat.size()>=1) {
						if(Convert.toInteger(hirestatSdm.get("hirestat_id")) == statusoff) {
							countstatusOff++;
						}

						if((listdatahirestat.size()-1) == countstatusOff) {
							SdmHiring.updateHireStatIdbyClient79(sdmId, 1);
						}	
					}
					
				}
			}
		}
		
		return new CorePage(listMapSdmAssignment, totalItems);
	}
	
	/*
	 * Updated by Alifhar Juliansyah
	 * 09 August 2018
	 */
	public String getCurrentDate(){
	    final Calendar c = Calendar.getInstance();
	    int year, month, day;
	    year = c.get(Calendar.YEAR);
	    month = c.get(Calendar.MONTH);
	    day = c.get(Calendar.DATE);
	    return day + "/" + (month+1) + "/" + year;
	}
	
	/*
	 * Updated by Alifhar Juliansyah
	 * 09 August 2018
	 */
	public String getConvertBulan(String date) {
		String bulan[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
		String tanggal = date.substring(8,10);
		String bln = date.substring(5,7);
		String tahun = date.substring(0,4);
		String hasil =tanggal+"/"+bulan[Integer.parseInt(bln)-1]+"/"+tahun;
//		System.out.println("cek : " + hasil);
		return hasil;
	}
	
	/*
	 * Updated by Alifhar Juliansyah
	 * 09 August 2018
	 */
	private static double mothsBetween(Calendar date1, Calendar date2) {
        long lama = -1;
        Calendar tanggal = (Calendar) date1.clone();
        while (tanggal.before(date2)) {
        	if(lama < 0) {
               lama = lama + 1;
        	}
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }
//        double res=lama;
//		System.out.println("--------------------------> lama  : "+lama);
//        return (res)/30;
        if (lama > 30) {
        	lama = (lama)/30;

		}else if(lama < 30 && lama >= 0) {
			lama = 1;
		}else {
			lama = 0;
		}
        
        
        return lama;
    }
	
	/*
	 * Updated by Dewi Roaza  dan  Hendra Kurniawan
	 * 2018-09-19
	 * 
	 */
	@Override
	public Map<String, Object> customOnUpdate(SdmAssignment item, Map<String, Object> mapRequest) throws Exception {
//		LazyList<Sdm> listenddate=Sdm.findAll();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		String endAssign = Convert.toString(mapRequest.get("sdmassign_enddate"));
//		java.util.Date endass = sdf.parse(endAssign);
//		System.out.println(endAssign);
//		System.out.println("end assign" + endass);
//		for(Sdm data : listenddate) {
//			String endsdm = data.getString("sdm_endcontract");
//			java.util.Date endcontract = sdf.parse(endsdm);
//			System.out.println("end contract" + endsdm);
//			if (!(endass.compareTo(endcontract) <= 0)) {
//				Validation.required(null, "Update tanggal akhir outsouce melebihi tanggal kontrak");
//			}
//		}
//		
//		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
//
//		return result;
		
		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
		LazyList<Sdm> listenddate=Sdm.findAll();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		String endAssign = Convert.toString(mapRequest.get("sdmassign_enddate"));
		Integer sdmId = Convert.toInteger(mapRequest.get("sdm_id"));
		
		java.util.Date endassign = sdf.parse(endAssign);
		for(Sdm data : listenddate) {
			if(sdmId == Convert.toInteger(data.get("sdm_id"))) {
				String endsdm = data.getString("sdm_endcontract");
				
				java.util.Date endcontract = sdf.parse(endsdm);		
				System.out.println(endcontract);
				System.out.println(endassign);
				System.out.println(endassign.compareTo(endcontract) <= 0);
				if (endassign.compareTo(endcontract) >= 0) {
					Validation.required(null, "Tanggal tidak boleh melebihi kontrak");
				} else {
					item.save();
				}
			}
		}
		
		return result;
	}

	@SuppressWarnings("rawtypes")
	@GET
	public void cekDataAssign() {
		List<Map> listData = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date currentDate = new Date();
		Date assignEndDate = new Date();
		boolean update79 = false, update = false;
		int sdmhiringId = 0 ,sdmId =0, clientId=0,hirestatId =0, sdmStatus = 0;
		String sdmEncContract ="";
		listData = SdmAssignment.getDataAssign();
		System.out.println(listData.size());
		
		try{
			for(Map dataContractSdm : listData) {
				update79 = false;
				update = false;
				
				 sdmhiringId = Convert.toInteger(dataContractSdm.get("sdmhiring_id"));
				 hirestatId = Convert.toInteger(dataContractSdm.get("hirestat_id"));
				 sdmEncContract = Convert.toString(dataContractSdm.get("sdm_endcontract"));
				 String sdmAssignEndContract = Convert.toString(dataContractSdm.get("sdmassign_enddate"));
				 sdmId = Convert.toInteger(dataContractSdm.get("sdm_id"));
				 int sdmIdAssign = Convert.toInteger(dataContractSdm.get("sdm_id"));
				 Date sdmEncContractDate = sdf.parse(sdmEncContract);
				 Date sdmAssignEndContractDate = sdf.parse(sdmAssignEndContract);
				 clientId = Convert.toInteger(dataContractSdm.get("client_id"));
				 sdmStatus = Convert.toInteger(dataContractSdm.get("sdm_status"));
				 
				 if(sdmAssignEndContractDate.compareTo(currentDate) <= 0 && sdmId == sdmIdAssign && clientId != 1 && hirestatId == 4) {
					 update79 = true;
					 
					System.out.println(sdmEncContractDate);
					System.out.println(currentDate);
					System.out.println("hiringid "+sdmhiringId);
					System.out.println("clientid "+clientId);
				 }
				 if(sdmId == sdmIdAssign && clientId == 1 && sdmStatus == 0) {
					 System.out.println("masuk sini");
					 update = true;
					System.out.println(sdmEncContractDate);
					System.out.println(currentDate);
					System.out.println("hiringid "+sdmhiringId);
					System.out.println("clientid "+clientId);
				 }
				 
				 if(update79) {
						if(clientId != 1) {
							System.out.println("hiringid "+sdmhiringId);
							System.out.println("clientid "+clientId);
							SdmAssignment.updateStatusOff(sdmhiringId);
							SdmAssignment.updateStatusCv79(sdmId);	
						}
						
//						listData = SdmAssignment.getSdmHiringIdCv79(sdmId);
//						for (Map data : listData) {
//							sdmhiringId = Convert.toInteger(data.get("sdmhiring_id"));
//						}
						listData = SdmAssignment.getSdmHiringIdCv79(sdmId);
						for (Map data : listData) {
							sdmhiringId = Convert.toInteger(data.get("sdmhiring_id"));
							System.out.println("sdm hiring id : " + sdmhiringId);
						}			
						SdmAssignment.updateStartDateEnddateAssignCv79(1, sdmhiringId, sdf.format(currentDate), sdmEncContract);
				 }
				 
				 if(update) {
					 SdmAssignment.updateStatusOffcv79(sdmhiringId);
				}
			}
		} catch (Exception e){
			response().setResponseBody(e, 400);
			}
			sendResponse();
		}
}

