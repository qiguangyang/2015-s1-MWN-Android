package com.watchdog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.watchdog.dao.CaregiverDAO;
import com.watchdog.entity.CaregiverEntity;

public class CaregiverManagerImpl implements CaregiverManager {

	private CaregiverDAO caregiverDAO;
    
	public void setCaregiverDAO(CaregiverDAO cgDAO) {
		this.caregiverDAO = cgDAO;
	}

	@Override
	@Transactional
	public Map<String, String> getAuthInfo(String name) {
		List<CaregiverEntity> list = caregiverDAO.getAuthInfo(name);
		if (!list.isEmpty()) {
			CaregiverEntity cg = list.get(0);
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", cg.getUsername());
			map.put("password", cg.getPassword());
			map.put("userid", cg.getId().toString());
			return map;
		}
	    
		return null;
	}

	@Override
	@Transactional
	public Integer getUserIdByName(String name) {
		return null;
	}

}
