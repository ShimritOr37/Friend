package com.shimrit.pickforu;

import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.security.auth.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.shimrit.pickforu.R;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;




import android.os.Bundle;


@SuppressLint("CutPasteId")
public class MatchFriend extends Activity {
	
	private static final int NO_INFO = 0;
	private static final int BIRTHDAY = 1;
	private ConditionVariable mCondition;
	boolean Married=false;
	String op;
	String bd="start";
	String gender;
	Button back;
	String TAG= MatchFriend.class.getSimpleName();
	GraphObject[] array= new GraphObject[40];
	static int index;
	 ArrayList<GraphObject> temp, Aries,	Taurus	,Gemini	,Cancer	,Leo	,Virgo	,Libra,	Scorpio,	Sagittarius,	Capricorn,	Aquarius,	Pisces = new ArrayList<GraphObject>();
	 RadioButton female,male;
	 Map<String, List <GraphObject>> output = new HashMap<String, List <GraphObject>>(); 
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.matchafriend);
	   
	    
	  
	    output.put("Aries",Aries= new ArrayList<GraphObject>()); 
	    output.put("Taurus",Taurus=new ArrayList<GraphObject>()); 
	    output.put("Gemini",Gemini=new ArrayList<GraphObject>());
	    output.put("Cancer",Cancer=new ArrayList<GraphObject>());
	    output.put("Virgo",Virgo=new ArrayList<GraphObject>());
	    output.put("Cancer",Cancer=new ArrayList<GraphObject>());
	    output.put("Libra",Libra=new ArrayList<GraphObject>());
	    output.put("Scorpio",Scorpio=new ArrayList<GraphObject>());
	    output.put("Sagittarius",Sagittarius=new ArrayList<GraphObject>());
	    output.put("Capricorn",Capricorn=new ArrayList<GraphObject>());
	    output.put("Aquarius",Aquarius=new ArrayList<GraphObject>());
	    output.put("Pisces", Pisces=new ArrayList<GraphObject>());

	    female=(RadioButton)findViewById(R.id.female);
	    male=(RadioButton)findViewById(R.id.male);
	    TextView Gender = (TextView)findViewById(R.id.gender);
	    Log.d(TAG,"ID IS "+Singleton.Id+ " name:"+Singleton.Name+ "bd "+Singleton.bd);
	    
	    
	    
	    if (Singleton.friendInfoList != null ){
	    
	    	index=0;    	
	    	Log.d(TAG,"running for "+Singleton.Id+" "+Singleton.Name);
	    	bd="start";
	    	for (GraphObject friendInfo: Singleton.friendInfoList){
	    	if (friendInfo.getProperty("relationship_status").toString().substring(0,2).equals("Ma")&&friendInfo.getProperty("uid").equals(Singleton.Id)){
	    		Married=true;
	    	}
        		if (((friendInfo.getProperty("relationship_status"))!=null&&!friendInfo.getProperty("relationship_status").toString().substring(0,2).equals("Ma"))||(friendInfo.getProperty("relationship_status")==null)){//NOT MARRIED
        			Log.d(TAG,"this is b"+friendInfo.getProperty("birthday_date").toString()+" "+friendInfo.getProperty("name") +"b"+friendInfo.getProperty("birthday").toString());
        			if (friendInfo.getProperty("birthday_date")!=null && friendInfo.getProperty("birthday_date").toString().length()>4){
        					//has birthday
        					String date=friendInfo.getProperty("birthday_date").toString().substring(0, 5);	
        			        Date date1 = null;
							try {
								date1 = new SimpleDateFormat("MM/dd",Locale.ENGLISH).parse(date);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Calendar cal = Calendar.getInstance();
							cal.setTime(date1);
							int month = cal.get(Calendar.MONTH)+1;
							int day = cal.get(Calendar.DAY_OF_MONTH);
							int year = cal.get(Calendar.YEAR);
							
							if (date1!=null){
								String signs=ZodiacSign(date1, "ff");
								Log.d(TAG,"DATE "+date+friendInfo.getProperty("name")+"sign is "+signs+" day "+day+" month "+month);
							
								if (output.containsKey(signs)){//find sign and put it into the right list
									output.get(signs).add(friendInfo);
									Log.d(TAG,"uid"+friendInfo.getProperty("uid").toString());
									Log.d(TAG,"uid"+Singleton.Id.toString());
									if (friendInfo.getProperty("uid").toString().equals(Singleton.Id.toString())){
										bd=friendInfo.getProperty("birthday_date").toString();
										gender=friendInfo.getProperty("sex").toString();							
										Log.d(TAG,"YES");
									}
								}//end adding list
							}//end check null
        				}//birthday
        			
        			}//end for relationship
	    	        	
	    		}//end for the whole list. exit if bd
	    	
	    
	    	    }//end of checking list
	    Singleton.bd=bd;
      	Singleton.gender=gender;
      
		Intent intentC = new Intent(this, DateChoser.class);	
	    if (Singleton.bd.equals("start")||(!Singleton.gender.contains("ale"))){
	    	
	    		startActivityForResult(intentC, NO_INFO);
	    }else{
	    	if (!Married){
	    		set();
	    	}else
	    	{
	    		finish();
	    	}
	        	//startActivityForResult(this.getIntent(),BIRTHDAY);
	    	 }
	    
	}//endCreate
	public void set(){
	 ProfilePictureView profilePictureM = (ProfilePictureView)findViewById(R.id.profilePicture);
   	 ProfilePictureView profilePicture = (ProfilePictureView)findViewById(R.id.profilePicture1);
   	 ProfilePictureView profilePicture2 = (ProfilePictureView)findViewById(R.id.profilePicture2);
   	 ProfilePictureView profilePicture3 = (ProfilePictureView)findViewById(R.id.profilePicture3);
   	 Button back= (Button)findViewById(R.id.back);
   	
   	 Log.d(TAG, "bd was start and now "+Singleton.bd);
   	 
   	 
        if (!Singleton.bd.equals("start")){//candidate have a birthday matching by date
 
   		Calendar myCal = Calendar.getInstance();
   			if (Singleton.month==0){//5 string birthday
   				String m= Singleton.bd.substring(0, 5);
			   	int monthl=Integer.parseInt(m.substring(0,2));
			   	myCal.set( 2000,mod(monthl+6,12),Integer.parseInt(m.substring(3,5)));
			   	Log.d(TAG,"OP DATE"+mod(monthl+6,12));
   			}else{
   				myCal.set( 2000,mod(Singleton.month+6,12),Singleton.day);
   			}
   				
   	//mycall is the required date to the person candidate 
   	
   	String sign=ZodiacSign(myCal.getTime(), "element");
   	//get the wanted sign for candidate
   	
   	int j=1;
   	//index for array of candidates
   	
   		for (int i = 0; j <= 3 ;i++) {//i index of list	
   			
   			if(i< Singleton.friendInfoList.size()){
   			
   			GraphObject o = output.get(sign).get(i);	 
   			Object sex = o.getProperty("sex");
   			op=sex.toString();//op is the candidate gender
   			Log.d(TAG,"OP"+op);
   			
   			if (op.contains("ale")){//if  female or male
   		
				if (!op.equals(Singleton.gender)){//check if the oppose gender
					Log.d(TAG, "MATCH"+Singleton.gender);
	        	array[j]=output.get(sign).get(i);
	        	j++;   	
	        	Log.d(TAG,"index of list"+i+" index of array"+j);
   			}
				
   			}
   			
   			}//if not end list
   		}//for
   	 Log.d(TAG,"USER BIRTHDAY IS "+bd);
   	// output.
   	 profilePictureM.setProfileId(Singleton.Id);
   	 if (array[1]!=null){
		     profilePicture.setProfileId(array[1].getProperty("uid").toString());
		     profilePicture.setOnClickListener(new MyClickListener());
		     profilePictureM.setOnClickListener(new MyClickListener());
		     
		     
   	 }
   	 if (array[2]!=null){
		     profilePicture2.setProfileId(array[2].getProperty("uid").toString());
   	   	 profilePicture2.setOnClickListener(new MyClickListener());
   	 }
   	 if (array[3]!=null){
		     profilePicture3.setProfileId(array[3].getProperty("uid").toString());
		     profilePicture3.setOnClickListener(new MyClickListener());
   	 }
   	 
   	 }
	}//end set
        public void message(){//birtday not found
	 		   final Toast toast2= Toast.makeText(MatchFriend.this, Singleton.Name.toString()+" Didn't declare his birthday or Married, Please chose a different friend", Toast.LENGTH_LONG);
	 		   toast2.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
	 		new CountDownTimer(6000, 1000)
	 		{

	 		    public void onTick(long millisUntilFinished) {toast2.show();}
	 		    public void onFinish() {toast2.show();}

	 		}.start();
	 		   toast2.setDuration(5200);
				toast2.show();

   	}//message     

	private void changView() {
		// TODO Auto-generated method stub
		Intent intentC = new Intent(this, DateChoser.class);
		
		startActivity(intentC);
		
	}
		
	public void onResume(){
		super.onResume();
		
	}	
	

	
	private int mod(int x, int y)
	{
	    int result = x % y;
	    if (result < 0)
	        result += y;
	    return result;
	}
	
	public String ZodiacSign(Date date, String element)
    {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH)+1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int year = cal.get(Calendar.YEAR);
		
		
        switch (month)
        {
        
            case 1: if(day <= 20)
                { element = "Earth"; return "Capricorn"; }
                else
                { element = "Air"; return "Aquarius"; }
            case 2: if (day <= 19)
                { element = "Air"; return "Aquarius"; }
                else
                { element = "Water"; return "Pisces"; }
            case 3: if (day <= 20)
                { element = "Water"; return "Pisces"; }
                else
                { element = "Fire"; return "Aries"; }
            case 4: if (day <= 20)
                { element = "Fire"; return "Aries"; }
                else
                { element = "Earth"; return "Taurus"; }
            case 5: if (day <= 21)
                { element = "Earth"; return "Taurus"; }
                else
                { element = "Air"; return "Gemini"; }
            case 6: if (day <= 22)
                { element = "Air"; return "Gemini"; }
                else
                { element = "Water"; return "Cancer"; }
            case 7: if (day <= 22)
                { element = "Water"; return "Cancer"; }
                else
                { element = "Fire"; return "Leo"; }
            case 8: if (day <= 23)
                { element = "Fire"; return "Leo"; }
                else
                { element = "Earth"; return "Virgo"; }
            case 9: if (day <= 23)
                { element = "Earth"; return "Virgo"; }
                else
                { element = "Air"; return "Libra"; }
            case 10: if (day <= 23)
                { element = "Air"; return "Libra"; }
                else
                { element = "Water"; return "Scorpio"; }
            case 11:if (day <= 22)
                { element = "Water"; return "Scorpio"; }
                else
                { element = "Fire"; return "Sagittarius"; }
            case 12: if (day<= 21)
                { element = "Fire"; return "Sagittarius"; }
                else
                { element = "Earth"; return "Capricorn"; }
            default:
                element = ""; return "";
        }
    }
    
	public void  Back(View v){
    	finish();	
	}
	
	
	class MyClickListener implements android.view.View.OnClickListener {

		public void onClick(View v) {
			
			switch (v.getId()){
			// TODO Auto-generated method stub
			case R.id.profilePicture1:{
			Toast eaw= Toast.makeText(MatchFriend.this,"Name "+array[1].getProperty("name"),Toast.LENGTH_SHORT);
			eaw.show();
			}
			break;
			case R.id.profilePicture2:{
				Toast eaw= Toast.makeText(MatchFriend.this,"Name "+array[2].getProperty("name"),Toast.LENGTH_SHORT);
				eaw.show();
				}
			break;

			case R.id.profilePicture3:{
				Toast eaw= Toast.makeText(MatchFriend.this,"Name "+array[3].getProperty("name"),Toast.LENGTH_SHORT);
				eaw.show();
				}	
			break;
			case R.id.profilePicture:{
				Toast eaw= Toast.makeText(MatchFriend.this,"Name "+Singleton.Name,Toast.LENGTH_SHORT);
				eaw.show();
				}
			break;

			}
		}

	}
	
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == NO_INFO) {
	    	
	      	 Log.d(TAG, "bd was start and now "+Singleton.bd);
	      	Log.d(TAG, "bd was start and now "+Singleton.gender);
	      	  set();
	     
	    }
	    if ((requestCode == BIRTHDAY)){
	    	set();
	    	}
	        //intent 1 was
	       
	    }
	
   
}//endclass