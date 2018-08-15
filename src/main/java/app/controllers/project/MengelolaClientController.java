/*
 * Created By  : Khairil
 * Date Assign : 24-07-2018 13:24
 * Role : Controller Clients
 */
package app.controllers.project;

import app.models.Clients;
import app.models.Course;
import app.models.Education;
import app.models.Employment;
import app.models.Profiling;
import app.models.Sdm;
import app.models.SdmLanguage;
import core.io.helper.Validation;
import core.io.model.CorePage;
import core.io.model.PagingParams;
import core.javalite.controllers.CRUDController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.miscellaneous.TrimFilter;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.common.Convert;

public class MengelolaClientController extends CRUDController<Clients> {
    
    @Override
    public CorePage customOnReadAll(PagingParams params) throws Exception {
        LazyList<? extends Model> items = this.getItems(params);
        Long totalItems = this.getTotalItems(params);
        return new CorePage(items.toMaps(), totalItems);
    }
    
    @Override
	public Clients customInsertValidation(Clients item) throws Exception {
    	List<Map<String, Object>> listMapClient = new ArrayList<Map<String, Object>>();
    	LazyList<Clients> listClient = Clients.findAll();
    	
    	String clientName = item.getString("CLIENT_NAME");
		String clientAddress = item.getString("CLIENT_ADDRESS");
		String clientPicclient = item.getString("CLIENT_PICCLIENT");
		String clientMobileclient = item.getString("CLIENT_MOBILECLIENT");
		
//		for(Clients cln : listClient) {
//			if(cln.getString("CLIENT_NAME").equalsIgnoreCase(clientName)) {
//				Validation.required(null, "Client exists!");
//			}
//		}
		
		Validation.required(clientName, "Client must filled");
//		Validation.required(clientAddress, "Address must filled");
//		Validation.required(clientPicclient, "PIC Client must filled");
//		Validation.required(clientMobileclient, "Mobile Client must filled");
		
		return super.customInsertValidation(item);
	}
	
    //Modified by : Hendra Kurniawan
    //Date        : 15/08/2018
    public Map<String, Object> customOnInsert(Clients item, Map<String, Object> mapRequest) throws Exception{		
		Map<String, Object> result = super.customOnInsert(item, mapRequest);
		Clients sdm = Clients.findFirst("Order by client_id desc");
		
		String nama_client = Convert.toString(mapRequest.get("client_name"));
		String client_address = Convert.toString(mapRequest.get("client_address"));
		String client_pic = Convert.toString(mapRequest.get("client_picclient"));
		String client_mobile = Convert.toString(mapRequest.get("client_mobileclient"));
		
		if(nama_client == "")
		{  
			nama_client = "-";
		}
		if(client_address == "")
		{  
			client_address = "-";
		}
		if(client_pic == "")
		{  
			client_pic = "-";
		}
		if(client_mobile == "")
		{  
			client_mobile = "-";
		}
		
		item.set("client_name", nama_client);
		item.set("client_address", client_address);
		item.set("client_picclient", client_pic);
		item.set("client_mobileclient", client_mobile);
		item.save();
		return result;
	}
//	@Override
//	public Map<String, Object> customOnInsert(Clients item, Map<String, Object> mapRequest) throws Exception {
//		Map<String, Object> result = super.customOnInsert(item, mapRequest);
//		return result;
//	}
//	
//	@Override
//	public Map<String, Object> customOnUpdate(Clients item, Map<String, Object> mapRequest) throws Exception {
//		Map<String, Object> result = super.customOnUpdate(item, mapRequest);
//		return result;
//	}
//	
//	public Map<String, Object> customOnDelete(Clients item, Map<String, Object> mapRequest) throws Exception {
//		Map<String, Object> result = super.customOnDelete(item, mapRequest);
//		return result;
//	}
}