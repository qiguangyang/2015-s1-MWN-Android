package com.watchdog.mobile;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.watchdog.entity.PatientEntity;
import com.watchdog.service.PatientManager;

public class JsonDataAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(JsonDataAction.class);
	
	private PatientManager patientManager;

	private HttpServletRequest request;  
    private HttpServletResponse response;
    
    private int patientId;
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setPatientManager(PatientManager patientManager) {
		this.patientManager = patientManager;
	}
	
	public void setPatientId(int id) {
		this.patientId = id;
	}
	
	public void getAllPatients() {
		JSONArray jsonArray = new JSONArray();
		List<PatientEntity> pList = patientManager.getAllPatients();

		for (PatientEntity pe : pList) {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("patientID", pe.getId());
			jsonObject.put("patientName", pe.getFirstname() + " " +  pe.getLastname());
			jsonObject.put("contactNum", pe.getContact());
			jsonObject.put("forbiddenArea", pe.getForbidden());
			jsonArray.add(0, jsonObject);
		}

		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getPatientById() {
		PatientEntity pe = patientManager.getPatientById(patientId);
		
		JSONObject jo = new JSONObject();
		jo.put("patientId", pe.getId());
		jo.put("patientName", pe.getFirstname() + " " +  pe.getLastname());
		jo.put("contactNum", pe.getContact());
		jo.put("forbiddenArea", pe.getForbidden());
		
		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(jo.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
