package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("projectmethod")
@IdName("METHOD_ID")
public class ProjectMethod extends Model{
	public static ProjectMethod findByUsername(String methodname) {
		return ProjectMethod.findFirst("METHOD_NAME = ?", methodname);
	}
}
