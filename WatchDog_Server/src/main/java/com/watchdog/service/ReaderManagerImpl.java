package com.watchdog.service;

import com.watchdog.dao.ReaderDAO;
import com.watchdog.entity.ReaderEntity;

public class ReaderManagerImpl implements ReaderManager {

	private ReaderDAO readerDAO;

	// This setter will be used by Spring context to inject the dao's instance
	public void setReaderDAO(ReaderDAO readerDAO) {
		this.readerDAO = readerDAO;
	}

	@Override
	public ReaderEntity getReaderByIp(String ip) {
		return readerDAO.getReaderByIp(ip);
	}
	
	@Override
	public ReaderEntity getReaderByName(String name) {
		return readerDAO.getReaderByIp(name);
	}

}
