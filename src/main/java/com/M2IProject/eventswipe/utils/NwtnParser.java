package com.M2IProject.eventswipe.utils;

import java.util.Calendar;

public class NwtnParser {

	public static Calendar rawStringDateToCalendar(String rawStringDate) {
		// "2022-03-24T18:30:00Z" to real Calendar
		Calendar calendar = Calendar.getInstance();
		int[] ints = new int[6];
		String[] strs = new String[6];

		strs = rawStringDate.split("[-T:Z]+");
		for (int i = 0; i < 6; i++)
			ints[i] = Integer.parseInt(strs[i]);

		calendar.set(Calendar.YEAR, ints[0]);
		calendar.set(Calendar.MONTH, ints[1]);
		calendar.set(Calendar.DATE, ints[2]);
		calendar.set(Calendar.HOUR_OF_DAY, ints[3]);
		calendar.set(Calendar.MINUTE, ints[4]);
		calendar.set(Calendar.SECOND, ints[5]);

		return calendar;
	}

}
