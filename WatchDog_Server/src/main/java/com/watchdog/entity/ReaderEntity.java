package com.watchdog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="READER")
public class ReaderEntity {

	@Id
    @Column(name="READER_ID")
    @GeneratedValue
	private int id;
	
    @Column(name="READER_NAME")
	private String readerName;
    
    @Column(name="READER_IP")
	private String readerIP;
    
    @Column(name="READER_LEFT")
	private int left;
    
    @Column(name="READER_CENTER")
	private int center;
    
    @Column(name="READER_RIGHT")
	private int right;
    
    @Column(name="READER_LEVEL")
	private int level;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReaderName() {
		return readerName;
	}

	public void setReaderName(String readerName) {
		this.readerName = readerName;
	}

	public String getReaderIP() {
		return readerIP;
	}

	public void setReaderIP(String readerIP) {
		this.readerIP = readerIP;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getCenter() {
		return center;
	}

	public void setCenter(int center) {
		this.center = center;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
    
}
