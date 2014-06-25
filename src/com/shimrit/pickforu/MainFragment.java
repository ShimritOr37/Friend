package com.shimrit.pickforu;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainFragment extends Fragment {
	
	private ProfilePictureView profile,sp1,sp2,sp3,sp4,sp5,sp6,sp7,sp8,sp9,sp10,sp11;
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
	    profile=(ProfilePictureView)view.findViewById(R.id.profilePicture);
	     sp1=(ProfilePictureView)view.findViewById(R.id.sp1);
		 sp2=(ProfilePictureView)view.findViewById(R.id.sp2);
		 sp3=(ProfilePictureView)view.findViewById(R.id.sp3);
		 sp4=(ProfilePictureView)view.findViewById(R.id.sp4);
		 sp5=(ProfilePictureView)view.findViewById(R.id.sp5);
		 sp6=(ProfilePictureView)view.findViewById(R.id.sp6);
		 sp7=(ProfilePictureView)view.findViewById(R.id.sp7);
		 sp8=(ProfilePictureView)view.findViewById(R.id.sp8);
		 sp9=(ProfilePictureView)view.findViewById(R.id.sp9);
		 sp10=(ProfilePictureView)view.findViewById(R.id.sp10);
		 sp11=(ProfilePictureView)view.findViewById(R.id.sp11);
	    if (Singleton.UserId!=null){
	    	profilePicture.setProfileId(Singleton.UserId); 	
	    }
	    

	    Log.d(TAG,"ENTER VIEW");
	    authButton.setReadPermissions(Arrays.asList(PERMISSIONS));
		
		
		return view;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	    Log.d(TAG,"LIFE CICLE IS ON");
	}
	
	

	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	    	
	        Log.i(TAG, "Logged in...");
			Bundle params2 = new Bundle();
			params2.putString("fields","id,name,birthday,installed,picture");
			
			final Request req = new Request(Session.getActiveSession(), "me",
					params2, HttpMethod.GET, new Request.Callback() {
				@Override	
				public void onCompleted(Response response) {
					GraphObject graphObject = response.getGraphObject();
					if (graphObject != null) {
						
						Singleton.UserId= graphObject.getProperty("id").toString();
						if (graphObject.getProperty("birthday")!=null){
						Singleton.UserBd= graphObject.getProperty("birthday").toString();
						}else{
							Singleton.UserBd="start";
						}
						Singleton.UserName= graphObject.getProperty("name").toString();
						Object g= graphObject.getProperty("gender");
						if (g!=null){
						Singleton.genderUser=g.toString();
						}else{Singleton.genderUser="empty";}
						if (Singleton.UserId != null) {			
							profile.setProfileId(Singleton.UserId);
							Log.d(TAG, "IDIS" + Singleton.UserId);
									
						}
					}
					reqList(Session.getActiveSession());	
				}		});
			
			
			Request.executeBatchAsync(req);//req for user}
	    	
	    
			
		
	    } 
	    else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        profile.setProfileId(null);
	    }
	}
	
	private void reqList(Session session){
		String fqlQuery = "select uid, name, pic_square,birthday_date,birthday,sex, relationship_status from user where uid in (select uid2 from friend where uid1 = me())";
		Bundle params = new Bundle();
		params.putString("q", fqlQuery);
      
		Request request = new Request(session, "/fql", params,
				HttpMethod.GET, new Request.Callback() {
					public void onCompleted(Response response) {
						Singleton.friendInfoList = response.getGraphObject().getPropertyAsList("data",GraphObject.class);
						Log.i("bugbug","Got results: " + response.toString());

				        Session.getActiveSession().requestNewPublishPermissions(
			                    new Session.NewPermissionsRequest(MainFragment.this, "publish_actions"));
				        
				        Singleton.output=MatchFriend.getlist();
				        if (Singleton.UserId != null) {			
							sp1.setProfileId(Singleton.UserId);
							Log.d(TAG, "IDIS" + Singleton.UserId);
									
						}
				    	new CountDownTimer(6000, 1000)
				 		{

							@Override
							public void onTick(long millisUntilFinished) {
								// TODO Auto-generated method stub
								
							}

							@Override
							public void onFinish() {
								// TODO Auto-generated method stub
								Log.d("bugbug","output is "+Singleton.output.containsKey("Taurus"));
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
									String temp="p".concat(String.valueOf(j));
									if (!Singleton.output.isEmpty()&&Singleton.output.get(sign).get(j)!=null){
									setImage.get(temp).setProfileId(Singleton.output.get(sign).get(j).getProperty("uid").toString());
									Log.d("bugbug","output is "+temp+" "+Singleton.output.get(sign).get(j).getProperty("uid"));
									}
								
							}

							}

				 		}.start();
				      
						
					}
		});
		
		Request.executeBatchAsync(request);//req for list
	
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
	
	        uiHelper.onResume();}
	   
	        

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
