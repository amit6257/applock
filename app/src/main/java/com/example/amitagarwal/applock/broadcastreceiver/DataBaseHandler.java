package com.example.amitagarwal.applock.broadcastreceiver;

import android.content.Context;
import android.os.AsyncTask;

import com.example.amitagarwal.applock.database.LockedAppDataForDao;
import com.example.amitagarwal.applock.database.LockedAppsDao;
import com.example.amitagarwal.applock.database.SystemAppDao;
import com.example.amitagarwal.applock.database.SystemAppDataForDao;
import com.example.amitagarwal.applock.database.ThirdPartyAppDao;
import com.example.amitagarwal.applock.database.ThirdPartyAppDataForDao;
import com.example.amitagarwal.applock.utils.PackageUtils;
import com.example.amitagarwal.applock.utils.ParseUtils;

import java.util.ArrayList;

public abstract class DataBaseHandler {

	public void loadThirdPartyAppsFromDb(Context context) {
		GetThirdPartyAppsFromDBTask getApps = new GetThirdPartyAppsFromDBTask(context);
		getApps.execute();
	}

	private class GetThirdPartyAppsFromDBTask extends AsyncTask<Void, Void, Boolean>{

		//called during fragment launch restart
		ThirdPartyAppDao dao;
		ArrayList<ThirdPartyAppDataForDao> pkgsFromDbList = new ArrayList<ThirdPartyAppDataForDao>();
		Context context;

		public GetThirdPartyAppsFromDBTask(Context context){
			this.context = context;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try{
				dao = new ThirdPartyAppDao(context);
				//get list of packages from DB
				pkgsFromDbList = (ArrayList<ThirdPartyAppDataForDao>) dao.getAll();
				//also remove locked apps
				ArrayList<LockedAppDataForDao> lockedAppDataForDaos = PackageUtils.getLockedAppsList(context);
				PackageUtils.removeLockedThirdPartyApps(pkgsFromDbList,lockedAppDataForDaos);
				
				

			}catch(Exception e){
				ParseUtils.sendParseException(e);
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			resultReceived(pkgsFromDbList);
		}
	}
	
	//
	public void loadSystemAppsFromDb(Context context) {
		GetSystemAppsFromDBTask getApps = new GetSystemAppsFromDBTask(context);
		getApps.execute();
	}

	private class GetSystemAppsFromDBTask extends AsyncTask<Void, Void, Boolean>{

		//called during fragment launch restart
		SystemAppDao dao;
		ArrayList<SystemAppDataForDao> pkgsFromDbList = new ArrayList<SystemAppDataForDao>();
		Context context;

		public GetSystemAppsFromDBTask(Context context){
			this.context = context;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try{
				dao = new SystemAppDao(context);
				//get list of packages from DB
				pkgsFromDbList = (ArrayList<SystemAppDataForDao>) dao.getAll(); 
				//also remove locked apps
				ArrayList<LockedAppDataForDao> lockedAppDataForDaos = PackageUtils.getLockedAppsList(context);
				PackageUtils.removeLockedSystemApps(pkgsFromDbList, lockedAppDataForDaos);

			}catch(Exception e){
				ParseUtils.sendParseException(e);
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			resultReceived(pkgsFromDbList);
		}
	}
	//

	//removes uninstalled apps from DB,adds new apps to DB,and fires a callback after its work is done
	public void performDbTasksOnFirstLaunch(Context context){
		//put all new packages in DB
		UpdateAppsListDBTaskOnFirstLaunch updateProdInfoDb = new UpdateAppsListDBTaskOnFirstLaunch(context);
		updateProdInfoDb.execute();
	}
	private class UpdateAppsListDBTaskOnFirstLaunch extends AsyncTask<Void, Void, Boolean>{

		ArrayList<ThirdPartyAppDataForDao> thirdPartyAppDataForDaos = new ArrayList<ThirdPartyAppDataForDao>();
		ArrayList<SystemAppDataForDao> systemAppDataForDaos = new ArrayList<SystemAppDataForDao>();

		private Context context;

		public UpdateAppsListDBTaskOnFirstLaunch(Context context){
			this.context = context;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			PackageUtils.setAllPackages(context,thirdPartyAppDataForDaos,systemAppDataForDaos);

			ThirdPartyAppDao thirdPartyAppDao = new ThirdPartyAppDao(context);
			thirdPartyAppDao.createInBulk(thirdPartyAppDataForDaos, true);

			SystemAppDao systemAppDao = new SystemAppDao(context);
			systemAppDao.createInBulk(systemAppDataForDaos, true);

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			resultReceived(null);
		}
	}

	//removes uninstalled apps from DB,adds new apps to DB,and fires a callback after its work is done
	public void performDbTaskRefreshNewApps(Context context,boolean isRequestedThirdPartyApps){
		//put all new packages in DB
		UpdateAppsListDBTaskRefreshNewApps updateProdInfoDb = new UpdateAppsListDBTaskRefreshNewApps(context,isRequestedThirdPartyApps);
		updateProdInfoDb.execute();
	}

