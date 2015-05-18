package com.watchdog.dao;

import java.util.List;

import com.watchdog.entity.EmerTaskEntity;

public interface EmerTaskDAO {

	public void addTask(EmerTaskEntity task);

	public List<EmerTaskEntity> getTaskByUserID(int userId);

	public List<EmerTaskEntity> getAllTasks();
	
	public EmerTaskEntity getTaskById(int taskId);
    
    public boolean updateStatus(Integer taskId, String status);
}
