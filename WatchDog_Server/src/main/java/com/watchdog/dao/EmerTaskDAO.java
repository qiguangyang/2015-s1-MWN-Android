package com.watchdog.dao;

import java.util.List;

import com.watchdog.entity.EmerTaskEntity;

public interface EmerTaskDAO {
	//This method will be called when a patient object is added
    public void addTask(EmerTaskEntity task);
    //Get patients by name
    public List<EmerTaskEntity> getTaskByUserID(int userId);
    //This method return list of patients in database
    public List<EmerTaskEntity> getAllTasks();
    //Deletes a patient by it's id
    public void updateStatus(Integer patientId, String status);
}
