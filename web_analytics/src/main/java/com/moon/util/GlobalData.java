package com.moon.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

/** 
 *Created by wei on 2016/5/4
 * 
 * */
public class GlobalData {
	
	public static final String SessionUser="user";
	public static final String LIST = "list";
	public static final String SINGLE = "single";
	public static final String REDO = "redo";
	public static final Object SessionGuesst = "admin";
	
	public static int toInt( SortedMap sm, String key )
	{
		int i  = -1;
		if (sm==null || sm.size()==0|| (! sm.containsKey(key))) {
			
		}else
		{
			Object o = sm.get(key);
			if (o!=null) {
				
				i =  (Integer)o;
			}
		}
		return i;
	}
	
	
	
	public static Date toDate( SortedMap sm, String key )
	{
		Date date = null;
		if (sm==null || sm.size()==0|| (! sm.containsKey(key))) {
			
		}else
		{
			Object o = sm.get(key);
			if (o!=null) {
				date = (Date)o;
			}
		}
		return date;
	}
	
	public static long toLong( SortedMap sm, String key )
	{
		long l = -1;
		if (sm==null || sm.size()==0|| (! sm.containsKey(key))) {
			
		}else
		{
			Object o = sm.get(key);
			if (o!=null) {
				//Cannot cast from Object to int
				l = Long.valueOf(0);
			}
		}
		return l;
	}
	public static String toString( SortedMap sm, String key )
	{
		String s = null;
		if (sm==null || sm.size()==0|| (! sm.containsKey(key))) {
			
		}else
		{
			Object o = sm.get(key);
			if (o!=null) {
				s = o.toString();
			}
		}
		return s;
	}
	
	
	public static List<String> toList( SortedMap sm, String key )
	{
		List<String> s = new ArrayList<String>();
		if (sm==null || sm.size()==0|| (! sm.containsKey(key))) {
			
		}else
		{
			String  o = (String) sm.get(key);
			
			if (o!=null) {
				
				s.add(o);
			}
		}
		 return s;
	}

}