	private class UpdateAppsListDBTaskRefreshNewApps extends AsyncTask<Void, Void, Boolean>{

		private ArrayList<ThirdPartyAppDataForDao> thirdPartyAppDataForDaos = new ArrayList<ThirdPartyAppDataForDao>();
		private ArrayList<SystemAppDataForDao> systemAppDataForDaos = new ArrayList<SystemAppDataForDao>();

		private Context context;
		private ArrayList<ThirdPartyAppDataForDao> thirdPartyAppDataForDaosFromDb = new ArrayList<ThirdPartyAppDataForDao>();
		private ArrayList<SystemAppDataForDao> systemAppDataForDaosFromDb = new ArrayList<SystemAppDataForDao>();
		
		private boolean isRequestedThirdPartyApps;

		public UpdateAppsListDBTaskRefreshNewApps(Context context,boolean isRequestedThirdPartyApps){

			this.context = context;
			this.isRequestedThirdPartyApps = isRequestedThirdPartyApps;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			//set list of all packages
			PackageUtils.setAllPackages(context,thirdPartyAppDataForDaos,systemAppDataForDaos);
			
			//get existing apps from db and remove them from this list,finally add this list of new apps in db
			
			//add new third party apps to DB
			thirdPartyAppDataForDaosFromDb = PackageUtils.getThirdPartyPackagesFromDb(context);
			//remove packages that are already in db
			for(ThirdPartyAppDataForDao packageInfoFromDb:thirdPartyAppDataForDaosFromDb){

				for(ThirdPartyAppDataForDao inMemoryPackageInfo:thirdPartyAppDataForDaos){
					if(packageInfoFromDb.getPid().equalsIgnoreCase(inMemoryPackageInfo.getPid())){
						thirdPartyAppDataForDaos.remove(inMemoryPackageInfo);
						break;
					}
				}
			}

			ThirdPartyAppDao dao = new ThirdPartyAppDao(context);
			dao.createInBulk(thirdPartyAppDataForDaos, true);

			//-----------------------------------------------------------
			//AND now for system apps
			//add new system apps to DB
			systemAppDataForDaosFromDb = PackageUtils.getSystemPackagesFromDb(context);
			//remove packages that are already in db
			for(SystemAppDataForDao packageInfoFromDb:systemAppDataForDaosFromDb){

				for(SystemAppDataForDao inMemoryPackageInfo:systemAppDataForDaos){
					if(packageInfoFromDb.getPid().equalsIgnoreCase(inMemoryPackageInfo.getPid())){
						systemAppDataForDaos.remove(inMemoryPackageInfo);
						break;
					}
				}
			}			

			SystemAppDao systemAppDao = new SystemAppDao(context);
			systemAppDao.createInBulk(systemAppDataForDaos, true);

			//also remove locked apps
			ArrayList<LockedAppDataForDao> lockedAppDataForDaos = PackageUtils.getLockedAppsList(context);
			//get complete list of apps from db and return
			if(isRequestedThirdPartyApps){
				thirdPartyAppDataForDaosFromDb = PackageUtils.getThirdPartyPackagesFromDb(context);
				PackageUtils.removeLockedThirdPartyApps(thirdPartyAppDataForDaosFromDb,lockedAppDataForDaos);
			}else{
				systemAppDataForDaosFromDb = PackageUtils.getSystemPackagesFromDb(context);
				PackageUtils.removeLockedSystemApps(systemAppDataForDaosFromDb,lockedAppDataForDaos);
			}

			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			if(isRequestedThirdPartyApps){
				resultReceived(thirdPartyAppDataForDaosFromDb);
			}else{
				resultReceived(systemAppDataForDaosFromDb);
			}

		}
	}

	public abstract void resultReceived(Object obj);

	public void loadLockedAppsFromDb(Context context) {
		GetLockedAppsFromDBTask getLockedApps = new GetLockedAppsFromDBTask(context);
		getLockedApps.execute();		
	}
	private class GetLockedAppsFromDBTask extends AsyncTask<Void, Void, Boolean>{
		//called during fragment launch restart
		LockedAppsDao dao;
		ArrayList<LockedAppDataForDao> pkgsFromDbList = new ArrayList<LockedAppDataForDao>();
		Context context;

		public GetLockedAppsFromDBTask(Context context){
			this.context = context;
		}

		@Override
		protected Boolean doInBackground(Void... params) {

			try{
				dao = new LockedAppsDao(context);
				//get list of packages from DB
				pkgsFromDbList = (ArrayList<LockedAppDataForDao>) dao.getAll();

			}catch(Exception e){
				ParseUtils.sendParseException(e);
			}
			return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			resultReceived(pkgsFromDbList);
		}
	}
}
