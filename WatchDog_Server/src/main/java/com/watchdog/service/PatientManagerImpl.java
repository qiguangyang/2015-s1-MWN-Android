package com.watchdog.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.watchdog.dao.PatientDAO;
import com.watchdog.entity.PatientEntity;

public class PatientManagerImpl implements PatientManager {

	private PatientDAO patientDAO;
    
  	public void setPatientDAO(PatientDAO patientDAO) {
  		this.patientDAO = patientDAO;
  	}
	
	@Override
	@Transactional
	public void addPatient(PatientEntity patient) {
		patientDAO.addPatient(patient);
	}
	
	@Override
	@Transactional
	public List<PatientEntity> getAllPatients() {
		return patientDAO.getAllPatients();
	}
	
		@Override
		@Transactional
    public PatientEntity getPatientById(Integer patientId) {
    	return patientDAO.getPatientById(patientId);
    }
	
	@Override
	@Transactional
	public void deletePatient(Integer patientId) {
		patientDAO.deletePatient(patientId);
	}

	@Override
	@Transactional
	public PatientEntity getPatientByTagId(String tagId) {
		return patientDAO.getPatientByTagId(tagId);
	}
}
