package com.example.amitagarwal.applock.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "thirdPartyAppDb")
public class ThirdPartyAppDataForDao {

	@DatabaseField(id=true, columnName="pid")
	private String pid;

	@DatabaseField(columnName="appName")
	private String  appName;

	public ThirdPartyAppDataForDao(String pid, String appName) {  
		setPid(pid);
		setAppname(appName);
	}

	public ThirdPartyAppDataForDao() {
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
}