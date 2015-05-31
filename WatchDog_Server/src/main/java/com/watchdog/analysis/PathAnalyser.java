package com.watchdog.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

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
	
	private String XmppURL = ""; // XMPP notification URL
	
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
			List<String> locList = Arrays.asList(loc.split(";"));
			
			int des = 0;
			if (path.getDirection() == 1) {
				int o = path.getOrientation();
				switch (o) {
				case 0:
					des = r.getLeft();
				case 1:
					des = r.getCenter();
				case 2:
					des = r.getRight();
				}

				if (locList.contains(String.valueOf(des))) {
					EmerTaskEntity et = new EmerTaskEntity();
					et.setPatientId(pe.getId());
					et.setStatus("RED");
					et.setLocation(locationDao.getLocationById(des).getName());
					et.setRelatedCareGiverId(et.getRelatedCareGiverId());
					emerTaskManager.addTask(et);
					notifyDevice(XmppURL);
				}
			}
		}
		return false;
	}
	
	private void notifyDevice(String url) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(url);
 
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("locale", ""));
 
		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
