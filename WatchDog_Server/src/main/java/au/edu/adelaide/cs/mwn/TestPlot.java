package au.edu.adelaide.cs.mwn;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;

import javax.swing.JFrame;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.TracePoint2D;
import info.monitorenter.gui.chart.pointpainters.PointPainterDisc;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.traces.Trace2DSimple;

public class TestPlot {

	TestPlot() {
		
	}
	public void showdiag(){
	    // Create a chart:  
	    Chart2D chart = new Chart2D();
	    // Create an ITrace: 
	    ITrace2D trace = new Trace2DLtd();
	    // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
	    chart.addTrace(trace);    
	    trace.setColor(Color.RED);
	    trace.setPointHighlighter(new PointPainterDisc());
	    chart.enablePointHighlighting(true);
	    
	    // Add all points, as it is static: 
	    Random random = new Random();
	    ReadWriteDataTxt rwTxt = new ReadWriteDataTxt();
	    String[][] data;
		try {
			data = rwTxt.readTxt("Data/13result.txt");
		for (int i = 0; i < data.length; i++) {
			String[] last = data[i][2].substring(10).split(":");
		    Double[] lastoc = new Double[2];
			for (int i3 = 0; i3 < last.length; i3++) {
				lastoc[i3] = Double.parseDouble(last[i3]);
				
			}
			PointPainterDisc icon = new PointPainterDisc(); 
			icon.setDiscSize(i); // make it bigger than the others
			icon.setColorFill(Color.BLUE); // choose a color not used by the others
			
			TracePoint2D point = new TracePoint2D(lastoc[0], lastoc[1]);
			
			point.addAdditionalPointPainter(icon);
			
			trace.addPoint(point);
			
			//trace.addPoint(lastoc[0], lastoc[1]);
		}
	    
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    // Make it visible:
	    // Create a frame.
	    JFrame frame = new JFrame("MinimalStaticChart");
	    // add the chart to the frame: 
	    frame.getContentPane().add(chart);
	    frame.setSize(400,300);
	    // Enable the termination button [cross on the upper right edge]: 
	    frame.addWindowListener(
	        new WindowAdapter(){
	          public void windowClosing(WindowEvent e){
	              System.exit(0);
	          }
	        }
	      );
	    frame.setVisible(true);
	  }
}
