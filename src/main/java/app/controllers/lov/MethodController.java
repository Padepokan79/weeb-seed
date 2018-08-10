package app.controllers.lov;

import app.models.ProjectMethod;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class MethodController extends LOVController<ProjectMethod>{

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("method_id");
		model.setLovValues("method_name");
	}
}
