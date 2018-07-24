package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("religionlist")
@IdName("id_unik")
@BelongsToParents({
	@BelongsTo(foreignKeyName="religion_id", parent = MdAgama.class)
})
public class ListReligion extends Model{

}
