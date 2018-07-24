package app.controllers.example.crud;

import java.util.List;
import java.util.Map;

import app.models.core.master.MasterUser;
import core.io.helper.MapHelper;
import core.io.model.CorePage;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class CustomOnReadAllController extends CRUDController<MasterUser> {
					
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		String username = param("username");
		
		// Dapat digunakan pada CommonController
		List<Map<String, Object>> items = MapHelper.castToListMap(
				MasterUser.search(username, params));

		// Dapat digunakan pada CommonController
		Long totalItems = MasterUser.count(username, params);
		
		return new CorePage(items, totalItems);		
	}
}