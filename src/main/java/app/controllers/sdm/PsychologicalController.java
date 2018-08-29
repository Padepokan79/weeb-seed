package app.controllers.sdm;

import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import app.models.Psychologicals;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class PsychologicalController extends CRUDController<Psychologicals> {
	public class PsychologicalDTO extends DTOModel {
		public int psycoId;
		public String psycoName;
	}
	
	//Created by Alifhar, 26/07/18, 14:55
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		LazyList<Psychologicals> items=Psychologicals.findAll();
		Long totalItems=this.getTotalItems(params);
		
		return new CorePage(items.toMaps(), totalItems);
	}
	
	//Created by Alifhar, 25/07/18, 15:03
	@Override
	public Psychologicals customInsertValidation(Psychologicals item) throws Exception {
		LazyList<Psychologicals> listPsy = Psychologicals.findAll();
		
		for(Psychologicals psy : listPsy) {
			PsychologicalDTO dto = new PsychologicalDTO();
			dto.fromModelMap(psy.toMap());
			if(item.getString("psyco_name").equalsIgnoreCase(dto.psycoName))
				Validation.required(null, "Psychology Name is already exist");
		}
		Validation.required(item.getString("psyco_name"), "Value of Psychology Name can't be empty");
		
		return super.customInsertValidation(item);
	}
	
	//Created by Alifhar, 26/07/18, 10:49
	@Override
	public Map<String, Object> customOnUpdate(Psychologicals item, Map<String, Object> mapRequest) throws Exception {
		LazyList<Psychologicals> listPsy=Psychologicals.findAll();
		String input=mapRequest.get("psyco_name").toString();
		
		for(Psychologicals psy : listPsy) {
			String data = psy.getString("psyco_name");
			int lastId = item.getInteger("psyco_id");
			int id=psy.getInteger("psyco_id");
			if(data.equalsIgnoreCase(input) && lastId!=id)
				Validation.required(null, "Psychology is already exist");
		}
		Validation.required(mapRequest.get("psyco_name"), "Value of Psychology Name can't be empty");
		
		Map<String, Object> result = super.customOnUpdate(item, mapRequest);

		return result;
	}
	
//	@Override
//	public Map<String, Object> customOnDelete(Psychologicals item, Map<String, Object> mapRequest) throws Exception {
//		Map<String, Object> result=super.customOnDelete(item, mapRequest);
//		PsychologicalDTO dto = new PsychologicalDTO();
//		dto.fromModelMap(result);
//		
//		return dto.toModelMap();
//	}
}