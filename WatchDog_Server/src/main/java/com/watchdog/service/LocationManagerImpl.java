package com.watchdog.service;

import org.springframework.transaction.annotation.Transactional;

import com.watchdog.dao.LocationDAO;
import com.watchdog.entity.LocationEntity;

public class LocationManagerImpl implements LocationManager {

	private LocationDAO locationDAO;

	// This setter will be used by Spring context to inject the dao's instance
	public void setLocationDAO(LocationDAO locationDAO) {
		this.locationDAO = locationDAO;
	}

	@Override
	@Transactional
	public LocationEntity getLocationById(int id) {
		return locationDAO.getLocationById(id);
	}

}
