package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("sdmlanguage")
@IdName("sdmlanguage_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName="language_id", parent = Languages.class),
	@BelongsTo(foreignKeyName="sdm_id" , parent = Sdm.class)
})
public class SdmLanguage extends Model {

}
