package app.controllers.lov;

import app.models.Languages;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class LanguagesController extends LOVController<Languages> {

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("language_id");
		model.setLovValues("language_name");
	}

}
