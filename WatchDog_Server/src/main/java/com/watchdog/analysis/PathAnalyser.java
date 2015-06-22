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

import com.opensymphony.xwork2.ActionSupport;
import com.watchdog.entity.EmerTaskEntity;
import com.watchdog.entity.PathRecordEntity;
import com.watchdog.entity.PatientEntity;
import com.watchdog.entity.ReaderEntity;
import com.watchdog.service.CaregiverManager;
import com.watchdog.service.EmerTaskManager;
import com.watchdog.service.LocationManager;
import com.watchdog.service.PatientManager;
import com.watchdog.service.ReaderManager;

public class PathAnalyser extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EmerTaskManager emerTaskManager;
	private PatientManager patientManager;
	private ReaderManager readerManager;
	private LocationManager locationManager;
	private CaregiverManager caregiverManager;
	
	private String XmppURL = "http://127.0.0.1:7070/notification.do"; // XMPP notification URL
	
	public void setEmerTaskManager(EmerTaskManager emerTaskManager) {
		this.emerTaskManager = emerTaskManager;
	}

	public void setPatientManager(PatientManager patientManager) {
		this.patientManager = patientManager;
	}
	
	public void setLocationManager(LocationManager locationManager) {
		this.locationManager = locationManager;
	}
	
	public void setReaderManager(ReaderManager readerManager) {
		this.readerManager = readerManager;
	}
	
	public void setCaregiverManager(CaregiverManager caregiverManager) {
		this.caregiverManager = caregiverManager;
	}
	
	public boolean evaluate (PathRecordEntity path) {
		PatientEntity pe = patientManager.getPatientByTagId(path.getTagId());
		ReaderEntity r = readerManager.getReaderByIp(path.getReader());
		if (pe != null ) {
			String loc = pe.getForbidden();
			List<String> locList = Arrays.asList(loc.split(";"));
			String appKey = caregiverManager.getAppKeyById(pe.getCgId());
			String patientName = pe.getFirstname() + " " + pe.getLastname();
			
			int des = 0;
			if (path.getDirection() == -1) {
				notifyMsg1(patientName, appKey);
			} else if (path.getDirection() == 1) {
				int o = path.getOrientation();
				switch (o) {
				case 0:
					des = r.getLeft();
					break;
				case 1:
					des = r.getCenter();
					break;
				case 2:
					des = r.getRight();
					break;
				}

				if (locList.contains(String.valueOf(des))) {
					EmerTaskEntity et = new EmerTaskEntity();
					et.setPatientId(pe.getId());
					et.setStatus("RED");
					et.setLocation(locationManager.getLocationById(r.getLocation()).getName());
					et.setDestination(locationManager.getLocationById(des).getName());
					et.setRelatedCareGiverId(pe.getCgId());
					emerTaskManager.addTask(et);
					notifyMsg2(patientName, appKey, et);
				}
			}
		}
		return false;
	}
	
	private void notifyMsg1(String patient, String appKey) {
		String title = "Patient going out.";
		String message = patient + " is going out of the room";
		notify(appKey, title, message, "");
	}
	
	private void notifyMsg2(String patient, String appKey, EmerTaskEntity et) {
		String title = "Emergency Task";
		String message = patient + " is going from " + et.getLocation() + " to " + et.getDestination() + ".";
		String uri = "hahahahahha";
		notify(appKey, title, message, uri);
	}
	
	private void notify(String appKey, String title, String message, String uri) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(XmppURL);
 
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("action", "send"));
		urlParameters.add(new BasicNameValuePair("appKey", appKey));
		urlParameters.add(new BasicNameValuePair("title", title));
		urlParameters.add(new BasicNameValuePair("message", message));
		urlParameters.add(new BasicNameValuePair("uri", uri));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
