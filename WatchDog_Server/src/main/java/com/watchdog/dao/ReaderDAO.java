package com.watchdog.dao;

import com.watchdog.entity.ReaderEntity;

public interface ReaderDAO {

	public ReaderEntity getReaderByIp(String ip);
	
	public ReaderEntity getReaderByName(String name);
	
}
