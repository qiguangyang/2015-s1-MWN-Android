package com.watchdog.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.watchdog.entity.PatientEntity;
import com.watchdog.service.PatientManager;

public class EditPatientAction extends ActionSupport implements Preparable	
{
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(EditPatientAction.class);
	private List<PatientEntity> patients;
	private PatientEntity patient;
	
	private PatientManager patientManager;

	public String listPatients() {
		logger.info("listPatients method called");
		patients = patientManager.getAllPatients();
		return SUCCESS;
	}

	public String addPatient() {
		logger.info("addPatient method called");
		patientManager.addPatient(patient);
		return SUCCESS;
	}

	public String deletePatient() {
		logger.info("deletePatient method called");
		patientManager.deletePatient(patient.getId());
		return SUCCESS;
	}
	
	//This method will be called before any of Action method is invoked;
	//So some pre-processing if required.
	@Override
	public void prepare() throws Exception {
		patient = null;
	}

	public void setPatientManager(PatientManager patientManager) {
		this.patientManager = patientManager;
	}

	public List<PatientEntity> getPatients() {
		return patients;
	}

	public void setPatients(List<PatientEntity> patients) {
		this.patients = patients;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}
}
