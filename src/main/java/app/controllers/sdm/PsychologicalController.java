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
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		LazyList<Psychologicals> items=Psychologicals.findAll();
		Long totalItems=this.getTotalItems(params);
		
		return new CorePage(items.toMaps(), totalItems);
	}
	
	@Override
	public Psychologicals customInsertValidation(Psychologicals item) throws Exception {
		LazyList<Psychologicals> listPsy = Psychologicals.findAll();
		
		for(Psychologicals psy : listPsy) {
			PsychologicalDTO dto = new PsychologicalDTO();
			dto.fromModelMap(psy.toMap());
			if(item.getString("psyco_name").equalsIgnoreCase(dto.psycoName))
				Validation.required(null, "Data sudah ada");
		}
		Validation.required(item.getString("psyco_name"), "Tidak boleh kosong");
		
		return super.customInsertValidation(item);
	}
	
//	@Override
//	public Map<String, Object> customOnUpdate(Psychologicals item, Map<String, Object> mapRequest) throws Exception {
//
//		Map<String, Object> result=super.customOnUpdate(item, mapRequest);
//		PsychologicalDTO dto = new PsychologicalDTO();
//
//
//		dto.fromModelMap(result);
//		return dto.toModelMap();
//	}
//	
//	@Override
//	public Map<String, Object> customOnDelete(Psychologicals item, Map<String, Object> mapRequest) throws Exception {
//		Map<String, Object> result=super.customOnDelete(item, mapRequest);
//		PsychologicalDTO dto = new PsychologicalDTO();
//		dto.fromModelMap(result);
//		
//		return dto.toModelMap();
//	}
}