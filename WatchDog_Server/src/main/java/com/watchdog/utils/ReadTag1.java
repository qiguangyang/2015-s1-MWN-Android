package com.watchdog.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

public class ReadTag1 implements TagReportListener {

	private ImpinjReader reader;
	private boolean flag = true;

	public List<TestTag> tts;

	public ReadTag1() {
		this.tts = new ArrayList<TestTag>();
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

//			settings.getAutoStart().setMode(AutoStartMode.Periodic);
//			settings.getAutoStart().setPeriodInMs(100);
//			settings.getAutoStop().setMode(AutoStopMode.Duration);
//			settings.getAutoStop().setDurationInMs(1000);

			ReportConfig report = settings.getReport();
			report.setIncludeAntennaPortNumber(true);
			settings.getReport().setIncludePeakRssi(true);
			settings.getReport().setIncludeDopplerFrequency(true);
			settings.getReport().setIncludeChannel(true);
			settings.getReport().setIncludeFirstSeenTime(true);
			settings.getReport().setIncludeLastSeenTime(true);
			report.setMode(ReportMode.WaitForQuery);

			settings.setReaderMode(ReaderMode.AutoSetDenseReader);

			// set some special settings for antenna 1
			AntennaConfigGroup antennas = settings.getAntennas();
			antennas.disableAll();
			antennas.enableById(new short[] { 1, 2, 3, 4 });
			antennas.getAntenna((short) 1).setIsMaxRxSensitivity(true);
			antennas.getAntenna((short) 1).setIsMaxTxPower(true);
			antennas.getAntenna((short) 1).setTxPowerinDbm(20.0);
			antennas.getAntenna((short) 1).setRxSensitivityinDbm(-70);
			antennas.getAntenna((short) 2).setIsMaxRxSensitivity(true);
			antennas.getAntenna((short) 2).setIsMaxTxPower(true);
			antennas.getAntenna((short) 2).setTxPowerinDbm(20.0);
			antennas.getAntenna((short) 2).setRxSensitivityinDbm(-70);
			antennas.getAntenna((short) 3).setIsMaxRxSensitivity(true);
			antennas.getAntenna((short) 3).setIsMaxTxPower(true);
			antennas.getAntenna((short) 3).setTxPowerinDbm(20.0);
			antennas.getAntenna((short) 3).setRxSensitivityinDbm(-70);
			antennas.getAntenna((short) 4).setIsMaxRxSensitivity(true);
			antennas.getAntenna((short) 4).setIsMaxTxPower(true);
			antennas.getAntenna((short) 4).setTxPowerinDbm(20.0);
			antennas.getAntenna((short) 4).setRxSensitivityinDbm(-70);

			reader.setTagReportListener(this);

			System.out.println("Applying Settings");
			reader.applySettings(settings);

			System.out.println("Starting");
			reader.start();
			
			while (flag) {
				Thread.sleep(1000);
				reader.queryTags();
			}
			
			System.out.println("Press Enter to exit.");

			Scanner s = new Scanner(System.in);
			s.nextLine();

			reader.stop();
			reader.disconnect();
			s.close();
		} catch (OctaneSdkException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace(System.out);
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
				
//				System.out.print(rssi);
				String path = "Data/datas/" + epc + ".csv";
				File file = new File(path);

				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
				fw.append(time);
				fw.append(',');
				fw.append(rssi);
				fw.append(',');
				fw.append(atn);
				fw.append('\n');
				fw.flush();
				fw.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
