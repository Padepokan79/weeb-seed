package app.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("sdmhistory")
@IdName("sdmhistory_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName="sdm_id", parent = Sdm.class)
})

public class SdmHistory extends Model{

	@SuppressWarnings("rawtypes")
	public static List<Map> getSdmActive() {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query ");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT SDM_ID, SDM_Name, SDM_NIk, SDM_ADDRESS, SDM_PHONE, SDM_STARTCONTRACT, SDM_ENDCONTRACT, SDM_STATUS \r\n" + 
				"FROM sdm \r\n" + 
				"WHERE SDM_STATUS = 1 AND CURRENT_DATE > SDM_ENDCONTRACT");
		System.out.println("query : "+query.toString());
		
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	
	public static List<Map> getSdmNonActive() {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query ");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT SDM_ID, SDM_Name, SDM_NIk, SDM_ADDRESS, SDM_PHONE, SDM_STARTCONTRACT, SDM_ENDCONTRACT, SDM_STATUS \r\n" + 
				"FROM sdm \r\n" + 
				"WHERE SDM_STATUS = 0");
		System.out.println("query : "+query.toString());
		
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	
	public static int insertSdmHistory(int sdmId, String startDate, String endDate) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query ");
		StringBuilder query = new StringBuilder();		
		query.append("INSERT INTO sdmhistory ( SDM_ID , SDMHISTORY_STARTDATE, SDMHISTORY_ENDDATE ) \r\n"+
					"VALUES ( ? , ? , ?)");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		params.add(startDate);
		params.add(endDate);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
	
	public static int updateSdmStatus(int sdmId) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query ");
		StringBuilder query = new StringBuilder();		
		query.append("UPDATE sdm \r\n" + 
				"SET SDM_STATUS = 0 \r\n" + 
				"WHERE SDM_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		
		return  Base.exec(query.toString(), params.toArray(new Object[params.size()]));
		
		
	}
	
	
	
}
