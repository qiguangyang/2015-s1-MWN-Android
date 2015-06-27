package com.watchdog.test;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class TestWriting implements Runnable {
	private Socket socket;
	private DataOutputStream dos;

	public TestWriting(Socket client) {
		socket = client;
		try {
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (true) {
			String s = new Date().toLocaleString();
			try {
				Thread.sleep(200);
				dos.writeUTF(s);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
