package com.shimrit.pickforu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.shimrit.pickforu.R;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
    back= (Button)findViewById(R.id.back);
    findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
    findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());
    findViewById(R.id.myimage3).setOnTouchListener(new MyTouchListener());
    findViewById(R.id.myimage4).setOnTouchListener(new MyTouchListener());
    findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
    findViewById(R.id.topright).setOnDragListener(new MyDragListener2());
    findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener3());
    findViewById(R.id.bottomright).setOnDragListener(new MyDragListener4());
    
    if (Singleton.friendInfoList!=null){
    	
    	Bitmap bm=null;
		URL url=null;
		
		if (Singleton.friendInfo==null){// if no one was chose take the first friend
			Singleton.friendInfo=Singleton.friendInfoList.get(0);
		}
		Log.d(TAG,"number is"+ Singleton.friendInfo.getProperty("uid"));	
		
		try{
			 	url= new URL("https://graph.facebook.com/"+Singleton.friendInfo.getProperty("uid")+"/picture?type=large");
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
  public void back(View v){
	 finish();
  }
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
	
	Bundle params = new Bundle();
	params.putString("to", Singleton.friendInfo.getProperty("uid").toString());
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
			
		}
	});
	// TODO Auto-generated method stub
}


}//class




  


