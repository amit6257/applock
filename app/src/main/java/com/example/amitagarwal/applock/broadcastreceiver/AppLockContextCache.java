package com.example.amitagarwal.applock.broadcastreceiver;

import java.util.HashMap;

public class AppLockContextCache {

	private static AppLockContextCache instance = new AppLockContextCache();
	private HashMap<String, Object> contextCache = new HashMap<String, Object>();
	private AppLockContextCache(){

	}

	public static AppLockContextCache instatnce(){
		return instance;
	}

	public void putItem(String key,Object value){
		contextCache.put(key, value);		
	}

	public Object getItem(String key){
		return contextCache.get(key);
	}

	public void clearValue(String key){
		if(contextCache.containsKey(key))
			contextCache.remove(key);
	}
}
