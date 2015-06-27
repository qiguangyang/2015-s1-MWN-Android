package com.watchdog.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.watchdog.analysis.PathAnalyser;
import com.watchdog.entity.PathRecordEntity;
import com.watchdog.entity.PatientEntity;
import com.watchdog.service.PatientManager;

public class EditPatientAction extends ActionSupport implements Preparable {
	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(EditPatientAction.class);
	private List<PatientEntity> patients;
	private PatientEntity patient;

	private PatientManager patientManager;
	private PathAnalyser pathAnalyser;
	
	private String reader;
	private String tagId;
	private String direction;
	private String orientation;
	
	public void setReader(String reader) {
		this.reader = reader;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

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

	public String test() {
		// rt.run();
		// PathRecordEntity path = new PathRecordEntity();
		// path.setDirection(1);
		// path.setOrientation(1);
		// path.setReader("READER1");
		// path.setTagId("301402422000020021D47F48");

		// pathAnalyser.evaluate(path);

		// ReadTag rt = new ReadTag();
		// CallTrack ct = new CallTrack();
		//
		// ct.start();
		// rt.run();

		return SUCCESS;
	}
	
	public void analyse() {
		PathRecordEntity path = new PathRecordEntity();
		path.setTagId(tagId);
		path.setReader(reader);
		path.setDirection(Integer.valueOf(direction));
		path.setOrientation(Integer.valueOf(orientation));
		
		pathAnalyser.evaluate(path);		
	}

	// This method will be called before any of Action method is invoked;
	// So some pre-processing if required.
	@Override
	public void prepare() throws Exception {
		patient = null;
	}

	public void setPatientManager(PatientManager patientManager) {
		this.patientManager = patientManager;
	}

	public void setPathAnalyser(PathAnalyser pathAnalyser) {
		this.pathAnalyser = pathAnalyser;
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
