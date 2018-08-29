package app.controllers.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.javalite.activeweb.AppController;
import org.javalite.activeweb.annotations.GET;

import core.io.helper.JsonHelper;

public class ProcessConverterFirstController extends AppController {

	Long backValue;
	
	public Long getBackValue() {
		return backValue;
	}
	
	public void setBackValue(String backValue) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		this.backValue = sdf.parse(backValue).getTime();
	}
	
	@GET
	public void index(){
		
		try {						
			setBackValue(param("DATE"));
			respond(JsonHelper.toJson("Long : " + getBackValue()));
						
		} catch (Exception e) {
			e.getStackTrace();
			respond(JsonHelper.toJson(""));
		}
		
	}
	
}
