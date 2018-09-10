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
	
}
