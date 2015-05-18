package com.watchdog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LOCATION")
public class LocationEntity {
     
    @Id
    @Column(name="ID")
    @GeneratedValue
    private Integer id;
     
    @Column(name="NAME")
    private String name;
 
    @Column(name="LEVEL")
    private String level;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
    
    
     
}
