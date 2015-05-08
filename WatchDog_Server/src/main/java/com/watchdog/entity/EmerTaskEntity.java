package com.watchdog.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMERTASK")
public class EmerTaskEntity {
	@Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
     
    @Column(name="LOCATION")
    private String location;
 
    @Column(name="PATIENT_ID")
    private int patientId;
 
    @Column(name="TASK_STATUS")
    private String status;
    
    @Column(name="RELATED_CG_ID")
    private int relatedCareGiverId;
     
    @Column(name="START_TIME")
    private Date starttime;
    
    @Column(name="END_TIME")
    private Date endtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPatientId() {
		return patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getRelatedCareGiverId() {
		return relatedCareGiverId;
	}

	public void setRelatedCareGiverId(int relatedCareGiverId) {
		this.relatedCareGiverId = relatedCareGiverId;
	}

	public Date getStarttime() {
		return starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
    
    
}
