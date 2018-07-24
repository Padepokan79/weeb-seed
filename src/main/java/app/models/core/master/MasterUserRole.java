package app.models.core.master;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

@Table("user_roles")
@CompositePK({"role_id", "user_id"})
public class MasterUserRole {

}
