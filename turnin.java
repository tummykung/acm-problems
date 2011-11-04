import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class turnin2 {
	
	public static int numRooms;
	public static class Room {
		public int location, openTime;
		public int id;
	}
	public static int hallwayLength;
	public static int busLocation;

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		numRooms = s.nextInt();
		List<Room> remainingRooms = new LinkedList<Room>();
		hallwayLength = s.nextInt();
		busLocation = s.nextInt();
		int maxOpenTime = 0;
		for (int i = 0; i < numRooms; i++) {
			Room r = new Room();
			r.location = s.nextInt();
			r.openTime = s.nextInt();
			r.id = i+1;
			remainingRooms.add(r);
			if (r.openTime > maxOpenTime) maxOpenTime = r.openTime;
		}
		
		int currentLocation = 0, currentTime = 0;
		while (true) {
			if (remainingRooms.isEmpty()) break;
			
			// Pick the spatiotemporally closest room
			int minTime = Integer.MAX_VALUE;
			Room minRoom = null;
			for (Room r : remainingRooms) {
				int time = Math.max(Math.abs(currentLocation - r.location), r.openTime - currentTime);
				if (time < minTime) {
					minTime = time;
					minRoom = r;
				}
			}
			remainingRooms.remove(minRoom);
			currentLocation = minRoom.location;
			currentTime += minTime;
			//System.out.println("Go to room "+minRoom.id+" at "+minRoom.location+", it is now time "+currentTime);
		}
		// Go to the bus
		currentTime += Math.abs(currentLocation - busLocation);
		//System.out.println("Go to the bus, total time "+currentTime);
		System.out.println(currentTime);
	}
}
