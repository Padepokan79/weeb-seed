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
import org.javalite.common.Convert;

@Table("sdm")
@IdName("sdm_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName="sdmlvl_id", parent = SdmLvl.class),
	@BelongsTo(foreignKeyName="contracttype_id", parent = ContractType.class),
	@BelongsTo(foreignKeyName="gender_id", parent = Gender.class),
	@BelongsTo(foreignKeyName="religion_id", parent = Religion.class),
	@BelongsTo(foreignKeyName="health_id", parent = Health.class),
})
public class Sdm extends Model{

	@SuppressWarnings("rawtypes")//create: 18/4/18-jejen
	public static List<Map> getDataCV(int sdmId) {	
		List<Object> params = new ArrayList<Object>();
		System.out.println("masuk query");
		StringBuilder query = new StringBuilder();		
		query.append("select sdm.SDM_NAME, gender.GENDER_NAME, religion.RELIGION_NAME, sdm.SDM_PLACEBIRTH, \r\n" + 
				"sdm.SDM_DATEBIRTH, health.HEALTH_STATUS \r\n" + 
				"FROM sdm, religion, health, gender \r\n" + 
				"WHERE sdm.RELIGION_ID = religion.RELIGION_ID and sdm.HEALTH_ID = health.HEALTH_ID \r\n" + 
				"AND sdm.GENDER_ID = gender.GENDER_ID and sdm.SDM_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		
		return lisdata;
	}
	@SuppressWarnings("rawtypes")//create: 18/4/18-jejen
	public static List<Map> getDataEducation(int sdmId) {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		
		query.append("select sdm.SDM_NAME , education.EDU_NAME, education.EDU_SUBJECT, degree.DEGREE_NAME, \r\n" + 
				"education.EDU_STARTDATE, education.EDU_ENDDATE from sdm, education, degree \r\n" + 
				"WHERE education.SDM_ID = sdm.SDM_ID AND education.DEGREE_ID = degree.DEGREE_ID AND sdm.SDM_ID = ?");
		System.out.println("query : "+query.toString());
		params.add(sdmId);
		List<Map> lisdata = Base.findAll(query.toString(), params.toArray(new Object[params.size()]));
		return lisdata;
	}
	
	@SuppressWarnings("rawtypes")//create: 18/4/18-jejen
	public static List<Map> getLastSdmId() {	
		List<Object> params = new ArrayList<Object>();		
		StringBuilder query = new StringBuilder();		
		query.append("select sdm.SDM_ID\r\n" + 
				"from sdm\r\n" + 
				"ORDER BY sdm.SDM_ID DESC\r\n" + 
				"LIMIT 1;");
//		System.out.println("query : "+query.toString());
//		params.add(sdmId);
		System.out.println("query : "+query.toString());
		List<Map> lisdata = Base.findAll(query.toString());
		return lisdata;
	}
}
