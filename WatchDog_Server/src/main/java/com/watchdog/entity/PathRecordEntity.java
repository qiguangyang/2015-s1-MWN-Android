package com.watchdog.entity;

public class PathRecordEntity {
	
	private String reader;
	
	private String tagId;

	// 0 indicates in, 1 indicates out
	private int direction;
	
	// 0 indicates left, 1 indicates middle, 2 indicates right
	private int orientation;

	public String getReader() {
		return reader;
	}

	public void setReader(String reader) {
		this.reader = reader;
	}

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getOrientation() {
		return orientation;
	}

	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	
	
}
