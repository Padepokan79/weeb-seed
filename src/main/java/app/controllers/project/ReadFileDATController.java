package app.controllers.project;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activeweb.annotations.GET;
import org.javalite.common.Convert;

import core.io.enums.HttpResponses;
import core.javalite.controllers.CommonController;

import java.io.*;
import java.nio.file.Files; 

public class ReadFileDATController extends CommonController
{ 
	//private static BufferedReader br;

	@GET
  public void reads() throws IOException 
  { 
  // We need to provide file path as the parameter: 
  // double backquote is to avoid compiler interpret words 
  // like \test as \t (ie. as a escape sequence) 
	
	try {
		//String st; 
			List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
			  
			 // File file = new File("F:\\fega\\attlog.dat");   
			  //br = new BufferedReader(new FileReader(file)); 
			  String filename = Convert.toString(param("$filename"));
			  String[] lines = Files.readAllLines(new File("assets\\" + filename).toPath()).toArray(new String[0]);
			  
			  for(String line: lines) {
				  Map<String, Object> map = new HashMap<>();
				    //@SuppressWarnings("unused")
					//int courseId = Integer.parseInt(line.substring(0,15));
				    //@SuppressWarnings("unused")
				    
					//String lines1 = line.substring(0, 24).trim();

					String nip = line.split("\t")[0];
					String nama = line.split("\t")[1];
					
					String tgl = line.split("\t")[2];
					String tgl1 = tgl.split(" ")[0];
					String tgl2 = tgl.split(" ")[1];
					String kd1 = line.split("\t")[3];
					String kd2 = line.split("\t")[4];
				    // etc...
				    map.put("nip", nip);
				    map.put("nama", nama);
				    //map.put("tgl", tgl);
				    map.put("tgl", tgl1);
				    map.put("waktu", tgl2);
				    map.put("stts_checkin", kd1+" "+kd2);
				    //map.put("stts_checkin2", kd2);
				    listData.add(map); 
				} 
			  /*while ((st = br.readLine()) != null) 
			    System.out.println(st);
			   map.put("dat", lines);
				result.add(map);*/
			 Map<String, Object> result = new HashMap<String, Object>();
			 result.put("items", listData);
			 result.put("totalItems", listData.size());
			response().setData(result);
			response().setResponseBody(HttpResponses.ON_SUCCESS, result );
			
	} catch (Exception e) {
		e.printStackTrace();
		response().setResponseBody(HttpResponses.FAILED);
	}
	sendResponse();
  } 
	
} 