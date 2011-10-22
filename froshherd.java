import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;
import java.text.*;

public class froshherd {
	private static Scanner scan;
	private List<Point> points = new Vector<Point>();
	
	public static void main(String[] args){
		scan = new Scanner(System.in);
		int numCases = scan.nextInt();
		for (int caseNum = 0; caseNum < numCases; caseNum++){
			froshherd ch = new froshherd();
			DecimalFormat df = new DecimalFormat("#.##");
			System.out.println(df.format(ch.calculate()));
		}
	}
	
	public froshherd(){
		int numInputs = scan.nextInt();
		for (int i = 0; i < numInputs; i++){
			double x = scan.nextDouble();
			double y = scan.nextDouble();
			points.add(new Point(x, y));
		}
	}
	
	public double calculate(){
		Collections.sort(points);
		Vector<Point> lower = new Vector<Point>();
		for(int i = 0; i < points.size(); i++){
			while (lower.size() >= 2 && cross(
					lower.elementAt(lower.size() - 2), 
					lower.elementAt(lower.size() - 1), 
					points.get(i)) <= 0)
				lower.remove(lower.size() - 1);
			lower.add(points.get(i));
		}

		Collections.reverse(points);
		Vector<Point> upper = new Vector<Point>();
		for(int i = 0; i < points.size(); i++){
			while (upper.size() >= 2 && cross(
					upper.elementAt(upper.size() - 2), 
					upper.elementAt(upper.size() - 1), 
					points.get(i)) <= 0)
				upper.remove(upper.size() - 1);
			upper.add(points.get(i));
		}

		List<Point> combined = new ArrayList<Point>(lower);
		combined.addAll(upper);
		
		double length = 0;
		length += length(new Point(0, 0), combined.get(0));
		for (int i = 0; i < combined.size() - 1; i++)
			length += length(combined.get(i), combined.get(i+1));
		length += length(combined.get(combined.size() - 1), new Point(0, 0));

		return length;
	}
	
	private double length(Point a, Point b) {
		double i = Math.pow(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2), 0.5);
		return i;
	}
	
	private double cross(Point o, Point a, Point b){
		return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
	}
	
	public class Point implements Comparable<Point> {
		public double x;
		public double y;
		
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		@Override
		public int compareTo(Point o) {
			int x = new Double(this.x - o.x).intValue();
			int y = new Double(this.y - o.y).intValue();
			
			if (x != 0) return x;
			else return y;
		}
		
	}
}
