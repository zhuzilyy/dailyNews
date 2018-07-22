package com.qianyi.dailynews.utils;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends Activity {
	public static List<Activity> list=new ArrayList<Activity>();
	public static List<Activity> list2=new ArrayList<Activity>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	public static void close(){
		for (Activity a : list) {
			a.finish();
		}
	}
	public static void close2(){
		for (Activity a : list2) {
			a.finish();
		}
	}

}
