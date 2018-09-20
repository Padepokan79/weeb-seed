/**
 * @author Hendra Kurniawan
 *
 * Date : Sep 20, 2018
 */
package app.controllers.allocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

import com.ibm.icu.util.Calendar;

import app.controllers.allocation.MengelolaSdmSkillController.MengelolaSdmSkillDTO;
import app.models.Sdm;
import app.models.SdmSkill;
import app.models.Skill;
import app.models.SkillType;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class DetailSdmSkillController extends CRUDController<SdmSkill>{
	
	public class DetailSdmSkillDTO extends DTOModel{
		
		public int sdmId;
		public int sdmskillId;
		public String sdmNik;
		public String sdmName;
		public String skilltypeName;
		public String sdmSkillValue ;
		
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> listMapSdmSkill = new ArrayList<Map<String, Object>>();	
		LazyList<SdmSkill> listskill = (LazyList<SdmSkill>)this.getItems(params);	
		
		Long totalItems = this.getTotalItems(params);
			
		String skillType;
		String sdmSkill;
		String tampungSkill;
		String tampungValue;
		String tampungSkillValue = "";
		String huruf;
		String dataGabungan="";
		String dataGabungan2="";
		
		int index1=0, index2=0, jumlahDataSkill=0, jumlahDataValue=0, jumlahdata=0;
		List<Map> listData = new ArrayList<>();
		listData = SdmSkill.getDataSdmSkillConcat(41);
		
		for(Map list: listData) {
			System.out.println("DATA KE I ");
			skillType = Convert.toString(list.get("skilltype_name"));
			tampungSkill = Convert.toString(list.get("sdmskill"));
			tampungValue = Convert.toString(list.get("sdmskillvalue"));
			jumlahdata = 0;
			
			
			
			jumlahDataSkill = tampungSkill.length();
			jumlahDataValue = tampungValue.length();
			
			for(index1=0; index1 < jumlahDataValue; index1 ++ ) {
				huruf = tampungValue.substring(index1, index1 + 1);
				if(huruf.equals(",")) {
					jumlahdata++;
				} else {
	
				} 
			}
			System.out.println("jumlah data "  + jumlahdata);
			String [] tampungKataSkill = new String [jumlahdata];
			String [] tampungKataValue = new String [jumlahdata];
			
			System.out.println("type name : " + skillType);
			System.out.println("skill : " + tampungSkill);
			System.out.println("value : " + tampungValue);
			System.out.println("panjang String skill : " + tampungSkill.length());
			System.out.println("panjang String value : " + tampungValue.length());
			
			index2=0;
			for(index1=0; index1 < jumlahDataSkill; index1 ++ ) {
				huruf = tampungSkill.substring(index1, index1 + 1);
				if(huruf.equals(",")) {
					tampungKataSkill[index2] = tampungSkillValue;
					tampungSkillValue="";
					index2++;
				} else {
					tampungSkillValue = tampungSkillValue + huruf ;	
				}
				 
			}
			
			index2=0;
			tampungSkillValue="";
			for(index1=0; index1 < jumlahDataValue; index1 ++ ) {
				huruf = tampungValue.substring(index1, index1 + 1);
				if(huruf.equals(",")) {
					tampungKataValue[index2] = tampungSkillValue;
					tampungSkillValue="";
					index2++;
				} else {
					tampungSkillValue = tampungSkillValue + huruf ;	
				}
				 
			}
			
			for(String data: tampungKataSkill) {
				System.out.println("skill :" + data);
			}
			for(String data: tampungKataValue) {
				System.out.println("skill value :" + data);
			}
			int index=0;
			for(index=0; index < index2; index++) {
				dataGabungan = tampungKataSkill[index] + " (" + tampungKataValue[index] + "), ";
				dataGabungan2 = dataGabungan2 + dataGabungan;
			}
			
			System.out.println("data sudah di gabung " + dataGabungan2 );
		}
		
		return new CorePage(listMapSdmSkill, totalItems);
		}
}
