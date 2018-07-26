package app.controllers.project;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import app.models.SdmAssignment;
import core.io.model.CorePage;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class SdmAssignmentController extends CRUDController<SdmAssignment>{

	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception{
		
		params.setOrderBy("sdmassign_id");
		LazyList<? extends Model> items = this.getItems(params);
		Long totalItems = this.getTotalItems(params);
		
		return new CorePage(items.toMaps(), totalItems);
	}
}

