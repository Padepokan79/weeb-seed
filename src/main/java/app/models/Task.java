package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("tasks")
@IdName("task_id")
@BelongsTo(foreignKeyName = "TAS_TASK_ID", parent = Task.class)
public class Task extends Model {

}
