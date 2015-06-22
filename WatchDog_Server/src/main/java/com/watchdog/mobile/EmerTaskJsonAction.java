package com.watchdog.mobile;

import java.io.IOException;
import java.text.DateFormat;
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

	private int caregiverId;
	private int taskId;
	private String status;
	
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
	
	public void setCareGiverId(Integer id) {
		this.caregiverId = id;
	}
	
	public void setTaskId(Integer id) {
		this.taskId = id;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void getAllTasks() {
		List<EmerTaskEntity> etList = emerTaskManager.getAllTasks();

		JSONArray jsonArray = getJsonArray(etList);
		returnJsonStr(jsonArray.toString());
	}
	
	public void getRelatedTasks() {
		List<EmerTaskEntity> etList = emerTaskManager.getRelatedTasks(caregiverId);
		
		JSONArray jsonArray = getJsonArray(etList);
		returnJsonStr(jsonArray.toString());

	}
	
	public void getTaskByStatus(String status) {
		
	}
	
	public void getTaskById() {
		EmerTaskEntity te = emerTaskManager.getTaskById(taskId);
		JSONObject jo = null;
		if (te!=null) {
			jo = getJsonObject(te);
		}
		returnJsonStr(jo.toString());
	}
	
	public void getTaskByStatus() {
		List<EmerTaskEntity> etList = emerTaskManager.getTaskByStatus(status);
		JSONArray jsonArray = getJsonArray(etList);
		returnJsonStr(jsonArray.toString());
	}
	
	public void updateTaskStatus() {
		String result = emerTaskManager.updateTaskStatus(taskId, status) ? "SUCCESS" : "FAILURE";
		
		JSONObject jo = new JSONObject();
		jo.put("updateResult", result);
		
		returnJsonStr(jo.toString());
	}
	
	private JSONArray getJsonArray(List<EmerTaskEntity> etList) {
		JSONArray jsonArray = new JSONArray();
		
		for (EmerTaskEntity te : etList) {
			JSONObject jo = getJsonObject(te);
			jsonArray.add(0, jo);
		}
		return jsonArray;
	}
	
	private JSONObject getJsonObject(EmerTaskEntity te) {
		PatientEntity pe = patientManager.getPatientById(te.getPatientId());
		JSONObject jo = new JSONObject();
		jo.put("taskId", te.getId());
		jo.put("patientName",pe.getFirstname() + " " +  pe.getLastname());
		jo.put("location", te.getLocation());
		jo.put("destination", te.getDestination());
		jo.put("status", te.getStatus());
		jo.put("startTime", te.getStarttime().toString());
		jo.put("relatedCgId", te.getRelatedCareGiverId());
		return jo;
	}
	
	private void returnJsonStr(String str) {
		try {
			this.response.setCharacterEncoding("UTF-8");
			this.response.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
