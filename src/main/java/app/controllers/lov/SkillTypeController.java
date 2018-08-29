/**
 * 
 */
package app.controllers.lov;

import app.models.SkillType;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

/**
 * @author rifanandrian
 * @date Jul 20, 2018
 * @time 8:20:19 AM
 * email : muhamadrifanandrian@gmail.com
 */
public class SkillTypeController extends LOVController<SkillType>{

	/* (non-Javadoc)
	 * @see core.javalite.controllers.LOVController#initListOfValueModel(core.io.model.LOVModel)
	 */
	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("skilltype_id");
		model.setLovValues("skilltype_name");
		// TODO Auto-generated method stub
		
	}

}
