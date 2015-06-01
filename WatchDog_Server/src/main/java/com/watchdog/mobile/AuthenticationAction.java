package com.watchdog.mobile;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;
import com.watchdog.service.CaregiverManager;

public class AuthenticationAction extends ActionSupport implements SessionAware, ServletRequestAware, ServletResponseAware {

	private static final long serialVersionUID = 662534553255137198L;

	private Map<String, Object> sessionMap;
	private HttpServletRequest request;
	private HttpServletResponse response;

	private String userName;
	private String password;

	private CaregiverManager caregiverManager;
	
	@Override
	public void setSession(Map<String, Object> sessionMap) {
		this.sessionMap = sessionMap;
	}
	
	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setCaregiverManager(CaregiverManager cgManager) {
		this.caregiverManager = cgManager;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void login() {
		String loggedUserName = null;
		JSONObject result;
		
		if (sessionMap.containsKey("userName")) {
			loggedUserName = (String) sessionMap.get("userName");
		}

		Map<String, String> authInfo = caregiverManager.getAuthInfo(userName);
		if (authInfo == null) {
			result = getAuthInfoJSON(false);
			returnJsonStr(result.toString());
			return;
		}
		
		String authName = authInfo.get("username");
		String authPwd = authInfo.get("password");
		String authId = authInfo.get("userid");

		if (loggedUserName != null && loggedUserName.equals(authName)) {
			result = getAuthInfoJSON(true);
			returnJsonStr(result.toString());
			return;
		}

		if (userName != null && userName.equals(authName) && password != null
				&& password.equals(authPwd)) {
			sessionMap.put("userName", userName);
			sessionMap.put("userId", authId);
			result = getAuthInfoJSON(true);
			returnJsonStr(result.toString());
			return;
		}
		
		result = getAuthInfoJSON(false);
		returnJsonStr(result.toString());
		return;
	}

	public void logout() {
		if (sessionMap.containsKey("userName")) {
			sessionMap.remove("userName");
			sessionMap.remove("userId");
		}
		JSONObject jo = new JSONObject();
		jo.put("result", "SUCCESS");
		returnJsonStr(jo.toString());
		return;
	}
	
	private JSONObject getAuthInfoJSON(boolean success) {
		JSONObject jo = new JSONObject();
		if (success) {
			jo.put("result", "SUCCESS");
			jo.put("userName", sessionMap.get("userName"));
			jo.put("userId", sessionMap.get("userId"));
		} else {
			jo.put("result", "FAILURE");
		}
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
