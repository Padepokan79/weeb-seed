package app.controllers.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import core.javalite.controllers.CommonController;

public class DownloadController extends CommonController{
	
	public void downloadExcel() throws FileNotFoundException, URISyntaxException, MalformedURLException{
		URI uri = new URI("http://localhost:7878/static/test.xls");
		File file = new File(uri);
		
		if (file.canRead()){
			sendFile(file).contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet").status(200);
		}
	}
}
