package com.watchdog.utils;

import java.util.concurrent.LinkedBlockingQueue;

public class TrackingThread extends Thread implements Runnable {

	public LinkedBlockingQueue<String> lbq;

	public TrackingThread(String tagId) {
		this.setName(tagId);
		this.lbq = new LinkedBlockingQueue<String>();
	}

	public void run() {
		try {
			while (true) {
				if (!lbq.isEmpty()) {
					System.out.println(lbq.take());
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
