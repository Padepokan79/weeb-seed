package app.models;

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

}
