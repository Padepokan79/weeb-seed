package app.models.core.master;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@Table("logged_in_users")
@IdName("username")
public class MasterLoggedInUser extends Model {

}
