package org.inspira.jcapiz;

import java.util.Calendar;

public class Misc {

	public static String dateFormat(){
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);
		int seconds = c.get(Calendar.SECOND);
		return (dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth))
				+
				"/"
				+
				(month < 10 ? "0" + month : String.valueOf(month))
				+
				"/"
				+
				year
				+
				" "
				+
				(hourOfDay < 10 ? "0" + hourOfDay : String.valueOf(hourOfDay))
				+
				":"
				+
				(minutes < 10 ? "0" + minutes : String.valueOf(minutes))
				+
				":"
				+
				(seconds < 10 ? "0" + seconds : String.valueOf(seconds));
	}
}
