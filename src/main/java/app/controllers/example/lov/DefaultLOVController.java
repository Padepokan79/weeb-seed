package app.controllers.example.lov;

import app.models.core.master.MasterUser;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class DefaultLOVController extends LOVController<MasterUser>{

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("user_id");
		model.setLovValues("username");
	}
}
