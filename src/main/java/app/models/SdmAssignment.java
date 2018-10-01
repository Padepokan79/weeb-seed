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
//		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("UPDATE sdm_assignment "
				+ "SET SDMASSIGN_ENDDATE = ? "
				+ "WHERE SDMASSIGN_ID = ? && CLIENT_ID = ?");
//		System.out.println("query : "+query.toString());
		params.add(startdate);
		params.add(sdmassign);
		params.add(clientId);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
    
    public static List<Map> getSdmassignCv79(int clientId, int sdmId) {
  		List<Object> params = new ArrayList<Object>();
//  		System.out.println("masuk query");
  		StringBuilder query = new StringBuilder();
  		query.append("SELECT assign.SDMASSIGN_ID FROM sdm_assignment as assign, sdm_hiring as hi WHERE assign.sdmhiring_id = hi.sdmhiring_id AND hi.sdm_id = ? AND assign.client_id = ?");
//  		System.out.println("query : "+query.toString());
  		params.add(sdmId);
  		params.add(clientId);
  		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
  		
  		return lisdata;		
  	}
    
    public static List<Map> getEndcontractSdm(int sdmhiringId) {
  		List<Object> params = new ArrayList<Object>();
//  		System.out.println("masuk query");
  		StringBuilder query = new StringBuilder();
  		query.append("SELECT sdm.SDM_ENDCONTRACT, sdm.SDM_STARTCONTRACT , sdm.SDM_ID from sdm INNER JOIN sdm_hiring ON  sdm.SDM_ID = sdm_hiring.SDM_ID WHERE SDMHIRING_ID = ? ");
  		params.add(sdmhiringId);
//  		System.out.println("query : "+query.toString());
  		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
  		
  		return lisdata;		
  	}
    
    public static int updateDataAssignHire(int sdmhiringId, String startDateProject, String endDateProject) {
		
		List<Object> params = new ArrayList<>();
//		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("UPDATE sdm_assignment SET \r\n" + 
				"SDMASSIGN_STARTDATE = ? , \r\n" + 
				"SDMASSIGN_ENDDATE = ? \r\n" + 
				"WHERE SDMHIRING_ID = ? ");
		System.out.println("query : "+query.toString());
		System.out.println(startDateProject);
		System.out.println(endDateProject);
		params.add(startDateProject);
		params.add(endDateProject);
		params.add(sdmhiringId);
		System.out.println("aku ke update assignment");
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
    
public static int insertDataAssignHire(int sdmhiringId, String startDateProject, String endDateProject, String sdmassignPicclient, String sdmassignPicclientphone, int clientId) {
		
		List<Object> params = new ArrayList<>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO sdm_assignment "
				+ "(METHOD_ID, SDMHIRING_ID, SDMASSIGN_STARTDATE, SDMASSIGN_ENDDATE, SDMASSIGN_LOC, SDMASSIGN_PICCLIENT, SDMASSIGN_PICCLIENTPHONE, CLIENT_ID)"
				+ " VALUES (1, ?, ?, ?, 'Bandung' , ? , ? , ? )");
//		System.out.println("query : "+query.toString());
		params.add(sdmhiringId);
		params.add(startDateProject);
		params.add(endDateProject);
		params.add(sdmassignPicclient);
		params.add(sdmassignPicclientphone);
		params.add(clientId);
		System.out.println("aku ke insert");
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
    
public static int updateEndDateCv79(String endDate, int sdmId, int clientId) {
	
	List<Object> params = new ArrayList<>();
	System.out.println("masuk query 79");
	StringBuilder query = new StringBuilder();
	query.append("UPDATE sdm_assignment "
			+ "SET SDMASSIGN_ENDDATE = ? "
			+ "WHERE SDMHIRING_ID = ? && CLIENT_ID = ?");
	System.out.println("query : "+query.toString());
	params.add(endDate);
	params.add(sdmId);
	params.add(clientId);
	return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
}

public static List<Map> getSdmHiringIdCv79(int sdmId) {
	List<Object> params = new ArrayList<Object>();
	System.out.println("masuk query");
	StringBuilder query = new StringBuilder();
	query.append("SELECT SDMHIRING_ID FROM sdm_hiring WHERE CLIENT_ID = 1 && SDM_ID = ? ");
	params.add(sdmId);
	System.out.println("query : "+query.toString());
	List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
	
	return lisdata;		
}

