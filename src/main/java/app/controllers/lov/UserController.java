package app.controllers.lov;

import app.models.User;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class UserController extends LOVController<User> {

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("user_id");
		model.setLovValues("username");
	}

}
