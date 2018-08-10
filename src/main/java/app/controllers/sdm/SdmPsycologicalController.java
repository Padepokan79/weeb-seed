package app.controllers.sdm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.common.Convert;

import app.models.Psychologicals;
import app.models.Religion;
import app.models.Sdm;
import app.models.SdmPsycological;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

/*
 * Created by Alifhar Juliansyah
 * 27-07-2018, 07:38
 */
public class SdmPsycologicalController extends CRUDController<SdmPsycological> {
	public class SdmPsycologicalDTO extends DTOModel {
		public int sdmpsycologicalId;
		public String sdmName;
		public String psycoName;
		public String sdmpsycologicalDesc;
		public Date psycologicalDate;
		public int num;
	}
	
	
	/*
	 * Updated by Nurdhiat Chaudhary Malik
	 * 07 Agustus 2018
	 */
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		LazyList<SdmPsycological> items = (LazyList<SdmPsycological>)this.getItems(params);
		Long totalItems = this.getTotalItems(params);
		
		List<Map<String, Object>> ListMapSdmPsy = new ArrayList<Map<String,Object>>();
		int nu = 1;
		for(SdmPsycological sdmPsy : items) {
			Sdm sdm = sdmPsy.parent(Sdm.class);
			Psychologicals psy = sdmPsy.parent(Psychologicals.class);
		
			SdmPsycologicalDTO dto = new SdmPsycologicalDTO();
			dto.fromModelMap(sdmPsy.toMap());
			dto.sdmName = Convert.toString(sdm.get("sdm_name"));
			dto.psycoName = Convert.toString(psy.get("psyco_name"));
			dto.num = nu;
			ListMapSdmPsy.add(dto.toModelMap());
			nu++;
		}
		return new CorePage(ListMapSdmPsy, totalItems);
	}
	
	@Override
	public SdmPsycological customInsertValidation(SdmPsycological item) throws Exception {
		String desc = item.getString("sdmpsycological_desc");
		
		Validation.required(desc, "Valued of Description can't be empty");
		
		return super.customInsertValidation(item);
	}
	
	@Override
	public Map<String, Object> customOnUpdate(SdmPsycological item, Map<String, Object> mapRequest) throws Exception {
		String desc = mapRequest.get("sdmpsycological_desc").toString();
		
		Validation.required(desc, "Valued of Description can't be empty");
		
		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
		
		SdmPsycologicalDTO dto = new SdmPsycologicalDTO();
		dto.fromModelMap(result);
		
		Sdm sdm = item.parent(Sdm.class);
		Psychologicals psy = item.parent(Psychologicals.class);
		dto.sdmName = Convert.toString(sdm.get("sdm_name"));
		dto.psycoName = Convert.toString(psy.get("psyco_name"));
		
		return dto.toModelMap();
	}
}
