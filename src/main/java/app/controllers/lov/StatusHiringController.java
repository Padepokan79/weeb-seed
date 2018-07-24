package app.controllers.lov;

import app.models.StatusHiring;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class StatusHiringController extends LOVController<StatusHiring> { //<nama model tanpa controller>	
	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("hirestat_id");
		model.setLovValues("hirestat_name");
	}
}
