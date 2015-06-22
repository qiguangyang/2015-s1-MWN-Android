package com.watchdog.service;

import java.util.Map;

public interface CaregiverManager {
	
	public Map<String, String> getAuthInfo(String name);
	
	public Integer getUserIdByName(String name);
	
	public String getAppKeyById(int id);
}
