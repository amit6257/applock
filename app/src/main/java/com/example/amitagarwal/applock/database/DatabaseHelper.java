package com.example.amitagarwal.applock.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.amitagarwal.applock.utils.ParseUtils;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.parse.ParseObject;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "com_apps_applock.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 2;

	// the DAO object we use to access the SimpleData table
	private Dao<ThirdPartyAppDataForDao, String> thirdPartyAppDao = null;
	private RuntimeExceptionDao<ThirdPartyAppDataForDao, String> stdRuntimeDao = null;
	
	//password dao
	private Dao<SystemAppDataForDao, String> systemAppDao = null;
	private RuntimeExceptionDao<SystemAppDataForDao, String> passwordRuntimeDao = null;
	
	//lockedApp dao
	private Dao<LockedAppDataForDao, String> lockedAppDao = null;
	private RuntimeExceptionDao<LockedAppDataForDao, String> lockedAppRuntimeDao = null;
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {		
		try {			
			TableUtils.createTable(connectionSource, ThirdPartyAppDataForDao.class);
			TableUtils.createTable(connectionSource, SystemAppDataForDao.class);
			TableUtils.createTable(connectionSource, LockedAppDataForDao.class);
		}
		catch (SQLException e) {
			ParseUtils.sendParseException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		// drop the old databases
		try{
			ParseObject dbUpgrade = new ParseObject("db_upgrade");    	
			dbUpgrade.put("db_upgrade_new_v_2","newVs=2");
			dbUpgrade.saveInBackground();  
		}catch(Exception e){
			ParseUtils.sendParseException(e, "onUpgrade Exception");
		}
		String sql = "drop database " + DATABASE_NAME;
		db.execSQL(sql);
		// after we drop the old databases, we create the new ones
		onCreate(db, connectionSource);
	}

	/**
	 * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
	 * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
	 */
	public RuntimeExceptionDao<ThirdPartyAppDataForDao,String> getSampleTableRuntimeDataDao() {
		if (stdRuntimeDao == null) {
			stdRuntimeDao = getRuntimeExceptionDao(ThirdPartyAppDataForDao.class);
		}
		return stdRuntimeDao;
	} 


	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
		thirdPartyAppDao = null;
		systemAppDao = null;
	}

	/**
	 * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
	 * value.
	 * @throws java.sql.SQLException 
	 */
	public Dao<ThirdPartyAppDataForDao,String> getSampleDataDao() throws SQLException {
		if (thirdPartyAppDao == null) {
			thirdPartyAppDao = getDao(ThirdPartyAppDataForDao.class);
		}
		return thirdPartyAppDao;
	} 
	
	public Dao<SystemAppDataForDao,String> getPasswordDataDao() throws SQLException {
		if (systemAppDao == null) {
			systemAppDao = getDao(SystemAppDataForDao.class);
		}
		return systemAppDao;
	} 
	
	public RuntimeExceptionDao<SystemAppDataForDao,String> getPasswordTableRuntimeDataDao() {
		if (passwordRuntimeDao == null) {
			passwordRuntimeDao = getRuntimeExceptionDao(SystemAppDataForDao.class);
		}
		return passwordRuntimeDao;
	}

	public Dao<LockedAppDataForDao, String> getLockedAppDataDao()throws SQLException {
		if (lockedAppDao == null) {
			lockedAppDao = getDao(LockedAppDataForDao.class);
		}
		return lockedAppDao;
	}
	
	public RuntimeExceptionDao<LockedAppDataForDao,String> getLockedAppRuntimeDataDao() {
		if (lockedAppRuntimeDao == null) {
			lockedAppRuntimeDao = getRuntimeExceptionDao(LockedAppDataForDao.class);
		}
		return lockedAppRuntimeDao;
	}
}