public static List<Map> getDataAssign() {
	List<Object> params = new ArrayList<Object>();
	System.out.println("masuk query");
	StringBuilder query = new StringBuilder();
	query.append("SELECT sdm.SDM_ID, sdm_assignment.SDMASSIGN_ID, sdm_hiring.SDMHIRING_ID,  sdm_hiring.HIRESTAT_ID, sdm.SDM_STARTCONTRACT, "
			+ "sdm.SDM_ENDCONTRACT , sdm_assignment.SDMASSIGN_STARTDATE, sdm_assignment.SDMASSIGN_ENDDATE, sdm_assignment.CLIENT_ID, sdm.SDM_STATUS FROM sdm_assignment \r\n" + 
			"INNER JOIN sdm_hiring ON sdm_assignment.SDMHIRING_ID = sdm_hiring.SDMHIRING_ID \r\n" + 
			"INNER JOIN sdm ON sdm.SDM_ID = sdm_hiring.SDM_ID ORDER BY sdm_assignment.SDMASSIGN_ENDDATE");
	
	System.out.println("query : "+query.toString());
	List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
	
	return lisdata;		
	}
   

public static int updateStatusOff(int sdmhiringId) {
	
	List<Object> params = new ArrayList<>();
	System.out.println("masuk query off");
	StringBuilder query = new StringBuilder();
	query.append("UPDATE sdm_hiring "
			+ "SET HIRESTAT_ID = 9 "
			+ "WHERE SDMHIRING_ID = ? AND CLIENT_ID != 1");
	System.out.println("query : "+query.toString());
	params.add(sdmhiringId);

	return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
}

public static int updateStatusCv79(int sdmId) {
	
	List<Object> params = new ArrayList<>();
	System.out.println("masuk query 79 diterima");
	StringBuilder query = new StringBuilder();
	query.append("UPDATE sdm_hiring "
			+ "SET HIRESTAT_ID = 4 "
			+ "WHERE SDM_ID = ?  && CLIENT_ID = 1");
	System.out.println("query : "+query.toString());
	params.add(sdmId);

	return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
}

public static int updateStartDateEnddateAssignCv79(int clientId, int sdmhiringId, String sdmassignStartdate, String sdmassignEnddate) {
	
	List<Object> params = new ArrayList<>();
	System.out.println("masuk query 79");
	StringBuilder query = new StringBuilder();
	query.append("UPDATE sdm_assignment  \r\n" + 
			"			SET SDMASSIGN_STARTDATE = ? ,\r\n" + 
			"			SDMASSIGN_ENDDATE = ? \r\n" + 
			"			WHERE CLIENT_ID = ? AND SDMHIRING_ID = ? ;\r\n" + 
			"");	
	System.out.println("query : "+query.toString());
	params.add(sdmassignStartdate);
	params.add(sdmassignEnddate);
	params.add(clientId);
	params.add(sdmhiringId);

	return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
}


public static List<Map> getDataHirindId() {
	List<Object> params = new ArrayList<Object>();
	System.out.println("masuk query");
	StringBuilder query = new StringBuilder();
	query.append("select sdmhiring_id from sdm_hiring client_id = 1");
	
	System.out.println("query : "+query.toString());
	List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
	
	return lisdata;		
	}

public static List<Map> getAssignmentId(int sdmhiringId) {
	List<Object> params = new ArrayList<Object>();
	System.out.println("masuk query");
	StringBuilder query = new StringBuilder();
	query.append("select sdmassign_id from sdm_assignment WHERE sdmhiring_id = ? ");
	params.add(sdmhiringId);
	System.out.println("query : "+query.toString());
	List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
	
	return lisdata;		
	}

public static int updateStatusOffcv79(int sdmhiringId) {
	
	List<Object> params = new ArrayList<>();
	System.out.println("masuk query 79 off ");
	StringBuilder query = new StringBuilder();
	query.append("UPDATE sdm_hiring "
			+ "SET HIRESTAT_ID = 9 "
			+ "WHERE SDMHIRING_ID = ? ");
	System.out.println("query : "+query.toString());
	params.add(sdmhiringId);

	return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
}
}
