package app.controllers.lov;

import app.models.Task;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class TaskController extends LOVController<Task> {

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("task_id");
		model.setLovValues("task_id");
	}

}
