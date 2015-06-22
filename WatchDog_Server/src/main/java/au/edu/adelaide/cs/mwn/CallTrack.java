package au.edu.adelaide.cs.mwn;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.opencsv.CSVReader;

public class CallTrack extends Thread {

	volatile boolean finish = true;

	public void run() {
		while (finish) {
			scanfiles();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void stopme() {
		finish = false;
	}

	public void scanfiles() {
		String rssifilesdir = "Data/datas";
		File rssifiles = new File(rssifilesdir);
		File[] rssidatas = rssifiles.listFiles();
		List<TrachThread> thlist = new ArrayList<TrachThread>();
		List<String> Runingfiles = new ArrayList<String>();
		if (rssidatas.length > 0) {
			for (int i = 0; i < rssidatas.length; i++) {

				if (!Runingfiles.contains(rssidatas[i].getName())) {
					TrachThread tt = new TrachThread(rssidatas[i].getName(), "Data/datas/" + rssidatas[i].getName());
					thlist.add(tt);
					Runingfiles.add(rssidatas[i].getName());
					tt.start();
				}
			}
		}

		// for (TrachThread trachThread : thlist) {
		// trachThread.start();
		// }
		//
		for (TrachThread trachThread : thlist) {
			try {
				trachThread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void excute(String path) {
		// String path = "Data/13.csv";
		MainTrack mt = new MainTrack();
		try {
			CSVReader reader = new CSVReader(new FileReader(path));
			String[] nextLine;
			nextLine = reader.readNext();
			String time = nextLine[0];
			Date dNow = null;
			SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss");
			try {
				dNow = ft.parse(time);
				dNow.setSeconds(dNow.getSeconds() + 1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			time = ft.format(dNow);
			String TagID = path.substring(11, path.length() - 4);
			int T = Integer.parseInt(time.split(":")[2]);
			// System.out.println(T);
			int i = 0;
			ArrayList<Integer> result = new ArrayList<Integer>();
			boolean excute = true;
			File f = new File("Data/" + TagID + "result.txt");
			while (excute) {

				if (i == 0) {

					f.delete();
				}
				result.add(mt.track(path, TagID, time, i));
				// System.out.println(result.toString());
				ReadWriteDataTxt rwTxt = new ReadWriteDataTxt();
				String[][] data;
				if (!f.exists()) {
					break;
				}
				data = rwTxt.readTxt("Data/" + TagID + "result.txt");
				if (data.length >= 2) {
					if (!data[data.length - 1][5].equals(data[data.length - 2][5])) {
						System.out.println(TagID + "\t" + time + "\t From \t" + data[data.length - 2][5] + "\t to \t"
								+ data[data.length - 1][5]);
						if (data[data.length - 2][5].equals("in") && data[data.length - 1][5].equals("out")) {
							post(TagID, -1, 0);
						}
					}
				}
				if (result.size() >= 2) {
					if (result.get(result.size() - 1) == 0 && result.get(result.size() - 2) == 0) {

						System.out.println(TagID + "\t" + time + "\t GO OUT \t from " + data[data.length - 1][5] + "\t at\t"
								+ data[data.length - 1][6] + " side");
						int orient = 0;
						if (data[data.length - 1][6].equals("left")) {
							orient = 0;
						} else if (data[data.length - 1][6].equals("middle")) {
							orient = 1;
						} else {
							orient = 2;
						}

						post(TagID, 1, orient);
						excute = false;
						File f1 = new File("Data/datas/" + TagID + ".csv");
						f1.delete();
						sleep(10);
					}
				}
				i++;
				// T++;
				sleep(1000);

				// if (T==60) {
				// T=0;
				// }
				// time = time.split(":")[0]+":"+time.split(":")[1]+":"+T;
				Date dNow1 = null;
				SimpleDateFormat ft1 = new SimpleDateFormat("hh:mm:ss");
				try {
					dNow1 = ft1.parse(time);
					dNow1.setSeconds(dNow1.getSeconds() + 1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				time = ft1.format(dNow1);

				//
				// System.out.println(time+"!");

			}

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void post(String tagId, int direction, int orientation) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost("http://10.201.42.210:8080/WatchDog_Server/analyse.action");

		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("reader", "Reader1"));
		urlParameters.add(new BasicNameValuePair("tagId", tagId));
		urlParameters.add(new BasicNameValuePair("direction", String.valueOf(direction)));
		urlParameters.add(new BasicNameValuePair("orientation", String.valueOf(orientation)));

		try {
			post.setEntity(new UrlEncodedFormEntity(urlParameters));
			HttpResponse response = client.execute(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
