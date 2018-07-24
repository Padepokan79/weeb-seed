/**
 * 
 */
package app.controllers.lov;

import app.models.Religion;
import core.io.model.LOVModel;
import core.javalite.controllers.LOVController;

/**
 *web-seed
 * LovReligionController.java
 ----------------------------
 * @author Ryan Ahmad Nuriana
 * 09.09.05 20 Jul 2018
 */
public class ReligionController extends LOVController<Religion> {

	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("religion_id");
		model.setLovValues("religion_name");
	}
}
