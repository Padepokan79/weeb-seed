package app.controllers.example.lov;

import java.util.List;
import java.util.Map;

import app.models.core.master.MasterUser;
import core.io.model.LOVModel;
import core.io.model.PagingParams;
import core.javalite.controllers.LOVController;

public class CustomLOVController extends LOVController<MasterUser>{

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("user_id");
		model.setLovValues("username");	
	}
	
	@Override
	public List<Map<String, Object>> customOnLoad(PagingParams params) throws Exception {
		params.setFilter("username = ? and is_active = ?", "Ichione", 1);
		params.setOrderBy("user_id");
		return super.customOnLoad(params);
	}
}
