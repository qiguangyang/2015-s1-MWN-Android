package com.watchdog.analysis;

import com.watchdog.dao.LocationDAO;
import com.watchdog.dao.ReaderDAO;
import com.watchdog.entity.EmerTaskEntity;
import com.watchdog.entity.PathRecordEntity;
import com.watchdog.entity.PatientEntity;
import com.watchdog.entity.ReaderEntity;
import com.watchdog.service.EmerTaskManager;
import com.watchdog.service.PatientManager;

public class PathAnalyser {
	
	private EmerTaskManager emerTaskManager;
	private PatientManager patientManager;
	private ReaderDAO readerDao;
	private LocationDAO locationDao;
	
	private String url = ""; // XMPP notification URL
	
	public void setEmerTaskManager(EmerTaskManager emerTaskManager) {
		this.emerTaskManager = emerTaskManager;
	}

	public void setPatientManager(PatientManager patientManager) {
		this.patientManager = patientManager;
	}
	
	public boolean evaluate (PathRecordEntity path) {
		PatientEntity pe = patientManager.getPatientByTagId(path.getTagId());
		ReaderEntity r = readerDao.getReaderByIp(path.getReader());
		if (pe != null ) {
			String loc = pe.getForbidden();
			String des = "";
			if (path.getDirection() == 1) {
				int o = path.getOrientation();
				switch (o) {
				case 0:
					des = locationDao.getLocationById(r.getLeft()).getName();
				case 1:
					des = locationDao.getLocationById(r.getCenter()).getName();
				case 2:
					des = locationDao.getLocationById(r.getRight()).getName();
				}

				if (loc.contains(des)) {
					EmerTaskEntity et = new EmerTaskEntity();
					et.setPatientId(pe.getId());
					et.setStatus("RED");
					et.setLocation(des);
					et.setRelatedCareGiverId(1); //TODO cgID
					emerTaskManager.addTask(et);
					notifyDevice();
				}
			}
		}
		return false;
	}
	
	private void notifyDevice() {
		
	}
	
}
