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
	public EmerTaskEntity getTaskById(int taskId) {
		return emerTaskDAO.getTaskById(taskId);
	}

	@Override
	@Transactional
	public boolean updateTaskStatus(int taskId, String status) {	
		try {
			int result = emerTaskDAO.updateStatus(taskId, status);
			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<EmerTaskEntity> getTaskByStatus(String status) {
		return emerTaskDAO.getTaskByStatus(status);
	}

}
