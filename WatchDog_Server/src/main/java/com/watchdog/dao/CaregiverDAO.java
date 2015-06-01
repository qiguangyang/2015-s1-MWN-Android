package com.watchdog.dao;

import java.util.List;

import com.watchdog.entity.CaregiverEntity;

public interface CaregiverDAO {
	public List<CaregiverEntity> getAuthInfo(String name);
}
