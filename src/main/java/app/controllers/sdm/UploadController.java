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
		String folderPath = "assets\\";
		
		File file = new File(folderPath);
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
				String filePath = folderPath + fileName;
				System.out.println("3");
				try {
					Sdm.delete("sdm_id = ?", sdmId);
					System.out.println("4");
//					Sdm photo = new Sdm();
//					photo.set("sdm_id", sdmId);
//					photo.set("sdm_name");
//					photo.set("sdm_placebirth");
//					photo.set("sdm_datebirth");
//					photo.set("sdm_startcontract");
//					photo.set("sdm_endcontract");
//					photo.set("sdm_status");
//					photo.set("sdm_image", fileName);
//					photo.insert();
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
