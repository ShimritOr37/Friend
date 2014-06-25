package com.shimrit.pickforu;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DateChoser extends Activity {

	private TextView tvDisplayDate;
	private DatePicker dpResult;
	private Button btnChangeDate;

	private int year;
	private int month;
	private int day;

	static final int DATE_DIALOG_ID = 999;

	RadioGroup group;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("bugbug","Date enter");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datepick);
		RadioGroup group= (RadioGroup)findViewById(R.id.group);
		
		if (Singleton.gender==null||!Singleton.gender.contains("male")){
			group.setVisibility(View.VISIBLE);
			 TextView lblgender= (TextView)findViewById(R.id.lblgender);
			 lblgender.setVisibility(View.VISIBLE);
			if (!Singleton.bd.equals("start")){
				btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
				btnChangeDate.setVisibility(View.INVISIBLE);//button invis
				TextView lblDate = (TextView)findViewById(R.id.lblDate);
				TextView tvDate = (TextView)findViewById(R.id.lblDate);
				lblDate.setVisibility(View.INVISIBLE);//text invis
				tvDate.setVisibility(View.INVISIBLE);
			}
		}
		
		
		
		if (Singleton.bd.equals("start")){
			
			setCurrentDateOnView();
			addListenerOnButton();
		}
		
		
		
		
	}

	// display current date
	public void setCurrentDateOnView() {

		tvDisplayDate = (TextView) findViewById(R.id.tvDate);
		dpResult = (DatePicker) findViewById(R.id.dpResult);
		//dpResult.setVisibility(View.VISIBLE);
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		tvDisplayDate.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(month + 1).append("-").append(day).append("-")
				.append(year).append(" "));

		// set current date into datepicker
		dpResult.init(year, month, day, null);

	}

	public void addListenerOnButton() {

		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);

		btnChangeDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialog(DATE_DIALOG_ID);

			}

		});

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			tvDisplayDate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

			// set selected date into datepicker also
			dpResult.init(year, month, day, null);
			Singleton.bd=String.valueOf(day).concat("/").concat(String.valueOf(month));
			Singleton.month=month;
			Singleton.day=day;

		}
	};

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.female:{
			Singleton.gender="female";
			Log.d("bugbug","you chose women in gender");	
		}
			break;
		case R.id.male:
			Singleton.gender="male";
			
			
			break;
		case R.id.setInfo:{
			if (!Singleton.bd.equals("start")){
				finish();
			}else{
				  final Toast toast2= Toast.makeText(DateChoser.this,"Please set Birthday date for "+ Singleton.Name.toString(), Toast.LENGTH_SHORT);
		 		   toast2.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
		 		   toast2.show();
			}
			if (Singleton.gender==null){
				 final Toast toast2= Toast.makeText(DateChoser.this,"Please set Gender for "+ Singleton.Name.toString(), Toast.LENGTH_SHORT);
		 		   toast2.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
		 		   toast2.show();
			}
			
		}
			break;

			
		default:
			break;
		}
	
	}
}