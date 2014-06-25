package com.shimrit.pickforu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionState;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.model.GraphObject;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.shimrit.pickforu.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.SharedPreferencesTokenCachingStrategy;


@SuppressLint("Instantiatable")
public class DragActivity extends Activity {
	
	Button back;
	private static String TAG= DragActivity.class.getSimpleName();
/** Called when the activity is first created. */
	  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    
	  
	super.onCreate(savedInstanceState);
	
    setContentView(R.layout.activity_darg);
    
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

    StrictMode.setThreadPolicy(policy); 
    Singleton.pick=0;//initial state
   
    findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
    findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());
    findViewById(R.id.myimage3).setOnTouchListener(new MyTouchListener());
    findViewById(R.id.myimage4).setOnTouchListener(new MyTouchListener());
    findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
    findViewById(R.id.topright).setOnDragListener(new MyDragListener2());
    findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener3());
    findViewById(R.id.bottomright).setOnDragListener(new MyDragListener4());
    Button nextFriendi = (Button) findViewById(R.id.nextF);
    
    setPic();
  }
    public void setPic(){
    if (Singleton.friendInfoList!=null){
    	
    	Bitmap bm=null;
		URL url=null;
	  
		if (Singleton.next){// if no one was chose take the first friend
			GraphObject member=Singleton.friendInfoList.get(Singleton.i);
			if (member!=null){
			Singleton.Id= member.getProperty("uid").toString();
			}
		}
	//	Log.d(TAG,"number is"+ Singleton.friendInfo.getProperty("uid"));	
		
		try{
			 	url= new URL("https://graph.facebook.com/"+Singleton.Id+"/picture?type=large");
			 	bm=BitmapFactory.decodeStream(url.openConnection().getInputStream());
			 
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
		ImageView imgView=(ImageView)findViewById(R.id.myimage1);
		if (bm!=null)
		imgView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(bm, 130));
   // findViewById(R.drawable.icon).setBackground(background);
  }
  
  }
  
  public void nextFriend(View v){
	  Singleton.next=true;
	  Singleton.i++;
	  setPic();
	  
  }

/*	public void nextFriend(View v) throws MalformedURLException {
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
	
	*/
  @SuppressLint("Instantiatable")
private final class MyTouchListener implements OnTouchListener {
    public boolean onTouch(View view, MotionEvent motionEvent) {
      if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
        ClipData data = ClipData.newPlainText("", "");
        DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
        view.startDrag(data, shadowBuilder, view, 0);
        view.setVisibility(View.INVISIBLE);
        return true;
      } else {
        return false;
      }
    }
  }

  class MyDragListener  implements OnDragListener  {
		 
		 Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
		 Drawable normalShape = getResources().getDrawable(R.drawable.shape);
		  @SuppressWarnings("deprecation")
		@Override
		    public boolean onDrag(View v, DragEvent event) {
		       int action = event.getAction();
		      switch (event.getAction()) {
		      case DragEvent.ACTION_DRAG_STARTED:
		        // do nothing
		        break;
		      case DragEvent.ACTION_DRAG_ENTERED:
		        v.setBackgroundDrawable(enterShape);
		        break;
		      case DragEvent.ACTION_DRAG_EXITED:
		        v.setBackgroundDrawable(normalShape);
		        break;
		      case DragEvent.ACTION_DROP:
		        // Dropped, reassign View to ViewGroup
		        View view = (View) event.getLocalState();
		        ViewGroup owner = (ViewGroup) view.getParent();
		        owner.removeView(view);
		        LinearLayout container = (LinearLayout) v;
		        container.addView(view);
		        view.setVisibility(View.VISIBLE);
		      float ox= event.getX();
		      float oy=event.getY();
		      Log.d(TAG,"result is"+(int)ox+"y is"+(int)oy);
		     //   post();
		      
		        break;
		      case DragEvent.ACTION_DRAG_ENDED:
		        v.setBackgroundDrawable(normalShape);
		      default:
		        break;
		      }
		      return true;
		    }
		  }
