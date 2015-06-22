package com.watchdog.test;

import com.watchdog.analysis.PathAnalyser;
import com.watchdog.entity.PathRecordEntity;

public class TestGenerateEmerTask {

	public static void main(String[] args) {
		PathRecordEntity path = new PathRecordEntity();
		path.setDirection(-1);
		path.setOrientation(0);
		path.setReader("speedwayr-10-c0-e8.local");
		path.setTagId("301402422000020021D47F48");
		
		PathAnalyser pa = new PathAnalyser();
		pa.evaluate(path);
		
	}
}
