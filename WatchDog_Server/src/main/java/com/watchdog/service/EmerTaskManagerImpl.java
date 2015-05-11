package com.watchdog.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.watchdog.dao.EmerTaskDAO;
import com.watchdog.entity.EmerTaskEntity;

public class EmerTaskManagerImpl implements EmerTaskManager {

	//EmerTask dao injected by Spring context
    private EmerTaskDAO emerTaskDAO;
    
    //This setter will be used by Spring context to inject the dao's instance
  	public void setEmerTaskDAO(EmerTaskDAO emerTaskDAO) {
  		this.emerTaskDAO = emerTaskDAO;
  	}
	
	@Override
	@Transactional
	public void addTask(EmerTaskEntity emerTask) {
		emerTaskDAO.addTask(emerTask);
	}

	@Override
	@Transactional
	public List<EmerTaskEntity> getAllTasks() {
		return emerTaskDAO.getAllTasks();
	}

	@Override
	@Transactional
	public List<EmerTaskEntity> getRelatedTasks(int caregiverId) {
		return emerTaskDAO.getTaskByUserID(caregiverId);
	}

	@Override
	@Transactional
	public boolean updateTaskStatus(int taskId, String status) {
		return emerTaskDAO.updateStatus(taskId, status);
	}

}
