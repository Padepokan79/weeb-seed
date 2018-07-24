package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table ("course")
@IdName ("course_id")
@BelongsToParents({
@BelongsTo(foreignKeyName="sdm_id", parent = Sdm.class),
})

public class Course extends Model{

}
