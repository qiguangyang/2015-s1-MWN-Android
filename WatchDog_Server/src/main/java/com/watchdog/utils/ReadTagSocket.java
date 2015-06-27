package com.watchdog.utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import com.impinj.octanesdk.AntennaConfigGroup;
import com.impinj.octanesdk.ImpinjReader;
import com.impinj.octanesdk.OctaneSdkException;
import com.impinj.octanesdk.ReaderMode;
import com.impinj.octanesdk.ReportConfig;
import com.impinj.octanesdk.ReportMode;
import com.impinj.octanesdk.Settings;
import com.impinj.octanesdk.Tag;
import com.impinj.octanesdk.TagReport;
import com.impinj.octanesdk.TagReportListener;

public class ReadTagSocket implements TagReportListener, Runnable {

	private ImpinjReader reader;
	private Socket socket;
	private DataOutputStream dos;

	public ReadTagSocket(Socket client) {
		socket = client;
		try {
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Thread(this).start();
	}

	public void run() {
		try {
			// String hostname = System.getProperty(SampleProperties.hostname);
			String hostname = "speedwayr-10-c0-e8.local";

			// if (hostname == null) {
			// throw new Exception("Must specify the '"
			// + SampleProperties.hostname + "' property");
			// }

			reader = new ImpinjReader();

			System.out.println("Connecting");
			reader.connect(hostname);

			Settings settings = reader.queryDefaultSettings();

			ReportConfig report = settings.getReport();
			report.setIncludeAntennaPortNumber(true);
			settings.getReport().setIncludePeakRssi(true);
			settings.getReport().setIncludeFirstSeenTime(true);
			report.setMode(ReportMode.Individual);

			settings.setReaderMode(ReaderMode.AutoSetDenseReader);

			// set some special settings for antenna 1
			AntennaConfigGroup antennas = settings.getAntennas();
			antennas.disableAll();
			antennas.enableById(new short[] { 1, 2, 3, 4 });

			reader.setTagReportListener(this);

			System.out.println("Applying Settings");
			reader.applySettings(settings);

			System.out.println("Starting");
			reader.start();

			System.out.println("Press Enter to exit.");

			Scanner s = new Scanner(System.in);
			s.nextLine();

			reader.stop();
			reader.disconnect();
			s.close();
			System.out.println("Disconnected.");
		} catch (OctaneSdkException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
		} finally {
			if (socket != null) {
				if (!socket.isClosed()) {
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	@Override
	public void onTagReported(ImpinjReader reader, TagReport report) {
		List<Tag> tags = report.getTags();

		try {
			String epc = "";
			String time = "";
			String rssi = "";
			String atn = "";

			for (Tag t : tags) {
				epc = t.getEpc().toString().replace(" ", "");

				Calendar cal = Calendar.getInstance();
				long milli = Long.valueOf(t.getFirstSeenTime().ToString());
				cal.setTimeInMillis(milli / 1000L);
				String hh = String.valueOf(cal.get(Calendar.HOUR));
				String mm = String.valueOf(cal.get(Calendar.MINUTE));
				String ss = String.valueOf(cal.get(Calendar.SECOND));
				time = hh + ":" + mm + ":" + ss;

				rssi = String.valueOf(t.getPeakRssiInDbm());
				atn = String.valueOf(t.getAntennaPortNumber());

				
				String s = epc + " " + time + " " + rssi + " " + atn;
				dos.writeUTF(s);
			}
		} catch (Exception e) {
			System.out.println("e");
			e.printStackTrace();
		}

	}

}
