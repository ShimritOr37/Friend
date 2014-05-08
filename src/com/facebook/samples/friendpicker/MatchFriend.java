package com.facebook.samples.friendpicker;


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
import java.util.List;

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
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import android.os.Bundle;


public class MatchFriend extends Activity {
	
	Button back;
	String TAG= MatchFriend.class.getSimpleName();
	GraphObject[] array= new GraphObject[40];
	static int index;
	 List<GraphObject> Aries,	Taurus	,Gemini	,Cancer	,Leo	,Virgo	,Libra,	Scorpio,	Sagittarius,	Capricorn,	Aquarius,	Pisces = new ArrayList<GraphObject>();
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.matchafriend);
	   
	    if (FriendPickerSampleActivity.friendInfoList != null){
	    	index=0;
        	for (GraphObject friendInfo: FriendPickerSampleActivity.friendInfoList){   		        		
        		if ((friendInfo != null)&&(index!=12)){
        			if (friendInfo.getProperty("relationship_status").toString()!="Married"){
        				Log.d(TAG,"DATE"+friendInfo.getProperty("birthday").toString().substring(0, 4));
    				array[index]=friendInfo;
        				
        			//Log.d(TAG, "friend "+friendInfo.getProperty("name")+" pic="+friendInfo.getProperty("pic_square")+"relationship status="+friendInfo.getProperty("relationship_status"));
        			Log.d(TAG,array[index].getProperty("name")+" is singal");
        			index++;
        		}
        		else{
        	
        		}
        	  }//index
        		}//endFor
        			

        	
        	 ProfilePictureView profilePictureM = (ProfilePictureView)findViewById(R.id.profilePicture);
        	 ProfilePictureView profilePicture = (ProfilePictureView)findViewById(R.id.profilePicture1);
        	 ProfilePictureView profilePicture2 = (ProfilePictureView)findViewById(R.id.profilePicture2);
        	 ProfilePictureView profilePicture3 = (ProfilePictureView)findViewById(R.id.profilePicture3);
        	 Button back= (Button)findViewById(R.id.back);
			     profilePicture.setProfileId(array[1].getProperty("uid").toString());
			     profilePicture2.setProfileId(array[2].getProperty("uid").toString());
			     profilePicture3.setProfileId(array[3].getProperty("uid").toString());
			     
			     profilePictureM.setProfileId(FriendPickerSampleActivity.Id);
	    }//endFriendList
	
}//endCreate
    public void  Back(View v){
    	
    	Intent intent77= new Intent();
    	startActivityForResult(intent77, 2);
}
}//endclass