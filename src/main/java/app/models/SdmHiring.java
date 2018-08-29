package app.models;

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
	
}
