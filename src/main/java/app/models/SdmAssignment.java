package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("sdm_assignment")
@IdName("sdmassign_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName="method_id", parent = ProjectMethod.class),
	@BelongsTo(foreignKeyName="method_id", parent = SdmHiring.class),
})
public class SdmAssignment extends Model{

}
