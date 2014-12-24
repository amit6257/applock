package com.example.amitagarwal.applock.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "lockedAppDb")
public class LockedAppDataForDao {

	@DatabaseField(id=true, columnName="pid")
	private String pid;

	@DatabaseField(columnName="appName")
	private String  appName;

	@DatabaseField(columnName="isSystemApp")
	private boolean isSystemApp;

	public LockedAppDataForDao(String pid, String appName,boolean isSystemApp) {  
		setPid(pid);
		setAppname(appName);
		setSystemApp(isSystemApp);
	}

	public LockedAppDataForDao() {
	}

	public String getAppname() {
		return appName;
	}

	public void setAppname(String appName) {
		this.appName = appName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public boolean isSystemApp() {
		return isSystemApp;
	}

	public void setSystemApp(boolean isSystemApp) {
		this.isSystemApp = isSystemApp;
	}
}