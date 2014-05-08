/**
 * Copyright 2010-present Facebook.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

import android.annotation.SuppressLint;
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


public class FriendPickerSampleActivity extends FragmentActivity {
	
	Intent intent44;
	static String Id;
	private static final String[] PERMISSIONS =
		    new String[] {"friends_birthday","user_relationship_details","friends_relationships","friends_relationship_details","read_stream", "offline_access"};
	private static final String TAG = FriendPickerSampleActivity.class.getSimpleName();
    private static final int PICK_FRIENDS_ACTIVITY = 1;
    private ImageButton pickFriendsButton;
    private TextView resultsTextView;
    TextView birthday ,relationship;
    private UiLifecycleHelper lifecycleHelper;
    boolean pickFriendsWhenSessionOpened;
    Button post,showlist,graph,button3;
    public static Facebook fb;
    ImageView pic;
	ImageView imgView;
    TextView welcome;
    ImageButton userinfo;
    private SharedPreferences sp;
    JSONObject obj;
    String accses_token;
    long expires=0;
    public static GraphObjectList<GraphObject> friendInfoList;
    final Object []  sign=new  GraphObject[12];
    public static int index,luck=0;
    private Session.StatusCallback statusCallback = new SessionStatusCallback();

    
   // long expires;
    Drawable normalShape;
    Drawable enterShape;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fb= new Facebook("143407059196911");
       // post= (Button)findViewById(R.id.button1);
        button3=(Button)findViewById(R.id.button3);
        showlist=(Button)findViewById(R.id.button2);
       // graph=(Button)findViewById(R.id.graph);
        userinfo=(ImageButton)findViewById(R.id.FacebookLogo);
      //  Button nextFriendi = (Button)findViewById(R.id.button4);
       // pic=(ImageView)findViewById(R.id.imageView1);
        setContentView(R.layout.main);
        LoginButton authButton = (LoginButton)findViewById(R.id.authButton);
		//authButton.setFragment(this);
		//authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));

        Session session = Session.getActiveSession();
        if (session == null) {
            if (savedInstanceState != null) {
                session = Session.restoreSession(this, null, statusCallback, savedInstanceState);
            }
            if (session == null) {
                session = new Session(this);
            }
            Session.setActiveSession(session);
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                session.openForRead(new Session.OpenRequest(this).setCallback(statusCallback));
            }
        }
        
        
        
        
        
        resultsTextView = (TextView) findViewById(R.id.resultsTextView);
        pickFriendsButton = (ImageButton) findViewById(R.id.pickFriendsButton);
        pickFriendsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onClickPickFriends();
            }
        });

        lifecycleHelper = new UiLifecycleHelper(this, new Session.StatusCallback() {
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                onSessionStateChanged(session, state, exception);
            }
        });
        lifecycleHelper.onCreate(savedInstanceState);

       
        
    }

	public void buttonClicks(View v) throws MalformedURLException, IOException{
		
    //	switch (v.getId()){
    	int id= v.getId();
    	if (R.id.FacebookLogo==id){
    		
    		Intent intent3 = new Intent(this,DragActivity.class);
    		startActivity(intent3);
    	}
    	/*
    	
    			if (fb.isSessionValid()){
    				fb.logout(getApplicationContext());
    				
    			}else{
    				accses_token=fb.getAccessToken();
    				Log.d(accses_token, "this is the accses tokn");
    				Toast toast3= Toast.makeText(FriendPickerSampleActivity.this, accses_token, Toast.LENGTH_SHORT);
    				toast3.show();
    				fb.authorize(FriendPickerSampleActivity.this, new String [] {"email"},new DialogListener() {
						
						@Override
						public void onFacebookError(FacebookError e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onError(DialogError e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onComplete(Bundle values) {
			    			Editor editor=sp.edit();
							editor.putString("accses_token", fb.getAccessToken());
							editor.putLong("accses_expires", fb.getAccessExpires());
							editor.commit();
						
					
							accses_token=fb.getAccessToken();
							// TODO Auto-generated method stub
							
						}
						
						@Override
							// TODO Auto-generated method stub
							
						}
					});
    				
    				Toast toast= Toast.makeText(FriendPickerSampleActivity.this, accses_token, Toast.LENGTH_SHORT);
    				toast.show();
    				
    				try{
    					
    				
        			Bitmap bmp;
        			URL img_url = null;
        			try {
						obj=Util.parseJson(fb.request("me"));
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			String name=obj.optString("name");
        			String id=obj.optString("id");
        			Toast toast2= Toast.makeText(FriendPickerSampleActivity.this, "name is"+name, Toast.LENGTH_SHORT);
    				toast2.show();
        			img_url= new URL("http://graph.facebook.com/589814070/picture?=type=small");
    				bmp = BitmapFactory.decodeStream(img_url.openConnection().getInputStream());
        			pic= (ImageView)findViewById(R.id.picture);	
        			pic.setImageBitmap(bmp);
        			pic.setVisibility(ImageView.VISIBLE);
        			
        			
    				
    				
        			} catch (FacebookError e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				} catch (IOException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				
    				}
    			}
    		 */
    			
    			
	}
	
    public void buttonClicks2(View v) throws FacebookError, MalformedURLException, IOException, ParseException{
    	//switch (v.getId()){
    	int id= v.getId();
    	
    	if (R.id.button2==id){
    		
    		Intent intent2 = new Intent(this,MyListActivity.class);
    		startActivity(intent2);
    	}
    	
    	if (R.id.button3==id){//login
    	
    		if (ensureOpenSession()){
    			
    		Bundle params2 = new Bundle();
    		params2.putString("fields", "id,name,installed,picture");
    		final Request req= new Request(Session.getActiveSession(),"me",params2,HttpMethod.GET,new Request.Callback() {


    		@Override
    		public void onCompleted(Response response) {
    			
    			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    			StrictMode.setThreadPolicy(policy); 
    			
    			GraphObject graphObject = response.getGraphObject();
    			
    		}});
    		Request.executeBatchAsync(req);


    		String fqlQuery = "select uid, name, pic_square,birthday, relationship_status from user where uid in (select uid2 from friend where uid1 = me())";
    		Bundle params = new Bundle();
    		params.putString("q", fqlQuery);


    		Session session = Session.getActiveSession();
    		session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSIONS));
    		Request request = new Request(session, 
    		"/fql", 
    		params, 
    		HttpMethod.GET, 
    		new Request.Callback(){ 
    		public void onCompleted(Response response) {
    		    Log.i("bugbug", "Got results: " + response.toString());
    		    friendInfoList = response.getGraphObject().getPropertyAsList("data", GraphObject.class);
    		    GraphObjectList<GraphObject> friendInfoList = response.getGraphObjectList();
    		    if (friendInfoList != null){
    		    	for (GraphObject friendInfo: friendInfoList){   		        		
    		    		if (friendInfo != null){
    		    			Log.d(TAG, "friend "+friendInfo.getProperty("name")+" pic="+friendInfo.getProperty("pic_square")+"relationjgfjycfjfhx="+friendInfo.getProperty("relationship_status"));
    		    			
    		    		}
    		    		else{
    				        	 Toast toast22= Toast.makeText(FriendPickerSampleActivity.this, "drawable is a null", Toast.LENGTH_SHORT);
    								toast22.show();
    				    }
    		    	}
    		    }		
    		    else {
    		    			Log.d(TAG, "friend object within list is null");
    		    }
    		    }    		        
    		});
    		Log.d(TAG,"THIS IS THE PERMISSION"+Session.getActiveSession().getPermissions());

    		Request.executeBatchAsync(request);
    	//	Button nextFriendi = (Button)findViewById(R.id.button4);
    	//	nextFriendi.setVisibility(Button.VISIBLE);
    		
    		 
    		 
}else
	
