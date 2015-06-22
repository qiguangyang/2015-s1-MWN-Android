package au.edu.adelaide.cs.mwn;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

public class ReadWriteDataTxt {
	/**
     * Read data from text file to Array 
     * @param inpath
     * @return 
     * @throws IOException
     */
    public String[][] readTxt(String inpath) throws IOException{  
        File file=new File(inpath);
        FileReader in = new FileReader(file);  
        LineNumberReader reader = new LineNumberReader(in);  
        String line;
        String [][] data= new String[getTxtLines(inpath)][getLineLength(inpath)];
        String[] linedata;
        int i = 0;

        while (true) {  
            line = reader.readLine();  	
            if (line==null) {
                break;
            }
            linedata = line.split("\t");
            for (int j = 0,k=0; j < linedata.length; j++,k++) {
                data[i][k] = linedata[j];
            }
            i++;
        }
        reader.close();  
        in.close();  
        return data;
    } 
    
    public int getTxtLines(String inpath) throws IOException{  
		File file=new File(inpath);
		FileReader in = new FileReader(file);  
		LineNumberReader reader = new LineNumberReader(in);  
		String line;
		int linenum = 0;
		while (true) {  
			line = reader.readLine();
			if (line==null) {
				break;
			}
			linenum++;
		}
		reader.close();  
		in.close();  
		return linenum;
	}
    
    public int getLineLength(String inpath) throws IOException{  
		File file=new File(inpath);
		FileReader in = new FileReader(file);  
		LineNumberReader reader = new LineNumberReader(in);  
		String line;
		int linelength= 0;
		line = reader.readLine();
		linelength=line.split("\\s").length;
		reader.close();  
		in.close();  
		return linelength;
	}
    
    public int write2Txt(String path, String data) throws IOException{
    	File file=new File(path);
    	try {
    		FileWriter fileWriter = new FileWriter(file,true);
    		BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);
    		bufferedWriter.append(data);
    		bufferedWriter.newLine();
    		bufferedWriter.close();
			
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
    	
    	
    	
    	return 0;
	}
	
}
