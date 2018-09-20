package app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.google.common.base.Strings;

@Table("sdm_assignment")
@IdName("sdmassign_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName="method_id", parent = ProjectMethod.class),
	@BelongsTo(foreignKeyName="sdmhiring_id", parent = SdmHiring.class),
	@BelongsTo(foreignKeyName="client_id", parent = Clients.class),
})
public class SdmAssignment extends Model{
	@SuppressWarnings("rawtypes")
	public static List<Map> getClientdata(int clientId) {
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT CLIENT_PICCLIENT, CLIENT_MOBILECLIENT FROM CLIENTS WHERE CLIENT_ID = ?");
		
		
		System.out.println("query : "+query.toString());
		params.add(clientId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;		
	}
	
    public static int updateHireStatIdWhenOutsource(int sdmId, int clientId) {
		
		List<Object> params = new ArrayList<>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("UPDATE sdm_hiring "
				+ "SET HIRESTAT_ID = 10 "
				+ "WHERE SDM_ID = ? && CLIENT_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		params.add(clientId);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}

    public static List<Map> getSdmHiringId(int clientId) {
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("SELECT SDMASSIGN_ID, SDMHIRING_ID FROM sdm_assignment WHERE CLIENT_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(clientId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;		
	}
    
    public static int updateDataAssign(int sdmassignId, String startDateProject, String endDateProject) {
		
		List<Object> params = new ArrayList<>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("UPDATE sdm_assignment SET \r\n" + 
				"SDMASSIGN_STARTDATE = ? , \r\n" + 
				"SDMASSIGN_ENDDATE = ? \r\n" + 
				"WHERE SDMASSIGN_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(startDateProject);
		params.add(endDateProject);
		params.add(sdmassignId);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
    
    /*
     * dewi
     * */
    public static int updateEndDateWhenOutsource(String startdate, int sdmassign, int clientId) {
		
		List<Object> params = new ArrayList<>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("UPDATE sdm_assignment "
				+ "SET SDMASSIGN_ENDDATE = ? "
				+ "WHERE SDMASSIGN_ID = ? && CLIENT_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(startdate);
		params.add(sdmassign);
		params.add(clientId);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
    
    public static List<Map> getSdmassignCv79(int clientId, int sdmId) {
  		List<Object> params = new ArrayList<Object>();
  		System.out.println("masuk query");
  		StringBuilder query = new StringBuilder();
  		query.append("SELECT assign.SDMASSIGN_ID FROM sdm_assignment as assign, sdm_hiring as hi WHERE assign.sdmhiring_id = hi.sdmhiring_id AND hi.sdm_id = ? AND assign.client_id = ?");
  		System.out.println("query : "+query.toString());
  		params.add(sdmId);
  		params.add(clientId);
  		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
  		
  		return lisdata;		
  	}
      
}
