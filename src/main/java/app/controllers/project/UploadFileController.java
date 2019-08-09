package app.controllers.project;

import core.javalite.controllers.CommonController;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javalite.activeweb.FormItem;
import org.javalite.activeweb.annotations.POST;

public class UploadFileController extends CommonController {

	@POST
	public void upload(){ 
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<>();
		File file = new File("assets\\");
		if (!file.exists()){
			if (file.mkdir()){
			}
			else{
			}
		}

		Iterator<FormItem> iterator = uploadedFiles(); 
		if (iterator.hasNext()){
			FormItem item = iterator.next();
			if(item.isFile()){
				String fileName = item.getFileName();
				String folderPath = "assets\\";
				String filePath = folderPath + fileName; 
				try { 
					item.saveTo(filePath);
					map.put("filepath", filePath);
					result.add(map);
					response().setData(result);
				} catch (IOException e) {
					e.printStackTrace(); 
					response().setResponseBody("ERROR_UPLOAD", 400); 
				}
			}   
		}  
		sendResponse();
	}

}
