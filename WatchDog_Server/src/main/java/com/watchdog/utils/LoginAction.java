package com.watchdog.utils;

import java.util.Date;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {

	private static final long serialVersionUID = -2771144361483938849L;
	private String userId;
	private String passwd;

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String execute() throws Exception {
		if ("admin".equals(userId) && "password".equals(passwd)) {
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("logined", "true");
			session.put("context", new Date());
			return SUCCESS;
		}
		return ERROR;
	}

	public String logout() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		session.remove("logined");
		session.remove("context");
		return SUCCESS;
	}
}
