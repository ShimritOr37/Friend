package com.shimrit.pickforu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.widget.ProfilePictureView;

public class Singleton {
	
	public static int month,day=0;
	public static String Id="",UserId="",UserBd,UserName="start", Name,bd="start",gender,genderUser;
	public static GraphObjectList<GraphObject> friendInfoList;
	public static GraphObject friendInfo;
	protected static int pick,i=0;
	public static boolean next=false;

	public static Map<String, List <GraphObject>> output = new HashMap<String, List <GraphObject>>();
	// Private constructor suppresses generation of a (public) default constructor
	  private Singleton() {}
	 
	  /**
	   * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
	   * or the first access to SingletonHolder.INSTANCE, not before.
	   */
	  private static class SingletonHolder
	  { 
	    private final static Singleton INSTANCE = new Singleton();
	  }
	 
	  public static Singleton getInstance()
	  {
	    return SingletonHolder.INSTANCE;
	  }
}
