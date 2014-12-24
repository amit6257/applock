package com.example.amitagarwal.applock.database;

import android.content.Context;
import android.util.Log;

import com.example.amitagarwal.applock.broadcastreceiver.Constants;
import com.example.amitagarwal.applock.utils.MyStringUtils;
import com.example.amitagarwal.applock.utils.ParseUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class SystemAppDao {

	private DatabaseHelper db;
	private Dao<SystemAppDataForDao,String> dao;

	public SystemAppDao(Context ctx)
	{
		try {
			DBManager dbManager = new DBManager();
			db = dbManager.getHelper(ctx);
			dao = db.getPasswordDataDao();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public List<SystemAppDataForDao> getAll()
	{
		try {
			return dao.queryForAll();
		} catch (SQLException e) {
			ParseUtils.sendParseException(e);
		}
		return null;
	}
	
	public int createInBulk(final ArrayList<SystemAppDataForDao> systemAppDataForDaos, final boolean forceInsert) {
		try {
			dao.callBatchTasks( new Callable<Void>() {
				public Void call() throws SQLException {
					for (SystemAppDataForDao systemAppDataForDao : systemAppDataForDaos) {
						int createResult = create(systemAppDataForDao,forceInsert);
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
	public int create(SystemAppDataForDao passwordDataForDao, boolean forceInsert)
	{
		if (forceInsert) {
			try {
				MyStringUtils.print("mmmmmmmmmmmmm");
				CreateOrUpdateStatus status = dao.createOrUpdate(passwordDataForDao);
				return status.getNumLinesChanged();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (getPasswordDataForDaoById(passwordDataForDao.getPid()) == null) {
					return dao.create(passwordDataForDao);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int update(SystemAppDataForDao systemAppDataForDao)
	{
		try {
			return dao.update(systemAppDataForDao);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}
	
	public int delete(SystemAppDataForDao passwordDataForDao)
	{
		try {
			return dao.delete(passwordDataForDao);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return 0;
	}

	public SystemAppDataForDao getPasswordDataForDaoById(String pid) {
		try {
			return dao.queryForId(pid);
		} catch (SQLException e) {
			// TODO: Exception Handling
			e.printStackTrace();
		}
		return null;
	}

}
