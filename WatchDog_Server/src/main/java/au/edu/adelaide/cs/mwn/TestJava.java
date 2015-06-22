package au.edu.adelaide.cs.mwn;

import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

public class TestJava {
	public static void main(String[] args) {
		Point first = new Point(1,1);
		Point second = new Point(0,0);
		System.out.println(getAngleFromPoint(first,second));
		
		System.out.println(getAngleFromPoint1(1.0,1.1,2.2,2.2));
		
		
	}
	public static double getAngleFromPoint(Point firstPoint, Point secondPoint) {

	    if((secondPoint.x > firstPoint.x)) {//above 0 to 180 degrees

	        return (Math.atan2((secondPoint.x - firstPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);

	    }
	    else if((secondPoint.x < firstPoint.x)) {//above 180 degrees to 360/0

	        return 360 - (Math.atan2((firstPoint.x - secondPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);

	    }//End if((secondPoint.x > firstPoint.x) && (secondPoint.y <= firstPoint.y))

	    return Math.atan2(0 ,0);

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
}
