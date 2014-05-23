package com.shimrit.pickforu;

import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.widget.ProfilePictureView;

public class Singleton {
	
	public static int month,day=0;
	public static String Id="", Name,bd="start",gender;
	public static GraphObjectList<GraphObject> friendInfoList;
	public static GraphObject friendInfo;
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
