/**
 * 
 */
package app.models;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

/**
 *web-seed
 * Account.java
 ----------------------------
 * @author Ryan Ahmad Nuriana
 * 14.34.37 19 Jul 2018
 */
@Table("account")
@IdName("account_id")
@BelongsToParents({
	@BelongsTo(foreignKeyName="sdm_id", parent = Sdm.class),
	@BelongsTo(foreignKeyName="useracc_id", parent = User.class)
})
public class Account extends Model{

}
