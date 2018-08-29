package app.controllers.example.crud;

import java.sql.Date;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

import com.google.common.base.Strings;
import com.ibm.icu.util.Calendar;

import app.models.core.master.MasterUser;
import app.models.core.master.MasterUserActivity;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

public class CustomAllInOneController extends CRUDController<MasterUser> {

	@Override
	public CorePage customOnReadAll(PagingParams params) throws Exception {		
		String username = param("username");		
		Integer isActive = Convert.toInteger(param("is_active"));
		
		if (!Strings.isNullOrEmpty(username) && isActive != null) {
			params.setFilter("username like ? and is_active = ", "%"+ username +"%", isActive);
		}
			
		params.setOrderBy("username");
		
		LazyList<? extends Model> items = this.getItems(params);
		Long totalItems = this.getTotalItems(params);
		
		return new CorePage(items.toMaps(), totalItems);				
	}
	
	@Override
	public MasterUser customInsertValidation(MasterUser item) throws Exception {
		String username = item.getString("username");
		Integer isActive = item.getInteger("is_active");
		
		// Contoh Validasi untuk variable yang harus memiliki nilai / tidak boleh null
		Validation.required(username, "Nilai username tidak boleh kosong!");

		// Contoh Validasi untuk variable yang harus bernilai 0 / 1
//		Validation.booleanOnly(isActive, "Nilai is_active hanya bernilai 0/1!");
		
		return super.customInsertValidation(item);
	}
	
	@Override
	public Map<String, Object> customOnInsert(MasterUser item, Map<String, Object> mapRequest) throws Exception {
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
		
		Map<String, Object> result = super.customOnInsert(item, mapRequest);
		result.put("additional_field", "Menambahkan field baru untuk kebutuhan FE jika diperlukan");
		
		// Melakukan insert ke table user_activities setelah user melakukan insert
		MasterUserActivity userActivity2 = new MasterUserActivity();
		userActivity2
					.set("activity_time", now)
					.set("activity_name", "Successfully insert new user.")
					.set("activity_state", "After Insert")
					.set("username", loggedInUsername)
					.saveIt();		
		
		return result;
	}	
	
	@Override
	public Map<String, Object> customOnUpdate(MasterUser item, Map<String, Object> mapRequest) throws Exception {
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
		result.put("additional_field", "Menambahkan field baru untuk kebutuhan FE jika diperlukan");

		MasterUserActivity userActivity2 = new MasterUserActivity();
		userActivity2.set("activity_time", now)
					.set("activity_name", "Successfully update old user")
					.set("activity_state", "After Update")
					.set("username", loggedInUsername)
					.saveIt();
				
		return result;
	}
	
	@Override
	public Map<String, Object> customOnDelete(MasterUser item, Map<String, Object> mapRequest) throws Exception {
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
