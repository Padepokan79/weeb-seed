/**
 * 
 */
package app.controllers.sdm;

import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import app.models.SdmHistory;
import core.io.model.CorePage;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/**
 *web-seed
 * MengelolaHistoriSdmController.java
 ----------------------------
 * @author Ryan Ahmad Nuriana
 * 14.18.44 24 Jul 2018
 */
public class MengelolaHistoriSdmController extends CRUDController<SdmHistory> {
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		params.setOrderBy("sdm_id");
		LazyList<? extends Model> items = this.getItems(params);
		Long totalItems = this.getTotalItems(params);
		return new CorePage(items.toMaps(), totalItems);				
	}
}
