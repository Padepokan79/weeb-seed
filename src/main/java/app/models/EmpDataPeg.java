package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@IdName("id_peg")
@Table("emp_data_peg")
@BelongsToParents({
	@BelongsTo(foreignKeyName="id_agama", parent=MdAgama.class)
})
public class EmpDataPeg extends Model {

}
