package com.watchdog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PATIENT")
public class PatientEntity {
     
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
     
    @Column(name="FIRSTNAME")
    private String firstname;
 
    @Column(name="LASTNAME")
    private String lastname;
 
    @Column(name="EMERGENCY_CONTACT")
    private String contact;
     
    @Column(name="FORBIDDEN_AREA")
    private String forbidden;
    
    @Column(name="TAG_ID")
    private String tagId;
    
    @Column(name="RELATED_CG_ID")
    private int cgId;
    
    public String getContact() {
        return contact;
    }
    public String getForbidden() {
        return forbidden;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public void setForbidden(String forbidden) {
        this.forbidden = forbidden;
    }
    public String getFirstname() {
        return firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public int getCgId() {
		return cgId;
	}
	public void setCgId(int cgId) {
		this.cgId = cgId;
	}
}