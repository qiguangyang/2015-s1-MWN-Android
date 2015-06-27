package com.watchdog.test;

import java.net.ServerSocket;
import java.net.Socket;

import com.watchdog.utils.ReadTagSocket;

public class testWhole {

	public static void main(String[] args) {

		testWhole server = new testWhole();
		server.init();
		// ReadTag rt = new ReadTag();
		// CallTrack ct = new CallTrack();
		//
		// ct.start();
		// rt.run();
	}

	public void init() {
		try {
			ServerSocket serverSocket = new ServerSocket(14151);
			while (true) {
				Socket client = serverSocket.accept();
				 new ReadTagSocket(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
