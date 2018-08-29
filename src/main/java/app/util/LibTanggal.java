package app.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LibTanggal {
		
	@SuppressWarnings("null")
	public static Date[] generateDate(Date start, Date end){
		
		Date result[] = null;
		
		Calendar cStart = Calendar.getInstance(); cStart.setTime(start);
		Calendar cEnd = Calendar.getInstance(); cStart.setTime(end);		
		
		int indx=0;
		while (cStart.before(cEnd)){
			result[indx] = cStart.getTime();
			cStart.add(Calendar.DAY_OF_MONTH, 1);
			indx++;
		}
		
		return result;		
	}
	
	public static Date generateDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");		
		return sdf.parse(date);
	}
	
}
