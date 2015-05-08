package com.watchdog.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.watchdog.dao.PatientDAO;
import com.watchdog.entity.PatientEntity;

public class PatientManagerImpl implements PatientManager {
	//Patient dao injected by Spring context
    private PatientDAO patientDAO;
	
	//This method will be called when a patient object is added
	@Override
	@Transactional
	public void addPatient(PatientEntity patient) {
		patientDAO.addPatient(patient);
	}
	
	//This method return list of patients in database
	@Override
	@Transactional
	public List<PatientEntity> getAllPatients() {
		return patientDAO.getAllPatients();
	}
	
	//This method return the specific patient in database
		@Override
		@Transactional
    public PatientEntity getPatientById(Integer patientId) {
    	return patientDAO.getPatientById(patientId);
    }
	
	//Deletes a patient by it's id
	@Override
	@Transactional
	public void deletePatient(Integer patientId) {
		patientDAO.deletePatient(patientId);
	}
	
	//This setter will be used by Spring context to inject the dao's instance
	public void setPatientDAO(PatientDAO patientDAO) {
		this.patientDAO = patientDAO;
	}
}
