package app.controllers.auth;

import java.util.Map;

import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Util;

import com.Ostermiller.util.MD5;

import app.models.core.master.MasterUser;
import app.util.Lib;
import core.io.enums.HttpResponses;
import core.io.helper.JsonHelper;
import core.io.helper.TokenHelper;
import core.javalite.controllers.CommonController;

public class TokenController extends CommonController {
	Lib libs = new Lib();

	@SuppressWarnings("unchecked")
	@POST
	public void index() {
		Map<String, Object> result = null;
		
		try {
			String requestBody = Util.read(getRequestInputStream());
			
			@SuppressWarnings("rawtypes")
			Map mapRequestBody = JsonHelper.toMap(requestBody);

			System.out.println("Data User : " + JsonHelper.toJson(mapRequestBody));

			String username = mapRequestBody.get("username").toString().toUpperCase();
			
			MasterUser dataUser = MasterUser.findFirst("username = ?", username);
						
			if (dataUser != null) {
				String password = MD5.getHashString(mapRequestBody.get("password").toString());
				
				System.out.println("Password DB : " + dataUser.getString("password"));
				System.out.println("Password Input : " + password);
				
				if (dataUser.getString("password").equals(password)) {
					result = TokenHelper.createTokenObj(username);
					result.putAll(dataUser.toMap());
				} else {
					response().setResponseBody("WRONG_PASSWORD", 400);					
				}
			} else {
				response().setResponseBody("NO_USER_FOUND", 400, mapRequestBody);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response().setResponseBody(HttpResponses.FAILED);
		}

		if(result != null) {
			respond(JsonHelper.toJson(result));												
		} else {
			sendResponse();			
		}
	}

	/*
	 * @POST public void index(){ try { String requestBody =
	 * Util.read(getRequestInputStream());
	 * 
	 * @SuppressWarnings("rawtypes") Map mapRequestBody =
	 * JsonHelper.toMap(requestBody);
	 * 
	 * String username =
	 * mapRequestBody.get("nama_login").toString().toUpperCase(); String
	 * password = MD5.getHashString(mapRequestBody.get("password").toString());
	 * 
	 * User user = User.findFirst("nama_login = ?", username); if (user !=
	 * null){ if (!user.getString("password").equals(password)){ throw new
	 * Exception(); } else { Map<String, Object> t =
	 * TokenHelper.createTokenObj(username); t = libs.puToResult(user,t,
	 * atributAdmUser());
	 * 
	 * respond(JsonHelper.toJson(t)); } } else { throw new Exception(); } }
	 * catch (Exception e){ logError(e); respond(HttpMessage.FAILED); } }
	 */

	@SuppressWarnings("unused")
	private String[] atributAdmUser() {
		String sDataPribadi[] = { "id", "id_peg", "banned", "id_user_group", "ip_address", "is_login", "nama_login",
				"password", "session_id", "status", "tgl_lastlogout", "tgl_login" };
		return sDataPribadi;
	}

	@POST
	public void register() {
		try {
			String request = Util.read(getRequestInputStream());

			@SuppressWarnings("rawtypes")
			Map mapRequest = JsonHelper.toMap(request);

			MasterUser user = new MasterUser();
			user.fromMap(mapRequest);
			user.validate();

			if (user.hasErrors()) {
				throw new Exception();
			}

			if (user.insert()) {
				respond(JsonHelper.toJson(user.toMap())).status(200);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			respond("{ \"message\" : \"Create\" }").status(400);
		}
	}
}
