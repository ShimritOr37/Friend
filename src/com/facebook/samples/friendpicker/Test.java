package com.facebook.samples.friendpicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;

public class Test {
	//private static String TAG = Test.class.getSimpleName();
	public static void main(String [] args){
    	SimpleDateFormat inputDF= new SimpleDateFormat("MM/dd/yyyy");
    	Date date1=null;
		try {
			date1 = inputDF.parse("9/30/2011");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Calendar cal = Calendar.getInstance();
		if (date1!=null)
		{
		cal.setTime(date1);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		
	System.out.print("month"+month+ "day"+day);
		}
		else{
			System.out.print("month");
			//Log.d("bugbug","isnull!");
		}

	}

	
}
