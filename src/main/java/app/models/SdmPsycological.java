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

/*
 * Created by Alifhar Juliansyah
 * 27-07-2018, 07:06
 */

@Table("sdmpsycological")
@IdName("sdmpsycological_id")
@BelongsToParents ({
	@BelongsTo(foreignKeyName = "sdm_id", parent = Sdm.class),
	@BelongsTo(foreignKeyName = "psyco_id", parent=Psychologicals.class),
	@BelongsTo(foreignKeyName = "sdmhiring_id", parent=SdmHiring.class)
})
public class SdmPsycological extends Model {
	
	public static List<Map> getStatus(int sdmId) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT SDM_STATUS FROM SDM WHERE SDM_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		
		return lisdata;
	}
	
	public static List<Map> getData(int clientId) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT client_name FROM clients WHERE CLIENT_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(clientId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	
	public static List<Map> getDataSdmPsycoId() {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT sdmpsycological_id FROM sdmpsycological Order by sdmpsycological_id desc limit 1");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	

	public static List<Map> getAllData(int clientId) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SElECT sdm.SDM_NAME, sdmpsycological.SDMPSYCOLOGICAL_ID, sdmpsycological.SDMPSYCOLOGICAL_DESC, sdmpsycological.PSYCOLOGICAL_DATE, sdmpsycological.PSYCO_ID, sdm_hiring.CLIENT_ID, sdm_hiring.SDM_ID, sdm_hiring.SDMHIRING_ID, psychologicals.PSYCO_NAME FROM sdmpsycological INNER JOIN sdm_hiring ON sdmpsycological.SDMHIRING_ID = sdm_hiring.SDMHIRING_ID INNER JOIN sdm ON sdmpsycological.SDM_ID = sdm.SDM_ID INNER join psychologicals on sdmpsycological.PSYCO_ID = psychologicals.PSYCO_ID\r\n" + 
				"where sdm_hiring.CLIENT_ID = ? ");
		System.out.println("query : "+query.toString());
		params.add(clientId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	
	public static List<Map> readAllData() {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SElECT sdm.SDM_NAME, sdmpsycological.SDMPSYCOLOGICAL_ID, sdmpsycological.SDMPSYCOLOGICAL_DESC, sdmpsycological.PSYCOLOGICAL_DATE, sdmpsycological.PSYCO_ID, sdm_hiring.CLIENT_ID, sdm_hiring.SDM_ID, sdm_hiring.SDMHIRING_ID, psychologicals.PSYCO_NAME FROM sdmpsycological INNER JOIN sdm_hiring ON sdmpsycological.SDMHIRING_ID = sdm_hiring.SDMHIRING_ID INNER JOIN sdm ON sdmpsycological.SDM_ID = sdm.SDM_ID INNER join psychologicals on sdmpsycological.PSYCO_ID = psychologicals.PSYCO_ID\r\n" + 
				"  ");
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	
	public static List<Map> cekDataPsycological(int sdmId) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("SELECT SDMHIRING_ID FROM sdmpsycological WHERE SDM_ID = ? \r\n" + 
				" ");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
}
