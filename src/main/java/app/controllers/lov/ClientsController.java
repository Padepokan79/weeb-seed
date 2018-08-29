package app.controllers.lov;

import app.models.Clients;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class ClientsController extends LOVController<Clients> {

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("client_id");
		model.setLovValues("client_name");
	}
}