Log.d("ss","null");
    	
    
    
    	}}
    public  void nextFriend(View v) throws MalformedURLException{
    	index++;
    	Bitmap bm=null;
    	URL url=null;
    	//imgView= (ImageView)findViewById(R.id.imageView1);
    	birthday= (TextView)findViewById(R.id.birthday);
    	relationship=(TextView)findViewById(R.id.relationship);
    		try{
    			 if (friendInfoList != null){
    				 ProfilePictureView profilePicture = (ProfilePictureView)findViewById(R.id.profilePicture);
     			     profilePicture.setProfileId(friendInfoList.get(index).getProperty("uid").toString());
     			     birthday.setText((CharSequence) friendInfoList.get(index).getProperty("birthday").toString());
     			    if (friendInfoList.get(index).getProperty("relationship_status")!=null)
     			     relationship.setText((CharSequence) friendInfoList.get(index).getProperty("relationship_status").toString());
     			     //birthday.setText("SS");
    				 url= new URL("https://graph.facebook.com/"+friendInfoList.get(index).getProperty("uid")+"/picture?type=large");
    			 	bm=BitmapFactory.decodeStream(url.openConnection().getInputStream());
    			 	
    			 }
    			//imgView.setImageBitmap();
    		}
    			catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	
    			catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		
    		if (bm!=null){
    			imgView.setImageBitmap(bm);
    		}
}

    private static final int BUFFER_IO_SIZE = 8000;

    private Bitmap loadImageFromUrl(final String url) {
      Bitmap mp=null;
    	try {
           // Addresses bug in SDK :
           // http://groups.google.com/group/android-developers/browse_thread/thread/4ed17d7e48899b26/
           BufferedInputStream bis = new BufferedInputStream(new URL(url).openStream(), BUFFER_IO_SIZE);
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           BufferedOutputStream bos = new BufferedOutputStream(baos, BUFFER_IO_SIZE);
           copy(bis, bos);
           bos.flush();
           mp= BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.size());
           Log.d("BUGBUG","null!!"+mp);
           return mp;
          
       } catch (IOException e) {       
           // handle it properly
       }
	return mp;
   }

   private void copy(final InputStream bis, final OutputStream baos) throws IOException {
       byte[] buf = new byte[256];
       int l;
       while ((l = bis.read(buf)) >= 0) baos.write(buf, 0, l);
   }
			
    	
    @Override
    protected void onStart() {
        super.onStart();

        // Update the display every time we are started.
        displaySelectedFriends(RESULT_OK);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Call the 'activateApp' method to log an app event for use in analytics and advertising reporting.  Do so in
        // the onResume methods of the primary Activities that an app may be launched into.
        AppEventsLogger.activateApp(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_FRIENDS_ACTIVITY:
                displaySelectedFriends(resultCode);
                break;
                
            case 2:
            break;
            default:
                Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
                break;
        }
    }

    private boolean ensureOpenSession() {
        if (Session.getActiveSession() == null ||
                !Session.getActiveSession().isOpened()) {
        	
            Session.openActiveSession(this, true, new Session.StatusCallback() {
                @Override
                public void call(Session session, SessionState state, Exception exception) {
                    onSessionStateChanged(session, state, exception);
                }
            });
            return false;
        }
        return true;
        
    }

    private void onSessionStateChanged(Session session, SessionState state, Exception exception) {
        if (pickFriendsWhenSessionOpened && state.isOpened()) {
            pickFriendsWhenSessionOpened = false;

            startPickFriendsActivity();
        }
    }

    private void displaySelectedFriends(int resultCode) {
    	Log.d("BUGBUG","res code is " + resultCode);
        String results = "";
        FriendPickerApplication application = (FriendPickerApplication) getApplication();

        Collection<GraphUser> selection = application.getSelectedUsers();
        
        if (selection != null && selection.size() > 0) {
            ArrayList<String> names = new ArrayList<String>();
            for (GraphUser user : selection) {
                names.add(user.getName());
                Id=user.getId();
            }
            results = TextUtils.join(", ", names);
        } else {
            results = "<No friends selected>";
            
        }

        resultsTextView.setText(results);
        
        if (results != "<No friends selected>" && results!=null){
         intent44=  new Intent(this, MatchFriend.class);
        startActivityForResult(intent44, 1);
    }//if
    }
    

    private void onClickPickFriends() {
        startPickFriendsActivity();
    }

    private void startPickFriendsActivity() {
        if (ensureOpenSession()) {
            Intent intent = new Intent(this, PickFriendsActivity.class);
            // Note: The following line is optional, as multi-select behavior is the default for
            // FriendPickerFragment. It is here to demonstrate how parameters could be passed to the
            // friend picker if single-select functionality was desired, or if a different user ID was
            // desired (for instance, to see friends of a friend).
            PickFriendsActivity.populateParameters(intent, null, true, true);
            startActivityForResult(intent, PICK_FRIENDS_ACTIVITY);
        } else {
            pickFriendsWhenSessionOpened = true;
        }
    }
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
           
        }
    }
	
}
   
