package au.edu.adelaide.cs.mwn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class TestRun extends Thread{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CallTrack ct = new CallTrack();
		ct.start();
		try {
			ct.join();
			//sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		
//	    while (true) {
//	    	try {
//				sleep(2000);
//			} catch (InterruptedException e) {
//				//TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    	boolean m = loadvaluefromrl("Stringurl");
//			if (!m) {
//				break;
//			}
//			
//		}
		//ct.stopme();
		
		System.out.println("END");
	}
	
	public static boolean loadvaluefromrl(String url){
		boolean value = true;
		try {
			URL u = new URL(url);
			URLConnection urlc = u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
                    urlc.getInputStream()));
			String get = in.readLine();
			if (get.equals("STOP")) {
				value = false;
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
		
	}

}
