package app.controllers.lov;

import org.javalite.activejdbc.Model;

import app.models.Skill;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

public class SkillController extends LOVController<Skill>{
	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("skill_id");
		model.setLovValues("skill_name");
	}
}
