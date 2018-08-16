/*
 Creted by 	: Yana
 Time		:
 Update by	: Yana
 Time 		: 27-07-2018
 */

package app.controllers.project;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

import app.models.ProjectMethod;
import app.models.Sdm;
import app.models.SdmAssignment;
import app.models.SdmHiring;
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
		
		DateFormat date = new SimpleDateFormat("dd/MM/yyyy"); 
		
		List<Map<String, Object>> listMapSdmAssignment 	= new ArrayList<Map<String, Object>>();
		LazyList<SdmAssignment> listSdmAssignment 		= (LazyList<SdmAssignment>)this.getItems(params);	
		List<Map<String, Object>> listMapSdmHiring 		= new ArrayList<Map<String, Object>>();
		LazyList<SdmHiring> listSdmHiring				= SdmHiring.findAll();	

		params.setOrderBy("sdmassign_id");
		Long totalItems = this.getTotalItems(params);
		int number = 1;
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
//            System.out.println("-----------------> dif : "+diff);
            
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
//			System.out.println("----------------------------> "+dto.sdmassign_notification);
            
			listMapSdmAssignment.add(dto.toModelMap());

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
        long lama = 0;
        Calendar tanggal = (Calendar) date1.clone();
        while (tanggal.before(date2)) {
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }
        double res=lama;
//		System.out.println("--------------------------> lama  : "+lama);
        return (res)/30;
    }


}

