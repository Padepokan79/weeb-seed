/**
 * @author Hendra Kurniawan
 *
 * Date : Jul 27, 2018
 */
package app.controllers.lov;

import app.models.ContractType;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class ContractTypeController extends LOVController<ContractType>{
	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("contracttype_id");
		model.setLovValues("contracttype_name");
	}
}
