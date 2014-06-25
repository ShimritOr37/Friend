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

package com.shimrit.pickforu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.Manifest.permission;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AppEventsLogger;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FriendPickerSampleActivity extends FragmentActivity implements Runnable {
	private OnClickListener clickListener;
	Gson gson;
	static int tryit = 0;
	Intent intent44;
	static String Id, birthdayd, Name;
	private MySQLiteHelper dbHelper;
	private SQLiteDatabase database;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
			MySQLiteHelper.COLUMN_COMMENT };

	private static final String[] PERMISSIONS = new String[] {
			"friends_birthday", "user_birthday", "user_relationship_details",
			"friends_relationships", "friends_relationship_details",
			"read_stream", "offline_access" };
	private static final String TAG = FriendPickerSampleActivity.class
			.getSimpleName();
	private static final int PICK_FRIENDS_ACTIVITY = 1;
	private ImageButton pickFriendsButton;
	private TextView resultsTextView;
	TextView birthday, relationship;
	private UiLifecycleHelper lifecycleHelper;
	boolean pickFriendsWhenSessionOpened;
	Button post, showlist, graph, button3, nextFriendi ;
	LoginButton authButton;
	public static Facebook fb;
	ImageView pic;
	ImageView imgView;
	TextView welcome;
	ImageButton userinfo, publishCom;
	private SharedPreferences sp;
	SharedPreferences.Editor editor;
	JSONObject json;
	Gson obj;
	String accses_token;
	long expires = 0;
	public static GraphObjectList<GraphObject> friendInfoList;
	final Object[] sign = new GraphObject[12];
	public static int index, luck = 0;
	private Session.StatusCallback statusCallback = new SessionStatusCallback();
	static ProfilePictureView profilePicture;
	private MainFragment mainFragment;
	// long expires;
	Drawable normalShape;
	Drawable enterShape;
	protected GraphObjectList<GraphObject> friendInfoList2;
	ArrayList<String> help = new ArrayList<String>();
	private static  TextView con;
	static  ProfilePictureView sp1,sp2,sp3,sp4,sp5,sp6,sp7,sp8,sp9,sp10,sp11;
	@SuppressWarnings("null")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (isOnline()){
		com.facebook.Settings.setPlatformCompatibilityEnabled(true);
		   if (savedInstanceState == null) {
		        // Add the fragment on initial activity setup
		        mainFragment = new MainFragment();
		        getSupportFragmentManager()
		        .beginTransaction()
		        .add(android.R.id.content, mainFragment)
		        .commit();
		    } else {
		        // Or set the fragment from restored state info
		        mainFragment = (MainFragment) getSupportFragmentManager()
		        .findFragmentById(android.R.id.content);
		    }
		}else{
			new AlertDialog.Builder(this)
		    .setTitle("There is no  internet connection")
		    .setMessage("Are you want to use an offline mode anyway?")
		    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // continue with delete
		        }
		     })
		    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) { 
		            // do nothing
		        }
		     })
		    .setIcon(android.R.drawable.ic_dialog_alert)
		     .show();
		}
	
		setContentView(R.layout.main);

	
	if (Singleton.UserId!=null){	
		Log.d(TAG,"id is recover "+Singleton.UserId);
		profilePicture = (ProfilePictureView) findViewById(R.id.profilePicture);
		profilePicture.setProfileId(Singleton.UserId);

    }
	
		authButton = (LoginButton)findViewById(R.id.authButton);//login sec time
	
		
		fb = new Facebook("143407059196911");	
		publishCom = (ImageButton) findViewById(R.id.publishCom);
		// setup new possible matches
		 con=(TextView)findViewById(R.id.con);

			//Thread for swipe pictures
			 Thread currentThread = new Thread(this);
		     currentThread.start();	

		
		
		// PickFriends declaration
		resultsTextView = (TextView) findViewById(R.id.resultsTextView);
		pickFriendsButton = (ImageButton) findViewById(R.id.pickFriendsButton);
		pickFriendsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Singleton.pick=0;
				onClickPickFriends();

			}
		});
		publishCom=(ImageButton) findViewById(R.id.publishCom);
		pickFriendsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Singleton.pick=1;
				onClickPickFriends();

			}
		});
		

		lifecycleHelper = new UiLifecycleHelper(this,
				new Session.StatusCallback() {
					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						onSessionStateChanged(session, state, exception);
					}
				});
		lifecycleHelper.onCreate(savedInstanceState);
		
	}
	
	public boolean isOnline() 
	{
	    //Getting the ConnectivityManager.
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	    //Getting NetworkInfo from the Connectivity manager.
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();

	    //If I received an info and isConnectedOrConnecting return true then there is an Internet connection.
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) 
	    {
	        return true;
	    }
	    return false;
	}

	public void onClick(View view) {
		onClickPickFriends();
		
	}

	private class SessionStatusCallback implements Session.StatusCallback {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {

		}
	}

	public void buttonClicks(View v) throws MalformedURLException, IOException {

		int id = v.getId();
		if (R.id.publishCom == id) {
			//authButton.setPublishPermissions("publish_actions");
			Singleton.pick=1;
			onClickPickFriends();
		//	Intent intent3 = new Intent(this, DragActivity.class);
			//startActivity(intent3);
		}
		
		if (R.id.matchMyself == id) {
			if (ensureOpenSession()){
			Singleton.Id=Singleton.UserId;
			if (Singleton.UserBd!=null){
			Singleton.bd=Singleton.UserBd;}else{Singleton.bd="start";}
			Singleton.Name=Singleton.UserName;
			Singleton.gender=Singleton.genderUser;
			Log.d(TAG,"USER DETAILS"+Singleton.Id+" "+Singleton.bd+" "+Singleton.Name);
			Intent intent4 = new Intent(this, MatchFriend.class);
			startActivity(intent4);
		}
		}
		

	}

	@SuppressWarnings("deprecation")
	public void buttonClicks2(View v) {
			
		// switch (v.getId()){
		int id = v.getId();
		if (R.id.authButton == id) {

			//StartLogin();
		}
		// login

	}// end onClick
	



	private static final int BUFFER_IO_SIZE = 8000;
	private static final int NEW_MATCH = 2;

	private Bitmap loadImageFromUrl(final String url) {
		Bitmap mp = null;
		try {
			// Addresses bug in SDK :
			// http://groups.google.com/group/android-developers/browse_thread/thread/4ed17d7e48899b26/
			BufferedInputStream bis = new BufferedInputStream(
					new URL(url).openStream(), BUFFER_IO_SIZE);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos,
					BUFFER_IO_SIZE);
			copy(bis, bos);
			bos.flush();
			mp = BitmapFactory.decodeByteArray(baos.toByteArray(), 0,
					baos.size());
			Log.d("BUGBUG", "null!!" + mp);
			return mp;

		} catch (IOException e) {
			// handle it properly
		}
		return mp;
	}

	private void copy(final InputStream bis, final OutputStream baos)
			throws IOException {
		byte[] buf = new byte[256];
		int l;
		while ((l = bis.read(buf)) >= 0)
			baos.write(buf, 0, l);
	}

	@Override
	protected void onStart() {
	super.onStart();
	
		// Update the display every time we are started.
		//displaySelectedFriends(RESULT_OK);
	

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		// Call the 'activateApp' method to log an app event for use in
		// analytics and advertising reporting. Do so in
		// the onResume methods of the primary Activities that an app may be
		// launched into.
		AppEventsLogger.activateApp(this);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case PICK_FRIENDS_ACTIVITY: {
			
			displaySelectedFriends(resultCode);
			Log.d("bugbug", "pf");
			if (Singleton.pick==0) {
			trythis();}
			if (Singleton.pick==1) {
				startDrag();}
			
		}
			break;

		case RESULT_OK: {
			displaySelectedFriends(resultCode);
			Log.d("bugbug", "rs");
			if (Singleton.pick==0) {
				trythis();}
				if (Singleton.pick==1) {
					startDrag();}
				
		}

		default:
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);
			break;
		}
	}

	private void startDrag() {
		// TODO Auto-generated method stub
		intent44 = new Intent(this, DragActivity.class);
		startActivity(intent44);
		
	}

	private boolean ensureOpenSession() {
		if (Session.getActiveSession() == null
				|| !Session.getActiveSession().isOpened()) {

			Session.openActiveSession(this, true, new Session.StatusCallback() {
				@Override
				public void call(Session session, SessionState state,
						Exception exception) {
					onSessionStateChanged(session, state, exception);
				}
			});
			return false;
		}
		return true;

	}

	private void onSessionStateChanged(Session session, SessionState state,
			Exception exception) {
		if (pickFriendsWhenSessionOpened && state.isOpened()) {
			pickFriendsWhenSessionOpened = false;

			startPickFriendsActivity();
		}
	}

	private  void displaySelectedFriends(int resultCode) {

		Log.d("BUGBUG", "res code is " + resultCode);
		String results = "";
		FriendPickerApplication application = (FriendPickerApplication) getApplication();

		Collection<GraphUser> selection = application.getSelectedUsers();

		if (selection != null && selection.size() > 0) {
			ArrayList<String> names = new ArrayList<String>();

			for (GraphUser user : selection) {

				if (help == null) {// help is list to save users selected by
									// order
					help.add(user.getId());

				}
				if (!help.contains(user.getId())) {// check if friend was
													// already selected
					help.add(Singleton.Id);
					Log.d("bugbug", "in help" + user.getName());
					Singleton.Id = user.getId();
					Singleton.Name = user.getName();
					boolean flage=true;
					if (Singleton.friendInfoList!=null||flage){
						for (GraphObject friendInfo: Singleton.friendInfoList) {
							if (friendInfo!=null)
							if (friendInfo.getProperty("uid").toString().equals(Singleton.Id.toString())){
								if (friendInfo.getProperty("birthday_date")!=null){
									Singleton.bd=friendInfo.getProperty("birthday_date").toString();
									flage=false;
									Log.d("bugbug","bd"+Singleton.bd);
								}
								if (friendInfo.getProperty("sex")!=null)
									Singleton.gender=friendInfo.getProperty("sex").toString();
								
						}
					}
				
					Singleton.next=false;
					}
				}
					
				names.add(user.getName());
			}

			results = TextUtils.join(", ", names);

		} else {
			results = "";

		}

		resultsTextView.setText(results);

	}

	private void trythis() {// trythis activat
		intent44 = new Intent(this, MatchFriend.class);
		startActivity(intent44);
	}

	private void onClickPickFriends() {
		startPickFriendsActivity();

	}
	
	
	private void startPickFriendsActivity() {
		if (ensureOpenSession()) {
			Intent intent = new Intent(this, PickFriendsActivity.class);
			// Note: The following line is optional, as multi-select behavior is
			// the default for
			// FriendPickerFragment. It is here to demonstrate how parameters
			// could be passed to the
			// friend picker if single-select functionality was desired, or if a
			// different user ID was
			// desired (for instance, to see friends of a friend).
			PickFriendsActivity.populateParameters(intent, null, true, true);
			startActivityForResult(intent, PICK_FRIENDS_ACTIVITY);

		} else {
			pickFriendsWhenSessionOpened = true;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		  try {
	            // all the stuff we want our Thread to do goes here
			  Log.d("bugbug","new thread"); 
			  Thread.sleep(12000);
			
	            // signaling things to the outside world goes like this
	            threadHandler.sendEmptyMessage(0);
	            Log.d("bugbug","new thread");
	        } catch (InterruptedException e) {
	            //don't forget to deal with the Exception !!!!!
	        }
		
	}
	private Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // whenever the Thread notifies this handler we have
            // only this behavior
            Log.d("bugbug","new mess");
           
        }
    };
	public static void setPic() {
		// TODO Auto-generated method stub
		 
		String sign= "Taurus";

		Map<String, ProfilePictureView> setImage = new HashMap<String, ProfilePictureView>();;
	
		
		setImage.put("p1", sp1);
		setImage.put("p2", sp2);
		setImage.put("p3", sp3);
		setImage.put("p4", sp4);
		setImage.put("p5", sp5);
		setImage.put("p6", sp6);
		setImage.put("p7", sp7);
		setImage.put("p8", sp8);
		setImage.put("p9", sp9);
		setImage.put("p10",sp10);
		setImage.put("p11",sp11);
		
		for (int j = 1; j<=11; j++) {
			if (!Singleton.output.isEmpty()&&Singleton.output.get(sign).get(j)!=null){
			setImage.get("p".concat(String.valueOf(j))).setProfileId(Singleton.output.get(sign).get(j).toString());
			Log.d("bugbug","enter pp");
			}
		}
		
		
	}

}
