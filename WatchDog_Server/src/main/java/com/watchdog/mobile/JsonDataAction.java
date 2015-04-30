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

	public void getAllPatients() {
		JSONArray jsonArray = new JSONArray();
		List<PatientEntity> pList = patientManager.getAllPatients();

		for (PatientEntity p : pList) {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("patientID", p.getId());
			jsonObject.put("patientName", p.getFirstname() + " " +  p.getLastname());
			jsonObject.put("contactNum", p.getContact());
			jsonObject.put("forbiddenArea", p.getForbidden());
			jsonArray.add(0, jsonObject);
		}

		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
