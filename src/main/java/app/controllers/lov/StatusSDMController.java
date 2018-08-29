package app.controllers.lov;

import app.models.StatusSDM;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class StatusSDMController extends LOVController<StatusSDM> {

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("contractype_id");
		model.setLovValues("contractype_name");
	}

}
