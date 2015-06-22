package com.watchdog.utils;

import java.io.DataInputStream;
import java.net.Socket;

public class SpeedwayConnectClient {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		// The IP address or hostname of your reader
        final String READER_HOSTNAME = "127.0.0.1";
        // The TCP port specified in Speedway Connect
        final int READER_PORT = 14151;
         
        try
        {
            // Create a TCP socket connection to the reader
            Socket s = new Socket(READER_HOSTNAME, READER_PORT);
            // Create a BufferedReader object from the socket connection
            DataInputStream dis = new DataInputStream(s.getInputStream());
//            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

            // Receive data in an infinite loop
            while (true)
            {
                // Read one line at a time
                String line = dis.readUTF();

                // Print it to the screen
                System.out.println(line);
            }
        }
        catch (Exception e)
        {
            // An error has occurred
            System.out.println(e.getMessage());
        }
	}
}
