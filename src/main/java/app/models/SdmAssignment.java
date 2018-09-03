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
}
