package com.watchdog.service;

import com.watchdog.entity.ReaderEntity;

public interface ReaderManager {
	public ReaderEntity getReaderByIp(String ip);
	
	public ReaderEntity getReaderByName(String name);
}
