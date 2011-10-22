import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;
import java.util.ArrayList;

public class aliens {
	private static Scanner scan = new Scanner(System.in);
	private static Point[] staticCowLocations;
	private static Vector<Point> cowLocations = new Vector<Point>();
	private static List<Point> convexCowLocations;

	public static void main(String[] args){
		int numCows = scan.nextInt();
		staticCowLocations = new Point[numCows];

		int numAliens = scan.nextInt();
		
		for (int cow = 0; cow < numCows; cow++){
			int x = scan.nextInt();
			int y = scan.nextInt();
			cowLocations.add(new Point(x, y));
			staticCowLocations[cow] = new Point(x, y);
		}
		
		convexCowLocations = calculate();
		for (int al = 0; al < numAliens; al++) {
			int x = scan.nextInt();
			int y = scan.nextInt();
			int fav = scan.nextInt() - 1; // offset
			Point alien = new Point(x, y);
			Point favCow = staticCowLocations[fav];
			// System.out.println("Alien is at " + alien.x + ", " + alien.y);
			System.out.println(isCCWest(alien, favCow) ? "1" : "0");
		}
	}

	public static boolean isCCWest(Point alien, Point fav){
		//System.out.println("Favorite cow is at " + fav.x + ", " + fav.y);
		for (Point cow : convexCowLocations){
			// System.out.println("Current cow is at " + cow.x + ", " + cow.y);
			if (cross(alien, fav, cow) > 0){
				//System.out.println("Favorite cow is not CCWest, current cow is CCWest");
				return false;
			}
		}
		return true;
	}

	public static List<Point> calculate(){
		Collections.sort(cowLocations);
		Vector<Point> lower = new Vector<Point>();
		for(int i = 0; i < cowLocations.size(); i++){
			while (lower.size() >= 2 && cross(
					lower.elementAt(lower.size() - 2), 
					lower.elementAt(lower.size() - 1), 
					cowLocations.get(i)) <= 0)
				lower.remove(lower.size() - 1);
			lower.add(cowLocations.get(i));
		}
		lower.remove(lower.size() - 1);
		Collections.reverse(cowLocations);
		Vector<Point> upper = new Vector<Point>();
		for(int i = 0; i < cowLocations.size(); i++){
			while (upper.size() >= 2 && cross(
					upper.elementAt(upper.size() - 2), 
					upper.elementAt(upper.size() - 1), 
					cowLocations.get(i)) <= 0)
				upper.remove(upper.size() - 1);
			upper.add(cowLocations.get(i));
		}
		upper.remove(upper.size() - 1);
		List<Point> combined = new ArrayList<Point>(lower);
		combined.addAll(upper);

		return combined;
	}

	private static double cross(Point o, Point a, Point b){
		return (a.x - o.x) * (b.y - o.y) - (a.y - o.y) * (b.x - o.x);
	}

	public static class Point implements Comparable<Point> {
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
