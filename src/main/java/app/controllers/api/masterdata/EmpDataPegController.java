package app.controllers.api.masterdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Model;

import com.google.common.base.Strings;

import app.models.EmpDataPeg;
import app.models.MdAgama;
import core.io.model.CorePage;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class EmpDataPegController extends CRUDController<EmpDataPeg>{

	@SuppressWarnings("unchecked")
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {
		if (Strings.isNullOrEmpty(param("id_agama"))) {
			throw new Exception("ID Agama Harus Ada");
		}
		
		String idAgama = param("id_agama");
		String nrik = Strings.isNullOrEmpty(param("nrik")) ? param("nrik") : "";
		String orderBy = "nrik";
		
		// Untuk Query nya, data yang diselect hanya boleh data 
		// untuk data yang bersangkutan dengan data table utama		
		String query = "SELECT edp.*"
					+ " FROM emp_data_peg edp, md_agama ma"
					+ " WHERE edp.id_agama = ma.id"
					+ " AND ma.id = " + idAgama
					+ " OR edp.nrik like '%" + nrik + "'";
				
		params.setFilter(query);
		params.setOrderBy(orderBy);
		
		params.include(MdAgama.class);
		
		List<? extends Model> lazyItems = super.getItems(params);
		Long totalItems = super.getTotalItems(params);

		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		
		// Cara untuk mendapatkan data dari anak anak nya 
		for (Model lazyItem : lazyItems) {
			MdAgama agama = lazyItem.parent(MdAgama.class);
			Map<String, Object> item = lazyItem.toMap();
			
			item.put("nama_agama", agama.get("agama"));
			items.add(item);
		}
		
		return new CorePage(
				items, 
				totalItems);
	}
}
