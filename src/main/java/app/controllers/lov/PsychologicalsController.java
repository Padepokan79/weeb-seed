package app.controllers.lov;

import app.models.Psychologicals;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class PsychologicalsController extends LOVController<Psychologicals>{
	
	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("psyco_id");
		model.setLovValues("psyco_name");
	}

}