/*	try {

obj = Util.parseJson("me");
ProfilePictureView profilePicture=	(ProfilePictureView)findViewById(R.id.profilePicture);
profilePicture.setProfileId(obj.optString("id"));
}
catch (JSONException e) {

    e.printStackTrace();
} 

*/ 
/*
Log.d("BUGBUG","sessionis"+Session.getActiveSession().getAccessToken());
Bundle params2 = new Bundle();
params2.putString("fields", "id,name,installed,picture");
final Request req= new Request(Session.getActiveSession(),"me",params2,HttpMethod.GET,new Request.Callback() {


@Override
public void onCompleted(Response response) {
	
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	StrictMode.setThreadPolicy(policy); 
	
	GraphObject graphObject = response.getGraphObject();
//     Toast toast2= Toast.makeText(FriendPickerSampleActivity.this, "name is "+graphObject.getProperty("id"), Toast.LENGTH_SHORT);
//		toast2.show();
		
/*
		
		URL fbAvatarUrl=null;
		URL img_url=null;
		Bitmap fbAvatarBitmap=null;
		try {
			fbAvatarUrl = new URL("https://graph.facebook.com/"+graphObject.getProperty("id")+"/picture?type=large");
			fbAvatarBitmap = BitmapFactory.decodeStream(fbAvatarUrl.openConnection().getInputStream());

			//img_url=new URL("http://www.walla.com");
			img_url= new URL("http://graph.facebook.com/"+graphObject.getProperty("id")+"/picture?=type=small");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        ImageView imgView =(ImageView)findViewById(R.id.imageView1);
      //  Bitmap drawable = loadImageFromUrl("http://www.sendflowers.co.il/data/images/flowerTypes/Rose_red.jpg");
        if (fbAvatarBitmap!=null)
        imgView.setImageBitmap(fbAvatarBitmap);
        
        else
        {
        	 Toast toast22= Toast.makeText(FriendPickerSampleActivity.this, "drawable is a null", Toast.LENGTH_SHORT);
				toast22.show();
        }



}});
Request.executeBatchAsync(req);


String fqlQuery = "select uid, name, pic_square,birthday, relationship_status from user where uid in (select uid2 from friend where uid1 = me())";
Bundle params = new Bundle();
params.putString("q", fqlQuery);


Session session = Session.getActiveSession();
session.requestNewPublishPermissions(new Session.NewPermissionsRequest(this, PERMISSIONS));
Request request = new Request(session, 
"/fql", 
params, 
HttpMethod.GET, 
new Request.Callback(){ 
public void onCompleted(Response response) {
    Log.i("bugbug", "Got results: " + response.toString());
    friendInfoList = response.getGraphObject().getPropertyAsList("data", GraphObject.class);
    GraphObjectList<GraphObject> friendInfoList = response.getGraphObjectList();
//  if (friendInfoList!=null)
  //  if (!(friendInfoList.get(index).getProperty("relationship_status").toString()=="Marrid")){
    //	index++;
   /* 	SimpleDateFormat inputDF= new SimpleDateFormat("MM/dd/YY");
    	Date date1=null;
		try {
			date1 = inputDF.parse("9/30/11");
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
		
		Log.d(TAG,"month"+month+ "day"+year);
		}
		else{
			Log.d("bugbug","isnull!");
		}
		
    //	sign[luck]=friendInfoList.get(index).getProperty("birthday");
    	
    	//index++;
    	 * 
    	 
 //   }
    Log.d(TAG, "friendInfoList ="+friendInfoList);
    
   // Log.i("bugbug",)
    if (friendInfoList != null){
    	for (GraphObject friendInfo: friendInfoList){   		        		
    		if (friendInfo != null){
    			Log.d(TAG, "friend "+friendInfo.getProperty("name")+" pic="+friendInfo.getProperty("pic_square")+"relationjgfjycfjfhx="+friendInfo.getProperty("relationship_status"));
    			
    		}
    		else{
		        	 Toast toast22= Toast.makeText(FriendPickerSampleActivity.this, "drawable is a null", Toast.LENGTH_SHORT);
						toast22.show();
		    }
    	}
    }		
    else {
    			Log.d(TAG, "friend object within list is null");
    }
    }    		        
});
Log.d(TAG,"THIS IS THE PERMISSION"+Session.getActiveSession().getPermissions());

Request.executeBatchAsync(request);
*/