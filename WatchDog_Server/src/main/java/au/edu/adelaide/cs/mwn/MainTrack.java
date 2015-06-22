package au.edu.adelaide.cs.mwn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.opencsv.CSVReader;

public class MainTrack {
	//private static int time=0;
	private  double[] meanrssi = new double[4];
	private  int[] rssicount = new int[4];
	private  Double[][] loc=new Double[1][2];
	//public static String path = "Data/Checking Data/corner left straight out to in 2.csv";
	//public static String path = "Data/Checking Data/left in right out (out to in) 3.csv";
	//public static String path = "Data/Checking Data/right in left out (out to in) 1.csv";
	//public static String path = "Data/Checking Data/No traversal in 1.csv";
	//public static String path = "Data/Checking Data/right in left out (out to in) 1.csv";
	//private static double initx = 0.0;
	//private static double inity = 0.0;
	private  double p_max = 0.3;
	private  double p_min = 0.1;
	
    public int track(String path, String TagID, String time,int T) throws FileNotFoundException {
    	ArrayList<String[]> realrssi = new ArrayList<String[]>();
    	meanrssi = new double[4];
    	rssicount = new int[4];
        boolean n = true;
        boolean m = true;
        try {
			CSVReader reader = new CSVReader(new FileReader(path));
			String [] nextLine;
		     while ((nextLine = reader.readNext()) != null) {
		        // nextLine[] is an array of values from the line
		    	// System.out.println(time);
		    	 
		    	 Date dNow =null;
					SimpleDateFormat ft = 
						      new SimpleDateFormat ("hh:mm:ss");
					try {
						dNow = ft.parse(nextLine[0]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String timestr = ft.format(dNow);
					// System.out.println(timestr+"@");
					// System.out.println(time+"%");
					 
		    	 if (timestr.equals(time)) {
		    		
		    		 realrssi.add(nextLine);
				}
		    	
		         //System.out.println(nextLine[0] +"\t"+ nextLine[1] +"\t"+ nextLine[2] +"\t"+ nextLine[3] +"\t"+ nextLine[4]+"\t"+ nextLine[5]+"\t"+ nextLine[6]   );
		     }
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        if (realrssi.size()<=0) {
			return 0;
		}
        //int i=0;
        //ReadWriteDataTxt rwTxt = new ReadWriteDataTxt();
//        for (int i = 0; i < 4; i++) {
//			meanrssi[i] =0.0;
//			rssicount[i] =0;
//		}
        for (int j = 0; j < realrssi.size(); j++) {
        	//System.out.println(realrssi.get(j)[6]);
        	meanrssi[Integer.parseInt(realrssi.get(j)[2])-1]+=Double.parseDouble(realrssi.get(j)[1]);
    		rssicount[Integer.parseInt(realrssi.get(j)[2])-1]+=1;
		}
        excute(T,TagID,time,100);
        return 1;
    }
    
    public static double getAngleFromPoint1(double x1,double y1,double x2,double y2) {

	    if((x2 > x1)) {//above 0 to 180 degrees

	        return (Math.atan2((x2 - x1), (y1 - y2)) * 180 / Math.PI);

	    }
	    else if((x2 < x1)) {//above 180 degrees to 360/0

	        return 360 - (Math.atan2((x1 - x2), (y1 - y2)) * 180 / Math.PI);

	    }//End if((secondPoint.x > firstPoint.x) && (secondPoint.y <= firstPoint.y))

	    return Math.atan2(0 ,0);

	}
    
	private void excute(int T,String TagID,String time,int particles){
		ReadWriteDataTxt rwTxt  = new ReadWriteDataTxt();
		Double[] directions= new Double[8];
		Double[] lastoc  = new Double[2];
		int dir = 1;
		int dir0 = 1;
		String[][] data;
		if (T==0) {
			for (int i2 = 0; i2 < directions.length; i2++) {
				directions[i2] = 1.0;
			}
		}
		
		if (T>0) {
			try {
				data = rwTxt.readTxt("Data//"+TagID+"result.txt");
				String dirsstr = data[data.length-1][3].substring(1, data[data.length-1][3].length()-1);
				
				dir = Integer.parseInt(data[data.length-1][4]);
				if (data.length-2>=0) {
					dir0 = Integer.parseInt(data[data.length-2][4]);
				}
				String[] last = data[data.length-1][2].substring(10).split(":");
				for (int i3 = 0; i3 < last.length; i3++) {
					lastoc[i3] = Double.parseDouble(last[i3]);
				}
				String[] direction = dirsstr.split(",");
				//System.out.println(dirsstr);
				for (int i2 = 0; i2 < direction.length; i2++) {
					directions[i2] = Double.parseDouble(direction[i2]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//System.out.println(Arrays.toString(directions)+"1");
			if (T==1) {
				for (int i1 = 0; i1 < directions.length; i1++) {
					if (i1==dir) {
							directions[i1]=p_max*particles;
					}
					else{
						directions[i1]=p_min*particles;
					}
				}
			}else {
				if (directions[dir]<p_max*particles && directions[dir0]>p_min*particles) {
					directions[dir]+=p_min*particles;
					directions[dir0]-=p_min*particles;
					
				}
			}
			
		}
		else {
			
		}
		
		for (int i = 0; i < meanrssi.length; i++) {
			meanrssi[i]=meanrssi[i]/rssicount[i];
		}
		//System.out.println(Arrays.toString(meanrssi));
		DecimalFormat df   = new DecimalFormat("######0.00");  
		ParticleFilterEngine pf = new ParticleFilterEngine(particles);
				if (T==0) {
					pf.init();
				}
				else 
				{
					pf.predict(directions,loc);
				}
				try {
					pf.weightmean(meanrssi);
					pf.normalize();
				} catch (IOException e) {
					e.printStackTrace();
				}
				pf.resample();
				pf.update();
				loc[0][0]=pf.location[0][0];loc[0][1]=pf.location[0][1];
				if (T>0) {
					
				
					double angel  = getAngleFromPoint1(lastoc[0], lastoc[1],loc[0][0], loc[0][1]);
					
					if (angel>=327.5 || angel <=22.5) {
						dir = 4;
					}
					if (angel>=22.5 && angel <=67.5) {
						dir = 3;
					}
					if (angel>=67.5 && angel <=112.5) {
						dir = 2;
					}
					if (angel>=112.5 && angel <=157.5) {
						dir = 1;
					}
					if (angel>=157.5 && angel <=202.5) {
						dir = 0;
					}
					if (angel>=202.5 && angel <=247.5) {
						dir = 7;
					}
					if (angel>=247.5 && angel <=292.5) {
						dir = 6;
					}
					if (angel>=292.5 && angel <=337.5) {
						dir = 5;
					}
				}
				else{
					if (loc[0][0]<=4.5) {
						if (loc[0][1]<=12.5) {
							dir = 1;
						}
						else {
							dir = 3;
						}
					}
					else{
						if (loc[0][1]<=12.5) {
							dir= 7;
						}
						else{
							dir =5;
						}
					}
				}
				
				String zone ;String side;
				if (loc[0][1]<=13) {
					zone = "in";
				}
				else
					zone = "out";
				if (loc[0][0]<=4) {
					side = "left";
				}
				else if(loc[0][0]<=5){
					side = "middle";
				}else
					side = "right";
				
				String result ="TagID:"+TagID+ "\t time:"+time+"\t Location:"+df.format(loc[0][0])+":"+df.format(loc[0][1])
						+"\t"+Arrays.toString(directions)+"\t"+dir+"\t"+zone+"\t"+side;
				try {
					rwTxt.write2Txt("Data/"+TagID+"result.txt", result);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
	
}
