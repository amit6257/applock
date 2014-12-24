package com.example.amitagarwal.applock.utils;

import java.util.ArrayList;

public class ListUtils {
	
	public static <T> boolean isNullOrEmpty(ArrayList<T> list){
		return list == null || list.size() == 0; 
	}

}
