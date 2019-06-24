package app.adapters;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateAdapter {

	public static String convertToMonthName(int month) {
		return DateAdapter.convertToMonthName(month, new Locale("in_ID"));
	}
	
	public static String convertToMonthName(int month, Locale locale) {
//		Calendar c = Calendar.getInstance();
//		c.set(Calendar.MONTH, month);
//
//		return c.getDisplayName(Calendar.MONTH, Calendar.LONG, locale);
		String[] monthNames = {"","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
	    return monthNames[month];
	}
	
	public static Date createDate(int day, int month, int year) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.YEAR, year);
		
		return c.getTime();
	}
	
	public static String now(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		String now = dateFormat.format(date);
		
		return now;
	}


}
