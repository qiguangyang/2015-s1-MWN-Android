package au.edu.adelaide.cs.mwn;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class ParticleFilterEngine {
	
	public int N;
	public Double[][] coordinates;
	public Double[] weight;
	public Double[][] location=new Double[1][2];
	public Double[][] predict=new Double[1][2];
	public Double[] motoin = new Double[8];
	
	private double s_x = 1.8;
	private double s_y = 7;
	
	public ParticleFilterEngine(int m){
		this.N=m;
		coordinates=new Double[N][2];
		weight=new Double[N];
	}
	
	/**
	 * init particles 
	 */
	
	public void init(){
		Random pos = new Random();
		//Random pos1 = new Random();
		for (int i = 0; i < N/2; i++) {
				Double x = pos.nextDouble()*9;
				Double y = pos.nextDouble()*6;
				coordinates[i][0]=x;
				coordinates[i][1]=y;
		}
		for (int i = N/2; i < N; i++) {
			Double x = pos.nextDouble()*9;
			Double y = pos.nextDouble()*6+19;
			coordinates[i][0]=x;
			coordinates[i][1]=y;
		}
		for (int j = 0; j < weight.length; j++) {
			weight[j] = 1.0 / (weight.length * 1.0);
		}
	}
	
	/**
	 * 
	 * @param rssivalue
	 * @throws IOException
	 */
	public void weightmean(double[] rssivalue) throws IOException{
		//System.out.println(Arrays.toString(weight));
		String rssi_txt = "";
		ReadWriteDataTxt rwDataTxt = new ReadWriteDataTxt();
		//System.out.println(Arrays.toString(rssivalue));
		for (int j = 0; j < rssivalue.length; j++) {
			if (rssivalue[j]<0) {
				rssi_txt="Data//rssi_real_"+(j+1)+".txt";
				//System.out.println(rssi_txt);
				
				String[][] rssimap = rwDataTxt.readTxt(rssi_txt);
				//System.out.println(rssi_txt);
				
				for (int i = 0; i < weight.length; i++) {
					int x = (int) Math.round(coordinates[i][0]);
					int y = (int) Math.round(coordinates[i][1]);
					
					if (x<=8 && y<=24 && x>=0 && y>=0) {
						if (rssimap[y][x].equals("NaN")) {
							weight[i]=0.0;
							continue;
						}
						Double mapvalue = Double.parseDouble(rssimap[y][x]);
						double newWeight = Math.exp(-Math.abs(mapvalue - rssivalue[j]));
							if (newWeight!=newWeight) {
								weight[i]=0.0;
								continue;
							}
							weight[i]+=newWeight;						
						
					}
					else {
						weight[i]=0.0;
					}
					//System.out.println(x+"--"+y+"--"+weight[i]);
				}
				
			}
		}
		
	}
	
	
	
	/**
	 * normalize the weight
	 */
	public void normalize(){
		
		double sum = 0.0;
		for (int i = 0; i < weight.length; i++) {
			sum+=weight[i];
		}
		for (int j = 0; j < weight.length; j++) {
			weight[j]=weight[j]/sum;
		}
		
	}
	
	
	/**
	 * Re-samples a set of weighted particles to produce a new set of unweighted
   particles
	 */
	public void resample(){
		
		Double[] tempweight = new Double[N];
		for (int i = 0; i < weight.length; i++) {
			tempweight[i] = weight[i];
		}
		Arrays.sort(tempweight);
		//System.out.println(Arrays.toString(tempweight));
		
		int index =(int) (tempweight.length*0.9);
		Random pos = new Random();	
		for (int j = 0; j < weight.length; j++) {
			int random = (int) Math.round(pos.nextDouble()*((int) (tempweight.length*0.1)-1));
			if (weight[j]< tempweight[index+random]) {
				int k=0;
				for (int i = 0; i < weight.length; i++) {
					if (weight[i]>=tempweight[index+random]) {
						k=i;
					}
				}
				coordinates[j][0] = coordinates[k][0]+pos.nextDouble();
				coordinates[j][1] = coordinates[k][1]+pos.nextDouble();
				//System.out.println(coordinates[k][0]+"--"+coordinates[k][1]+"--"+weight[k]);
			}
		}
		
	}
	
	/**
	 * Update the coordate
	 */
	public void update() {
		
		Double sumx=0.0;
		Double sumy=0.0;
		for (int i = 0; i < coordinates.length; i++) {
			sumx+=coordinates[i][0];
			sumy+=coordinates[i][1];
		}
		Double locx = sumx/coordinates.length;
		Double locy = sumy/coordinates.length;
		location[0][0]=locx;
		location[0][1]=locy;
	}
	
	/**
	 * Predict location by particles with weight
	 */
	public void predict(Double[] directions,Double[][] location){
		
		Random pos = new Random();
		int[] dir = new int[8];
		double x1 = 0.2;
		double y1 = 1.5;
		
		for (int i = 0; i < dir.length; i++) {
			int sum = 0;
			for (int j = 0; j <= i; j++) {
				sum+=directions[j].shortValue();
			}
			dir[i]=sum;
		}
		//System.out.println(Arrays.toString(directions));
//		System.out.println(Arrays.toString(dir));
//		System.out.println(location[0][0]+"=="+location[0][1]);
		for (int j = 0; j < dir[0]; j++) {
			coordinates[j][0] = location[0][0]+pos.nextDouble();
			double y = location[0][1]+pos.nextDouble()*(s_y-y1)+y1;
			if (y>=24) {
				coordinates[j][1] = 24-pos.nextDouble();
			}
			else
			coordinates[j][1] = y;
		}
		
		for (int j = dir[0]; j < dir[1]; j++) {
			double x = location[0][0]+pos.nextDouble()*(s_x-x1)+x1;
			double y = location[0][1]+pos.nextDouble()*(s_y-y1)+y1;
			if (x>=9) {
				coordinates[j][0] =9-pos.nextDouble();
			}
			else coordinates[j][0] = x;
			if (y>=24) {
				coordinates[j][1] = 24-pos.nextDouble();
			}
			else coordinates[j][1] = y;
	    }
		
		for (int j = dir[1]; j < dir[2]; j++) {
			double x = location[0][0]+pos.nextDouble()*(s_x-x1)+x1;
			if (x>=9) {
				coordinates[j][0] =9-pos.nextDouble();
			}
			else coordinates[j][0] = x;
			coordinates[j][1] = location[0][1]+pos.nextDouble();
		}
		
		for (int j = dir[2]; j < dir[3]; j++) {
			double x = location[0][0]+pos.nextDouble()*(s_x-x1)+x1;
			if (x>=9) {
				coordinates[j][0] =9-pos.nextDouble();
			}
			else coordinates[j][0] = x;
			coordinates[j][1] = location[0][1]-pos.nextDouble()*(s_y-y1)-y1;
	    }
		
		for (int j = dir[3]; j < dir[4]; j++) {
			coordinates[j][0] = location[0][0]+pos.nextDouble();
			coordinates[j][1] = location[0][1]-pos.nextDouble()*(s_y-y1)-y1;
		}
		
		for (int j = dir[4]; j < dir[5]; j++) {
			coordinates[j][0] = location[0][0]-pos.nextDouble()*(s_x-x1)-x1;
			coordinates[j][1] = location[0][1]-pos.nextDouble()*(s_y-y1)-y1;
	    }
		for (int j = dir[5]; j < dir[6]; j++) {
			coordinates[j][0] = location[0][0]-pos.nextDouble()*(s_x-x1)-x1;
			coordinates[j][1] = location[0][1]+pos.nextDouble();
	    }
		for (int j = dir[6]; j < dir[7]; j++) {
			coordinates[j][0] = location[0][0]-pos.nextDouble()*(s_x-x1)-x1;
			coordinates[j][1] = location[0][1]+pos.nextDouble()*(s_y-y1)+y1;
	    }
		
		for (int j = 0; j < weight.length; j++) {
			weight[j] = 1.0 / (weight.length * 1.0);
		}
	}
	
	public void motion(){
		
	}
	
}