////////////////
  class MyDragListener2  implements OnDragListener  {
	  
	 String des="smart";
		 
		 Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
		 Drawable normalShape = getResources().getDrawable(R.drawable.shape);
		  @SuppressWarnings("deprecation")
		@Override
		    public boolean onDrag(View v, DragEvent event) {
		       int action = event.getAction();
		      switch (event.getAction()) {
		      case DragEvent.ACTION_DRAG_STARTED:
		        // do nothing
		        break;
		      case DragEvent.ACTION_DRAG_ENTERED:
		        v.setBackgroundDrawable(enterShape);
		        break;
		      case DragEvent.ACTION_DRAG_EXITED:
		        v.setBackgroundDrawable(normalShape);
		        break;
		      case DragEvent.ACTION_DROP:
		        // Dropped, reassign View to ViewGroup
		        View view = (View) event.getLocalState();
		        ViewGroup owner = (ViewGroup) view.getParent();
		        owner.removeView(view);
		        LinearLayout container = (LinearLayout) v;
		        container.addView(view);
		        view.setVisibility(View.VISIBLE);
		      float ox= event.getX();
		      float oy=event.getY();
		      Log.d(TAG,"result is"+(int)ox+"y is"+(int)oy);
		        post(des);
		      
		        break;
		      case DragEvent.ACTION_DRAG_ENDED:
		        v.setBackgroundDrawable(normalShape);
		      default:
		        break;
		      }
		      return true;
		    }
		  }
  ////////////
  class MyDragListener3  implements OnDragListener  {
	  
		String  des="funny";
		 
		 Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
		 Drawable normalShape = getResources().getDrawable(R.drawable.shape);
		  @SuppressWarnings("deprecation")
		@Override
		    public boolean onDrag(View v, DragEvent event) {
		       int action = event.getAction();
		      switch (event.getAction()) {
		      case DragEvent.ACTION_DRAG_STARTED:
		        // do nothing
		        break;
		      case DragEvent.ACTION_DRAG_ENTERED:
		        v.setBackgroundDrawable(enterShape);
		        break;
		      case DragEvent.ACTION_DRAG_EXITED:
		        v.setBackgroundDrawable(normalShape);
		        break;
		      case DragEvent.ACTION_DROP:
		        // Dropped, reassign View to ViewGroup
		        View view = (View) event.getLocalState();
		        ViewGroup owner = (ViewGroup) view.getParent();
		        owner.removeView(view);
		        LinearLayout container = (LinearLayout) v;
		        container.addView(view);
		        view.setVisibility(View.VISIBLE);
		      float ox= event.getX();
		      float oy=event.getY();
		      Log.d(TAG,"result is"+(int)ox+"y is"+(int)oy);
		        post(des);
		      
		        break;
		      case DragEvent.ACTION_DRAG_ENDED:
		        v.setBackgroundDrawable(normalShape);
		      default:
		        break;
		      }
		      return true;
		    }
		  }
  //////
  class MyDragListener4  implements OnDragListener  {
	  
		 String des="kind";
		 
		 Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
		 Drawable normalShape = getResources().getDrawable(R.drawable.shape);
		  @SuppressWarnings("deprecation")
		@Override
		    public boolean onDrag(View v, DragEvent event) {
		       int action = event.getAction();
		      switch (event.getAction()) {
		      case DragEvent.ACTION_DRAG_STARTED:
		        // do nothing
		        break;
		      case DragEvent.ACTION_DRAG_ENTERED:
		        v.setBackgroundDrawable(enterShape);
		        break;
		      case DragEvent.ACTION_DRAG_EXITED:
		        v.setBackgroundDrawable(normalShape);
		        break;
		      case DragEvent.ACTION_DROP:
		        // Dropped, reassign View to ViewGroup
		        View view = (View) event.getLocalState();
		        ViewGroup owner = (ViewGroup) view.getParent();
		        owner.removeView(view);
		        LinearLayout container = (LinearLayout) v;
		        container.addView(view);
		        view.setVisibility(View.VISIBLE);
		      float ox= event.getX();
		      float oy=event.getY();
		      Log.d(TAG,"result is"+(int)ox+"y is"+(int)oy);
		        post(des);
		      
		        break;
		      case DragEvent.ACTION_DRAG_ENDED:
		        v.setBackgroundDrawable(normalShape);
		      default:
		        break;
		      }
		      return true;
		    }
  }
  /////

public void post(String des) {
	String Token=Session.getActiveSession().getAccessToken();
	Bundle params = new Bundle();
	params.putString("token",Token);
	params.putString("id",Singleton.UserId);
	params.putString("to", Singleton.Id);
	params.putString("description", "As your friend I have to say you are so "+des);
	//params.putString("link", "http://www.gmail.com");
	if (des=="funny")
	params.putString("picture", "http://www.clown.co.il/admin_heb/uploaded/file_3.jpg");
	if (des=="smart")
    params.putString("picture", "http://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Albert_Einstein_Head.jpg/200px-Albert_Einstein_Head.jpg");	
	if (des=="kind")
	params.putString("picture", "http://www.hashalom.org.il/iton/huthashani/images/tmp/%D7%94%D7%A2%D7%A5%20%D7%94%D7%A0%D7%93%D7%99%D7%91.jpg");	

	

	
	Facebook fb;
	fb=new Facebook("143407059196911");
	

	fb.dialog(DragActivity.this, "feed",params, new DialogListener() {
		
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
			
			// TODO Auto-generated method stub
		
			
			
		}
		
		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			finish();
			
		}
	});
	// TODO Auto-generated method stub
	 
}

}//class




  


