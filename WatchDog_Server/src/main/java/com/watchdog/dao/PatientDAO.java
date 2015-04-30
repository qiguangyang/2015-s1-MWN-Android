package com.watchdog.dao;

import java.util.List;

import com.watchdog.entity.PatientEntity;

public interface PatientDAO 
{
	//This method will be called when a patient object is added
    public void addPatient(PatientEntity patient);
    //Get patients by name
    public List<PatientEntity> getPatient(String name);
    //This method return list of patients in database
    public List<PatientEntity> getAllPatients();
    //Deletes a patient by it's id
    public void deletePatient(Integer patientId);
}