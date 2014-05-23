package com.shimrit.pickforu;

import java.lang.reflect.Array;
import java.util.Arrays;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
	private ProfilePictureView profile;
	private static final String[] PERMISSIONS = new String[] {
		"friends_birthday", "user_birthday", "user_relationship_details",
		"friends_relationships", "friends_relationship_details",
		"read_stream", "offline_access" };
	String helpId;
	private UiLifecycleHelper uiHelper;
	GraphObjectList<GraphObject> friendInfoList;

	private static final String TAG = "MainFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.main, container, false);
	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    ProfilePictureView profilePicture = (ProfilePictureView)view.findViewById(R.id.profilePicture);
	    profile = (ProfilePictureView)view.findViewById(R.id.profilePicture);
	    
	    authButton.setFragment(this);
	    if (Singleton.Id!=null){
	    	profilePicture.setProfileId(Singleton.Id);
	    	
	    }
	    //authButton.setReadPermissions(Arrays.asList(PERMISSIONS));
	   
		//Session session = Session.getActiveSession();
		
		
		
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	    	//ensureOpenSession();
	        Log.i(TAG, "Logged in...");
	        String fqlQuery = "select uid, name, pic_square,birthday_date,birthday,sex, relationship_status from user where uid in (select uid2 from friend where uid1 = me())";
			Bundle params = new Bundle();
			params.putString("q", fqlQuery);
	        session.requestNewPublishPermissions(new Session.NewPermissionsRequest(
					this, PERMISSIONS));
			Request request = new Request(session, "/fql", params,
					HttpMethod.GET, new Request.Callback() {
						public void onCompleted(Response response) {
							Singleton.friendInfoList = response.getGraphObject().getPropertyAsList("data",GraphObject.class);
							Log.i("bugbug","Got results: " + response.toString());
						}
			});
			
			Request.executeBatchAsync(request);//req for list
			
			Bundle params2 = new Bundle();
			params2.putString("fields", "id,name,installed,picture");
			
			final Request req = new Request(Session.getActiveSession(), "me",
					params2, HttpMethod.GET, new Request.Callback() {
				@Override	
				public void onCompleted(Response response) {
					GraphObject graphObject = response.getGraphObject();
					if (graphObject != null) {
						Singleton.Id= graphObject.getProperty("id").toString();
						if (Singleton.Id != null) {			
							profile.setProfileId(Singleton.Id);
							Log.d(TAG, "IDIS" + Singleton.Id);
						}
					}
				}		});
			
			
			Request.executeBatchAsync(req);//req for user
			
		
	    } 
	    else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        profile.setProfileId(null);
	    }
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }};

	    @Override
	    public void onResume() {
	        super.onResume();
	     // For scenarios where the main activity is launched and user
	        // session is not null, the session state change notification
	        // may not be triggered. Trigger it if it's open/closed.
	       

	        uiHelper.onResume();
	    }
	        

	    @Override
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        uiHelper.onActivityResult(requestCode, resultCode, data);
	    }    

	    @Override
	    public void onPause() {
	        super.onPause();
	        uiHelper.onPause();
	    }

	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        uiHelper.onDestroy();
	    }

	    @Override
	    public void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        uiHelper.onSaveInstanceState(outState);
	    }
	    private boolean ensureOpenSession() {
			if (Session.getActiveSession() == null
					|| !Session.getActiveSession().isOpened()) {

				Session.openActiveSession(this.getActivity(), true, new Session.StatusCallback() {
					@Override
					public void call(Session session, SessionState state,
							Exception exception) {
						onSessionStateChange(session, state, exception);
					}
				});
				return false;
			}
			return true;

		}
	   
	}
