/**
 * 
 */
package app.controllers.sdm;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.javalite.activeweb.FormItem;
import org.javalite.activeweb.annotations.POST;
import org.javalite.common.Convert;

import app.models.Sdm;
import core.io.enums.HttpResponses;
import core.javalite.controllers.CommonController;

/**
 * @author rifanandrian
 * @date Sep 4, 2018
 * @time 3:40:03 PM
 * email : muhamadrifanandrian@gmail.com
 */
public class UploadController extends CommonController{
	
	@POST
	public void upload() {
		String sdmId = Convert.toString(param("$sdm_id"));
		File file = new File("assets\\");
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Create new folder ...");
			}
		}
		System.out.println("1");
		Iterator<FormItem> iterator = uploadedFiles();
		if (iterator.hasNext()) {
			FormItem item = iterator.next();
			System.out.println("2");
			if (item.isFile()) {
				String fileName = item.getFileName();
				String folderPath = "assets\\";
				String filePath = folderPath + fileName;
				File fileFoto = new File(filePath);
				if(fileFoto.exists()) {
					fileFoto.delete();
				}
				System.out.println("3");
				try {
					System.out.println("4");
					Sdm photo = Sdm.findFirst("sdm_id = ?", sdmId);
					photo.set("sdm_image", fileName);
					photo.save();
					System.out.println("5");
					item.saveTo(filePath);
					response().setResponseBody(HttpResponses.ON_SUCCESS);
				} catch (IOException e) {
					System.out.println("6");
					e.printStackTrace(); 
					response().setResponseBody(e); 
				}
			}
			System.out.println("7");
		}
		sendResponse();
		System.out.println("sukses");
	}
}
