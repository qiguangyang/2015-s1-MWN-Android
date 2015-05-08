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
import com.watchdog.entity.EmerTaskEntity;
import com.watchdog.entity.PatientEntity;
import com.watchdog.service.EmerTaskManager;
import com.watchdog.service.PatientManager;

public class EmerTaskJsonAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EmerTaskJsonAction.class);

	private EmerTaskManager emerTaskManager;
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

	public void setEmerTaskManager(EmerTaskManager emerTaskManager) {
		this.emerTaskManager = emerTaskManager;
	}

	public void setPatientManager(PatientManager patientManager) {
		this.patientManager = patientManager;
	}
	
	public void getAllTasks() {
		List<EmerTaskEntity> etList = emerTaskManager.getAllTasks();

		JSONArray jsonArray = getJsonArray(etList);
		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getRelatedTasks(Integer caregiverId) {
		List<EmerTaskEntity> etList = emerTaskManager.getRelatedTasks(caregiverId);
		
		JSONArray jsonArray = getJsonArray(etList);
		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(jsonArray.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private JSONArray getJsonArray(List<EmerTaskEntity> etList) {
		JSONArray jsonArray = new JSONArray();
		
		for (EmerTaskEntity et : etList) {
			PatientEntity pe = patientManager.getPatientById(et.getPatientId());
			JSONObject jo = new JSONObject();
			jo.put("patientName",pe.getFirstname() + " " +  pe.getLastname());
			jo.put("location", et.getLocation());
			jo.put("status", et.getStatus());
			jsonArray.add(0, jo);
		}
		return jsonArray;
	}
}
