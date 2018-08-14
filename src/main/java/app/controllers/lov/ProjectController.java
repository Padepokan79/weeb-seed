/**
 * 
 */
package app.controllers.lov;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.models.Project;
import core.io.model.LOVModel;
import core.io.model.PagingParams;
import core.javalite.controllers.LOVController;

/**
 * @author rifanandrian
 * @date Aug 2, 2018
 * @time 8:07:26 AM
 * email : muhamadrifanandrian@gmail.com
 */
public class ProjectController extends LOVController<Project>{

	/* (non-Javadoc)
	 * @see core.javalite.controllers.LOVController#initListOfValueModel(core.io.model.LOVModel)
	 */
	@Override
	public void initListOfValueModel(LOVModel model) {
		model.setLovKey("project_id");
		model.setLovValues("project_name");
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see core.javalite.controllers.LOVController#customOnLoad(core.io.model.PagingParams)
	 */
	@Override
	public List<Map<String, Object>> customOnLoad(PagingParams params) throws Exception {
		List<Map<String, Object>> lisData = new ArrayList<Map<String, Object>>();
		List<Map> dataDariQuery = Project.groupByProjectName();
		for (Map map : dataDariQuery) {
			lisData.add(map);
		}
		
		// TODO Auto-generated method stub
		return lisData;
	}

}
