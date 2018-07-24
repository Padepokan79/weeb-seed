package app.controllers.lov;

import app.models.Health;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class HealthController extends LOVController<Health>{

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("health_id");
		model.setLovValues("health_status");
	}
}
