package app.controllers.lov;

import org.javalite.activejdbc.Model;

import app.models.Degree;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;
//LOV
public class DegreeController extends LOVController<Degree>{

	@Override
	public void initListOfValueModel(LOVModel model) {
		// TODO Auto-generated method stub
		model.setLovKey("degree_id");
		model.setLovValues("degree_name");
	}

}
