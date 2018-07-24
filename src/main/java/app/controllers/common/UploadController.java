package app.controllers.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import org.javalite.activeweb.FormItem;
import org.javalite.activeweb.annotations.POST;

import core.javalite.controllers.CommonController;

public class UploadController extends CommonController {

	@POST
	public void image(){

//		File files = new File("D:\\assets\\images");
//		if (!files.exists()){
//			if(files.mkdirs()){
//				responseContent().setResponseBody("folder image berhasil dibuat", 200);
//			}
//		}

		Iterator<FormItem> iterator = uploadedFiles();
		if (iterator.hasNext()){
			FormItem item = iterator.next();
			if(item.isFile()){		
				String fileName = item.getFileName();

				//URI uri = new URI("http://localhost:7878/static/test.xls");
				String folderPath = "http://localhost:7878/static/";//(String) appContext().get("staticServerResourcePath");
				String filePath = folderPath 
						+ fileName;

				System.out.println("File Path : " + filePath);
				
				try {
//					item.saveTo(filePath);
					saveToFile(item.getInputStream(), filePath);
					response().setResponseBody(filePath, 200);
				} catch (IOException | URISyntaxException e) {
					e.printStackTrace();
					response().setResponseBody("Error Uploading Image!", 400);
				}
			}   
		} 

		sendResponse();
	}

	private void saveToFile(InputStream inStream, String target)
			throws IOException, URISyntaxException {
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		URI uri = new URI(target);
		out = new FileOutputStream(new File(uri));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
	
	@POST
	public void signature(){
		File files = new File("D:\\assets\\signature");
		if (!files.exists()){
			if(files.mkdirs()){
				response().setResponseBody("folder signature berhasil dibuat", 200);
			}
		}

		Iterator<FormItem> iterator = uploadedFiles();
		if (iterator.hasNext()){
			FormItem item = iterator.next();
			if(item.isFile()){		
				String fileName = item.getFileName();

				String folderPath = (String) appContext().get("staticServerResourcePath");
				String filePath = folderPath 
						+ "/signature/" 
						+ fileName;

				try {
					item.saveTo(filePath);
					response().setResponseBody(filePath, 200);
				} catch (IOException e) {
					e.printStackTrace();
					response().setResponseBody("Error Uploading Image!", 400);
				}
			}   
		} 

		sendResponse();
	}
}
