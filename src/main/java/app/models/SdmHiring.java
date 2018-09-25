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

@Table ("sdm_hiring")
@IdName ("sdmhiring_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName="client_id", parent = Clients.class),
	@BelongsTo(foreignKeyName="hirestat_id" , parent = StatusHiring.class),
	@BelongsTo(foreignKeyName="sdm_id" , parent = Sdm.class)
})

public class SdmHiring extends Model {
	
	public static List<Map> getDataSdmbyClient(int clientId) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT SDM_ID, HIRESTAT_ID FROM SDM_HIRING WHERE CLIENT_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(clientId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
		
	// Modified by  : Hendra Kurniawan
	// Date			: 12-09-2018
	public static List<Map> getDataSdmbyEndProject(){
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT assign.SDMHIRING_ID, hiring.SDM_ID, hiring.CLIENT_ID,  assign.SDMASSIGN_STARTDATE, assign.SDMASSIGN_ENDDATE \r\n" +
				"FROM sdm_hiring as hiring inner JOIN sdm_assignment as assign on hiring.SDMHIRING_ID = assign.SDMHIRING_ID \r\n" + 
				"WHERE CURRENT_DATE > SDMASSIGN_ENDDATE ORDER BY assign.SDMASSIGN_ENDDATE DESC");
		
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
	
		return lisdata;
	}
	
	public static List<Map> getDataSdmbyHirestat(){
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT SDM_ID, HIRESTAT_ID, CLIENT_ID from sdm_hiring Where HIRESTAT_ID = 4");
		
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
	
		return lisdata;
	}
	
	public static int updateHireStatIdbyClient(int sdmhiringId){
		List<Object> params = new ArrayList<>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("UPDATE sdm_hiring \r\n" + 
				"SET HIRESTAT_ID = 9 \r\n" + 
				"WHERE SDMHIRING_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(sdmhiringId);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
	
	public static int updateHireStatIdbyClient79(int sdmId, int clientId){
		List<Object> params = new ArrayList<>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("UPDATE sdm_hiring \r\n" + 
				"SET HIRESTAT_ID = 4 \r\n" + 
				"WHERE SDM_ID = ? && ClIENT_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		params.add(clientId);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}

	public static List<Map> getStatusHireSDM(int sdmId){
		List<Object> params = new ArrayList<Object>();
//		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT HIRESTAT_ID from sdm_hiring WHERE SDM_ID = ?");
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
	
		return lisdata;
	}
	
	public static List<Map> getDataHiringDesc(){
		List<Object> params = new ArrayList<Object>();
//		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT SDMHIRING_ID , HIRESTAT_ID, SDM_ID, CLIENT_ID from sdm_hiring ORDER BY SDMHIRING_ID DESC LIMIT 1");
	
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
	
		return lisdata;
	}
	
	/*
	 * Updated (Commented) by Ryan Ahmad N
	 * 25 September 2018, 14:50
	 */
	public static List<Map> getSdmHiring_id(int sdmId){
		List<Object> params = new ArrayList<Object>();
		StringBuilder query = new StringBuilder();
		query.append("select sdmhiring_id from sdm_hiring where sdm_id=?");
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	
	public static int insertHiring(int sdmId){
		List<Object> params = new ArrayList<>();
//		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("INSERT INTO sdm_hiring (SDM_ID, HIRESTAT_ID, CLIENT_ID) VALUES ( ?, 4, 1)");
//		System.out.println("query : "+query.toString());
		params.add(sdmId);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
	
	
	public static int updateHiring(int sdmId){
		List<Object> params = new ArrayList<>();
//		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();
		query.append("UPDATE sdm_hiring \r\n" +
				"SET HIRESTAT_ID = 4 \r\n" + 
				"WHERE SDM_ID = ? \r\n" + 
				"AND CLIENT_ID = 1 ");
//		System.out.println("query : "+query.toString());
		params.add(sdmId);
		return Base.exec(query.toString(), params.toArray(new Object[params.size()]));
	}
}