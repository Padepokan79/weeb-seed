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
	@BelongsTo(foreignKeyName = "psyco_id", parent=Psychologicals.class)
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

}
