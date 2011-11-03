import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class turnin {
	
	public static int numRooms;
	public static class Room {
		public int location, openTime;
		public int id;
	}
	public static int hallwayLength;
	public static int busLocation;
	public static int[][] memo;
	

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
		memo = new int[maxOpenTime*numRooms][hallwayLength];
		for (int i = 0; i < maxOpenTime*numRooms; i++) {
			for (int j = 0; j < hallwayLength; j++) {
				memo[i][j] = -1;
			}
		}
		
		System.out.println(minExitTime(0, 0, remainingRooms));
	}
	
	public static int minExitTime(int currentTime, int currentLocation, List<Room> remainingRooms) {
		// No remaining rooms, just go to the bus
		if (remainingRooms.isEmpty()) {
//			System.out.println("Current time "+currentTime+", current position "+currentLocation+
//					", going to bus (distance "+Math.abs(currentLocation - busLocation)+")");
			return currentTime + Math.abs(currentLocation - busLocation);
		}
		// Check for pre-calculated result
//		System.out.println("Current time "+)
		if (memo[currentTime][currentLocation] != -1) {
			System.out.println("Reusing result from time "+currentTime+", position "+currentLocation);
			return memo[currentTime][currentLocation];
		}
		int minTime = Integer.MAX_VALUE;
		for (Room r : remainingRooms) {
			// copy the room list and remove this room
			List<Room> newRemainingRooms = new LinkedList<Room>(remainingRooms);
			newRemainingRooms.remove(r);
			//System.out.println(newRemainingRooms.toString());
			
			
			// walk to the room, and wait if it's not open
			int waitTime = Math.max(currentTime + Math.abs(currentLocation - r.location), r.openTime);
			System.out.println("Current time "+currentTime+", current position "+currentLocation+
					", trying room "+r.id+", waitTime "+waitTime);
			int time = minExitTime(waitTime, r.location, newRemainingRooms);
			if (time < minTime) {
				minTime = time;
			}
		}
		memo[currentTime][currentLocation] = minTime;
		return minTime;
	}
}
