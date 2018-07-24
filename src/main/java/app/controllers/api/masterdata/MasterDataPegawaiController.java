package app.controllers.api.masterdata;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;

import com.google.common.base.Strings;
import com.ibm.icu.util.Calendar;

import app.controllers.example.crud.CustomAllInOneDTOController.CustomAllInOneDTO;
import app.models.DataPegawai;
import app.models.MasterDataPegawai;
import app.models.Pospeg;
import app.models.core.master.MasterUserActivity;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.DTOModel;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class MasterDataPegawaiController
extends CRUDController<MasterDataPegawai> {

	public class MasterDataDTP extends DTOModel {
		public String nik;
		public String nama;
		public int agama;
		public int posisi;
		public String alamat;
	}
	
	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		List<Map<String, Object>> mapItems = new ArrayList<Map<String, Object>>();
		String nama = param("nama");		
//		Integer isActive = Convert.toInteger(param("is_active"));
		
//		if (!Strings.isNullOrEmpty(nama)) {
//			params.setFilter("nama like ? ", "%"+ nama);
//		}
		
		params.setOrderBy("nama");
		
		LazyList<? extends Model> items = this.getItems(params);
		Long totalItems = this.getTotalItems(params);
		
		for (Map<String, Object> mapItem : items.toMaps()) {
			MasterDataDTP dto = new MasterDataDTP();
			dto.fromMap(mapItem);
//			dto.additionalField = "Menambahkan field baru untuk kebutuhan FE jika diperlukan";

			mapItems.add(dto.toModelMap());
		}
		
		return new CorePage(items.toMaps(), totalItems);				
	}
	
	@Override
	public MasterDataPegawai customInsertValidation(MasterDataPegawai item) throws Exception {
		String nama = item.getString("nama");
		Integer isActive = item.getInteger("is_active");
		
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(nama, "Nilai Nama tidak boleh kosong!");

		// Contoh Validasi untuk variable yang harus bernilai 0 / 1
//		Validation.booleanOnly(isActive, "Nilai is_active hanya bernilai 0/1!");
		
		return super.customInsertValidation(item);
	}
	
	@Override
	public Map<String, Object> customOnInsert(MasterDataPegawai item, Map<String, Object> mapRequest) throws Exception {
		// Cara mendapatkan username dari request yang mengirimkan body
		String loggedInUsername = (String) mapRequest.get("loggedInUsername");
		Long currentTime = Calendar.getInstance().getTimeInMillis();
		Date now = new Date(currentTime);
		
		// Melakukan insert ke table user_activities sebelum user melakukan insert
		MasterUserActivity userActivity1 = new MasterUserActivity();
		userActivity1
					.set("activity_time", now)
					.set("activity_name", "Preparing insert new user.")
					.set("activity_state", "Before Insert")
					.set("username", loggedInUsername)
					.saveIt();		
		
//		Mstpegawai peg = new Mstpegawai();
//		peg.fromMap(mapRequest);
//		peg.set("tmtberlaku", mapRequest.get("tmtgaji"));
		DataPegawai peg = new DataPegawai();
		peg.fromMap(mapRequest);
		peg.set("nik", mapRequest.get("nik"));
		peg.set("nama", mapRequest.get("nama"));
		peg.set("agama", mapRequest.get("agama"));
		peg.set("posisi", mapRequest.get("posisi"));
		peg.insert();
		
		Map<String, Object> result = super.customOnInsert(item, mapRequest);
		MasterDataDTP dto = new MasterDataDTP();
		dto.fromMap(result);
		
//		result.put("additional_field", "Menambahkan field baru untuk kebutuhan FE jika diperlukan");
		
		// Melakukan insert ke table user_activities setelah user melakukan insert
		MasterUserActivity userActivity2 = new MasterUserActivity();
		userActivity2
					.set("activity_time", now)
					.set("activity_name", "Successfully insert new user.")
					.set("activity_state", "After Insert")
					.set("username", loggedInUsername)
					.saveIt();		
		
		return  dto.toMap();
	}	
	
	@Override
	public Map<String, Object> customOnUpdate(MasterDataPegawai item, Map<String, Object> mapRequest) throws Exception {
		// Cara mendapatkan username dari request yang mengirimkan body
		String loggedInUsername = (String) mapRequest.get("loggedInUsername");
		Long currentTime = Calendar.getInstance().getTimeInMillis();
		Date now = new Date(currentTime);
		
		// Melakukan insert ke table user_activities setelah user melakukan insert
		MasterUserActivity userActivity1 = new MasterUserActivity();
		userActivity1.set("activity_time", now)
					.set("activity_name", "Preparing update old user.")
					.set("activity_state", "Before Update")
					.set("username", loggedInUsername)
					.saveIt();		
		
		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
//		result.put("additional_field", "Menambahkan field baru untuk kebutuhan FE jika diperlukan");

		MasterUserActivity userActivity2 = new MasterUserActivity();
		userActivity2.set("activity_time", now)
					.set("activity_name", "Successfully update old user")
					.set("activity_state", "After Update")
					.set("username", loggedInUsername)
					.saveIt();
				
		return result;
	}
	
	@Override
	public Map<String, Object> customOnDelete(MasterDataPegawai item, Map<String, Object> mapRequest) throws Exception {
		// Cara mendapatkan username dari request yang mengirimkan body
		String loggedInUsername = (String) mapRequest.get("loggedInUsername");
		Long currentTime = Calendar.getInstance().getTimeInMillis();
		Date now = new Date(currentTime);
		
		// Melakukan insert ke table user_activities setelah user melakukan insert
		MasterUserActivity userActivity1 = new MasterUserActivity();
		userActivity1.set("activity_time", now)
					.set("activity_name", "Preparing delete registered user.")
					.set("activity_state", "Before Delete")
					.set("username", loggedInUsername)
					.saveIt();		
		
		Map<String, Object> result = super.customOnDelete(item, mapRequest);		
		result.put("additional_field", "Menambahkan field baru untuk kebutuhan FE jika diperlukan");
		
		MasterUserActivity userActivity2 = new MasterUserActivity();
		userActivity2.set("activity_time", now)
					.set("activity_name", "Successfully delete registered user")
					.set("activity_state", "After Delete")
					.set("username", loggedInUsername)
					.saveIt();
		
		return result;
	}	
}
