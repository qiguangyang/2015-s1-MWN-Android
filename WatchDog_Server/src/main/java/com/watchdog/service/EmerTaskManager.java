package com.watchdog.service;

import java.util.List;

import com.watchdog.entity.EmerTaskEntity;

public interface EmerTaskManager {
	
	public void addTask(EmerTaskEntity emerTask);
    
    public List<EmerTaskEntity> getAllTasks();
    
    public List<EmerTaskEntity> getRelatedTasks(int caregiverId);
    
    public EmerTaskEntity getTaskById(int taskId);
    
    public boolean updateTaskStatus(int taskId, String status);
}
