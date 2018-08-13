/**
 * @author Khairil
 * @date 1 August 2018
 * @role SDM LOV
 */
package app.controllers.lov;

import app.models.Sdm;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class SdmController extends LOVController<Sdm>{
	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("sdm_id");
		model.setLovValues("sdm_name");
	}
}