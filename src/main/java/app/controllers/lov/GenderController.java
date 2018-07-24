package app.controllers.lov;

import app.models.Gender;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class GenderController extends LOVController<Gender>{
	public void initListOfValueModel(LOVModel Gender) {
		Gender.setLovKey("gender_id");
		Gender.setLovValues("gender_name");
	}
}
