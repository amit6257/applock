package com.example.amitagarwal.applock.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.example.amitagarwal.applock.database.LockedAppDataForDao;
import com.example.amitagarwal.applock.database.LockedAppsDao;
import com.example.amitagarwal.applock.database.SystemAppDao;
import com.example.amitagarwal.applock.database.SystemAppDataForDao;
import com.example.amitagarwal.applock.database.ThirdPartyAppDao;
import com.example.amitagarwal.applock.database.ThirdPartyAppDataForDao;

import java.util.ArrayList;
import java.util.List;

public class PackageUtils {

	public static void setInstalledPackages(Context context, ArrayList<ThirdPartyAppDataForDao> thirdPartyAppDataForDaos, ArrayList<SystemAppDataForDao> systemAppDataForDaos) {

		List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(0);

		PackageInfo packageInfo;
		for(int i=0;i<packs.size();i++) {
			try{
				packageInfo = packs.get(i);
				if(packageInfo.packageName.equalsIgnoreCase(context.getPackageName()))
					continue;

				if(isPackageAllowed(packageInfo)){
					if(isSystemPackage(packageInfo)){
						systemAppDataForDaos.add(new SystemAppDataForDao(packageInfo.packageName,
								packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString(),"unknown"));
					}else{
						thirdPartyAppDataForDaos.add(new ThirdPartyAppDataForDao(packageInfo.packageName,
								packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString()));
					}	
				}				
			}catch(Exception e){
				ParseUtils.sendParseException(e);
			}
		}
	}

	private static boolean isPackageAllowed(PackageInfo pkgInfo) {
		//we actually want to remove list of system apps that are not accessible to open like com.android.systemui
		//so we have a hardcoded list of apps that we will call system apps 
		if((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
			if(! com.example.amitagarwal.applock.broadcastreceiver.Constants.SYSTEM_APPS.contains(pkgInfo.packageName)){
				return false;
			}
			return true;
		}	
		return true;
	}

	public static void setAllPackages(Context context, ArrayList<ThirdPartyAppDataForDao> thirdPartyAppDataForDaos, ArrayList<SystemAppDataForDao> systemAppDataForDaos) {

		setInstalledPackages(context,thirdPartyAppDataForDaos,systemAppDataForDaos);		
	}

	public static ArrayList<ThirdPartyAppDataForDao> getThirdPartyPackagesFromDb(Context context) {

		ThirdPartyAppDao thirdPartyAppDao = new ThirdPartyAppDao(context);
		ArrayList<ThirdPartyAppDataForDao> thirdPartyAppDataForDaos = (ArrayList<ThirdPartyAppDataForDao>) thirdPartyAppDao.getAll();

		return thirdPartyAppDataForDaos;
	}

	public static ArrayList<SystemAppDataForDao> getSystemPackagesFromDb(Context context) {

		SystemAppDao systemAppDao = new SystemAppDao(context);
		ArrayList<SystemAppDataForDao> systemAppDataForDaos = (ArrayList<SystemAppDataForDao>) systemAppDao.getAll();
		return systemAppDataForDaos;
	}

	private static boolean isSystemPackage(PackageInfo pkgInfo) {

		if((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
			if(com.example.amitagarwal.applock.broadcastreceiver.Constants.SYSTEM_APPS.contains(pkgInfo.packageName)){
				return true;
			}
			return false;
		}	
		return false;
	}

	public static ArrayList<LockedAppDataForDao> getLockedAppsList(Context ctx) {

		LockedAppsDao lockedAppsDao = new LockedAppsDao(ctx);
		return (ArrayList<LockedAppDataForDao>) lockedAppsDao.getAll();		
	}

	public static void removeLockedThirdPartyApps(ArrayList<ThirdPartyAppDataForDao> thirdPartyAppDataForDaosFromDb, 
			ArrayList<LockedAppDataForDao> lockedAppDataForDaos) {
		//remove packages that are already in db
		for(LockedAppDataForDao lockedApp:lockedAppDataForDaos){

			for(ThirdPartyAppDataForDao thirdPartyAppDataForDao:thirdPartyAppDataForDaosFromDb){
				if(lockedApp.getPid().equalsIgnoreCase(thirdPartyAppDataForDao.getPid())){
					thirdPartyAppDataForDaosFromDb.remove(thirdPartyAppDataForDao);
					break;
				}
			}
		}		
	}

	public static void removeLockedSystemApps(
			ArrayList<SystemAppDataForDao> systemAppDataForDaosFromDb, ArrayList<LockedAppDataForDao> lockedAppDataForDaos) {
		//remove packages that are already in db
		for(LockedAppDataForDao lockedApp:lockedAppDataForDaos){

			for(SystemAppDataForDao systemAppDataForDao:systemAppDataForDaosFromDb){
				if(lockedApp.getPid().equalsIgnoreCase(systemAppDataForDao.getPid())){
					systemAppDataForDaosFromDb.remove(systemAppDataForDao);
					break;
				}
			}
		}
	}
}