package com.example.amitagarwal.applock.database;

import android.content.Context;
import android.util.Log;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.ParseUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class LockedAppsDao {

	private DatabaseHelper db;
	private Dao<LockedAppDataForDao,String> packageDao;

	public LockedAppsDao(Context ctx)
	{
		try {
			DBManager dbManager = new DBManager();
			db = dbManager.getHelper(ctx);
			packageDao = db.getLockedAppDataDao();
		} catch (SQLException e) {
			ParseUtils.sendParseException(e);
		}

	}

	public int create(LockedAppDataForDao sampleDataForDao, boolean forceInsert)
	{
		if (forceInsert) {
			try {
				CreateOrUpdateStatus status = packageDao.createOrUpdate(sampleDataForDao);
				return status.getNumLinesChanged();
			} catch (SQLException e) {
				ParseUtils.sendParseException(e);
			}
		} else {
			try {
				if (getSampleDataForDaoById(sampleDataForDao.getPid()) == null) {
					return packageDao.create(sampleDataForDao);
				}
			} catch (SQLException e) {
				ParseUtils.sendParseException(e);
			}
		}
		return 0;
	}

	public int update(LockedAppDataForDao sampleDataForDao)
	{
		try {
			return packageDao.update(sampleDataForDao);
		} catch (SQLException e) {			
			ParseUtils.sendParseException(e);
		}
		return 0;
	}
	public int delete(LockedAppDataForDao packageDataForDao)
	{
		try {
			return packageDao.delete(packageDataForDao);
		} catch (SQLException e) {
			ParseUtils.sendParseException(e);
		}
		return 0;
	}

	public List<LockedAppDataForDao> getAll()
	{
		try {
			return packageDao.queryForAll();
		} catch (SQLException e) {
			ParseUtils.sendParseException(e);
		}
		return null;
	}

	public LockedAppDataForDao getSampleDataForDaoById(String pid) {
		try {
			return packageDao.queryForId(pid);
		} catch (SQLException e) {
			ParseUtils.sendParseException(e);
		}
		return null;
	} 

	public List<LockedAppDataForDao> getFlipkartProductInfoList(ArrayList<String> pids)
	{
		try {
			return packageDao.queryBuilder().where().in("pid",pids).query();
		} catch (SQLException e) {
			ParseUtils.sendParseException(e);
		} 
		return null;
	}

	public int createInBulk(final ArrayList<LockedAppDataForDao> sampleDataForDaos, final boolean forceInsert) {
		try {
			packageDao.callBatchTasks( new Callable<Void>() {
				public Void call() throws SQLException {
					for (LockedAppDataForDao sampleDataForDao : sampleDataForDaos) {
						int createResult = create(sampleDataForDao,forceInsert);
						if(createResult == 0)
							Log.d(Constants.TAG,"ERRORRRRRRRRRRRRRRRRRRRRRRRRRRRR row creation result  = 0..call batch task fn");
					}
					return null;
				}
			});
		} catch (Exception e) {
			ParseUtils.sendParseException(e);
		}
		return 0;
	}

	public void deleteAll() {
		try {
			packageDao.callBatchTasks( new Callable<Void>() {
				public Void call() throws SQLException {
					List<LockedAppDataForDao> sampleDataForDaos = getAll();
					if (sampleDataForDaos != null) {
						for (int i=0;i<sampleDataForDaos.size();i++) {
							delete(sampleDataForDaos.get(i));
						}
					}
					return null;
				}
			});
		} catch (Exception e) {
			ParseUtils.sendParseException(e);
		}
		return;
	}
}