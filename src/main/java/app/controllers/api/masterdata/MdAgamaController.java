package app.controllers.api.masterdata;

import java.util.List;
import java.util.Map;

import app.models.EmpDataPeg;
import app.models.MdAgama;
import core.javalite.controllers.CRUDController;

public class MdAgamaController extends CRUDController<MdAgama>{
	
	@Override
	public Map<String, Object> customOnInsert(MdAgama item, Map<String, Object> mapRequest) throws Exception {
		Map<String, Object> newItem = super.customOnInsert(item, mapRequest);
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> listPegawai = (List<Map<String, Object>>) mapRequest.get("dataPegawai"); 
		
		for (Map<String, Object> pegawai : listPegawai) {
			EmpDataPeg empDataPeg = EmpDataPeg.class.newInstance().fromMap(pegawai);
			empDataPeg.set("id_agama", newItem.get("id"));
			
			empDataPeg.save();
		}
		
		return newItem;
	}
}
