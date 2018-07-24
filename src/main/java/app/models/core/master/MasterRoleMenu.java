package app.models.core.master;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

@Table("role_menus")
@CompositePK({"role_id", "menu_id"})
public class MasterRoleMenu {

}
