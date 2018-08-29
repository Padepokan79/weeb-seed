package app.controllers.lov;

import app.models.SdmLvl;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class SdmLvlController extends LOVController<SdmLvl>{
	
	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("sdmlvl_id");
		model.setLovValues("sdmlvl_name");
	}

}
