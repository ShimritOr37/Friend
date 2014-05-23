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
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
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

public class FriendPickerSampleActivity extends FragmentActivity {

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
	Button post, showlist, graph, button3, nextFriendi, authButton;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
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
	
		setContentView(R.layout.main);

	
		
		Log.d(TAG,"id is recover "+Singleton.Id);
		fb = new Facebook("143407059196911");
		profilePicture = (ProfilePictureView) findViewById(R.id.profilePicture);
		profilePicture.setProfileId(Singleton.Id);
		
		authButton = (Button)findViewById(R.id.authButton);
		
		
		publishCom = (ImageButton) findViewById(R.id.publishCom);
		nextFriendi = (Button) findViewById(R.id.nextF);
		//nextFriendi.setVisibility(View.INVISIBLE);

	

		// PickFriends declaration
		resultsTextView = (TextView) findViewById(R.id.resultsTextView);
		pickFriendsButton = (ImageButton) findViewById(R.id.pickFriendsButton);
		pickFriendsButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
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

			Intent intent3 = new Intent(this, DragActivity.class);
			startActivity(intent3);
		}

	}

	@SuppressWarnings("deprecation")
	public void buttonClicks2(View v) throws FacebookError,
			MalformedURLException, IOException, ParseException, JSONException {
		// switch (v.getId()){
		int id = v.getId();
	/*	if (R.id.logOut == id) {

			if (Session.getActiveSession() != null) {
				Session.getActiveSession().closeAndClearTokenInformation();
			}

			Session.setActiveSession(null);
			profilePicture.setProfileId(null);
			nextFriendi.setVisibility(View.INVISIBLE);
			birthday.setVisibility(View.INVISIBLE);
		}*/
		// login

	}// end onClick

	public void nextFriend(View v) throws MalformedURLException {
		index++;
	
		// imgView= (ImageView)findViewById(R.id.imageView1);
		birthday = (TextView) findViewById(R.id.birthday);
		TextView namef = (TextView) findViewById(R.id.namef);
		relationship = (TextView) findViewById(R.id.relationship);
		
			if (Singleton.friendInfoList != null) {
				friendInfoList=Singleton.friendInfoList;
				
				//Singleton.profilePicture.setProfileId(friendInfoList.get(index)
					//	.getProperty("uid").toString());
				
				Log.d(TAG,friendInfoList.get(index)
						.getProperty("uid").toString());
				
				String bd = friendInfoList.get(index)
						.getProperty("birthday_date").toString();

				birthday.setText(bd);

				namef.setText(friendInfoList.get(index).getProperty("name")
						.toString());

				if (friendInfoList.get(index).getProperty("relationship_status") != null){
					relationship.setText((CharSequence) friendInfoList
							.get(index).getProperty("relationship_status")
							.toString());
				}
				
				Singleton.friendInfo=friendInfoList.get(index);
				// birthday.setText("SS");
			

	}
	}

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
		displaySelectedFriends(RESULT_OK);
	

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
			trythis();
		}
			break;

		case RESULT_OK: {
			displaySelectedFriends(resultCode);
			Log.d("bugbug", "rs");
			trythis();
		}

		default:
			Session.getActiveSession().onActivityResult(this, requestCode,
					resultCode, data);
			break;
		}
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

	private void displaySelectedFriends(int resultCode) {

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
					
				}
				names.add(user.getName());
			}

			results = TextUtils.join(", ", names);

		} else {
			results = "<No friends selected>";

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

}
