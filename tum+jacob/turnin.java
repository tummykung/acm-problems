import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class turnin {

	public static int numRooms;
	public static class Room implements Comparable{
		public int location, openTime;

		public int compareTo(Object b) {
			if(this.location == ((Room) b).location)
				return this.openTime - ((Room) b).openTime;
			return this.location - ((Room) b).location;
		}
		public int d(Room b) {
			return Math.abs(this.openTime - b.openTime);
		}	
	}
	public static class Time{
		int real, wait;
		public Time(int real, int wait){
			this.real = real;
			this.wait = wait;
		}
	}
	public static class OrderPair{
		int x, y, z;
		public OrderPair(int x, int y, int z){
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
	public static int hallwayLength;
	public static int busLocation;
	public static int[][] memo;
	public static ArrayList<Room> rooms;
	public static Time best[][];
	public static int numLeft;			 //number of rooms on the left of the exit after filtering

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		numRooms = s.nextInt();
		rooms = new ArrayList<Room>();
		hallwayLength = s.nextInt();
		busLocation = s.nextInt();
		
		// Start Point
		{
		Room r = new Room();
		r.location = 0;
		r.openTime = 0;
		rooms.add(r);
		}
		
		for (int i = 0; i < numRooms; i++) {
			Room r = new Room();
			r.location = s.nextInt();
			r.openTime = s.nextInt();
			rooms.add(r);
		}
		Collections.sort(rooms);

		int maxBus = 0;	// This is the latest open time at the bus stop
		for(int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).location == busLocation && rooms.get(i).openTime > maxBus)
				maxBus = rooms.get(i).openTime;
		}
		
		// =============== Filtering process ===============
		// Filter left
		{int i = 0;
		while(true) {
			if(i >= rooms.size() || rooms.get(i).location > busLocation)
				break;
			int j = i + 1;
			while(true) {
				if(j >= rooms.size() || rooms.get(j).location > busLocation)
					break;
				if(rooms.get(j).openTime <= rooms.get(i).openTime + rooms.get(i).d(rooms.get(j))) {
					rooms.remove(j);
					break;
				} j++;
			} i++;
		}// end big while
		}
		// Filter Right
		{int i = rooms.size() - 1;
		while(true) {
			if(i < 0 || rooms.get(i).location < busLocation)
				break;
			int j = i - 1;
			while(true) {
				if(j < 0 || rooms.get(j).location < busLocation)
					break;
				if(rooms.get(j).openTime <= rooms.get(i).openTime + rooms.get(i).d(rooms.get(j))) {
					rooms.remove(j);
					i--;	// You have to shift things to the left also
					break;
				} j--;
			} i--;
		}// end big while
		}
		
		Room roomBus = new Room();
		roomBus.location = busLocation;
		roomBus.openTime = maxBus;
		rooms.add(roomBus);
		
		Collections.sort(rooms);
		
		for(int i = 0; i < rooms.size(); i++) {
			System.out.println(rooms.get(i).location + " " + rooms.get(i).openTime);
		}
		
		//============== now do the dynamic programming on left ends and right ends =======
		numRooms = rooms.size();
		// Count left
		int numLeft = 0;	// not including the exit (at the busLocation)
		for(int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).location < busLocation)
				numLeft++;
		}
		System.out.println("numLeft = " + numLeft);
		System.out.println("numRooms = " + numRooms);
		
		best = new Time[numRooms][numRooms]; // i -> j
		for(int i = 0 ; i < numRooms; i++) {
			for(int j = 0 ; j < numRooms; j++) {
				if(i == j)
					best[i][j] = new Time(0, 0);
				else
					best[i][j] = new Time(-1, 0);
			}
		}
		int bestTime = bestRight(0, numRooms - 1, 0).real;
		// Final check
		System.out.println("before filter:" + bestTime);
		if(bestTime < maxBus)
			bestTime = maxBus;
		// output
		System.out.println(bestTime);
			
	}
	
	public static Time bestRight(int i, int j, int offset){
		System.out.println("CallR (" + i + " , " + j + "). Best[i][j] = " + best[i][j].real + ","  + best[i][j].wait);
		if(best[i][j].real == -1){
			int bestTime = -1;
			int bestTimeWait = -1;
			for(int k = i; k <= numLeft; k++) {
				System.out.println("TryK (" + i + " , " + k + "), k=" +k+", Best[i][j] = " + best[i][j].real + ","  + best[i][j].wait);
				// check the option of hanging out at k at the last place before changing side
				OrderPair temp = derive(i, k, offset);
				int newOffset = temp.x;
				int waitTime = temp.y;
				int d = temp.z;
				
				if(j == numLeft)
					return new Time(0, waitTime);
				
				Time tempT = bestLeft(j, Math.min(k+1, numLeft), newOffset);
				int candidateTime = d + waitTime + tempT.real;
				int wait = waitTime + tempT.wait;
				
				System.out.println("candidateTime " + candidateTime);
				System.out.println("wait " + wait);
				
				if(bestTime == -1 || candidateTime < bestTime){
					bestTime = candidateTime;
					bestTimeWait = wait;
				}
			}
			best[i][j] = new Time(bestTime, bestTimeWait);	
		}
		System.out.println("ThenR (" + i + " , " + j + "). Best[i][j] = " + best[i][j].real + ","  + best[i][j].wait);
		return best[i][j];
	}
	
	public static Time bestLeft(int i, int j, int offset){
		System.out.println("CallL (" + i + " , " + j + "). Best[i][j] = " + best[i][j].real + ","  + best[i][j].wait);
		if(best[i][j].real == -1){
			int bestTime = -1;
			int bestTimeWait = -1;
			for(int k = i; k >= numLeft; k--) {
				// check the option of hanging out at k at the last place before changing side
				System.out.println("TryK (" + i + " , " + k + "), k=" +k+", Best[i][j] = " + best[i][j].real + ","  + best[i][j].wait);
				OrderPair temp = derive(i, k, offset);
				int newOffset = temp.x;
				int waitTime = temp.y;
				int d = temp.z;
				
				if(j == numLeft)
					return new Time(0, waitTime);
				
				Time tempT = bestRight(j, Math.max(k-1, numLeft), newOffset);
				int candidateTime = d + waitTime + tempT.real;
				int wait = waitTime + tempT.wait;
				
				System.out.println("candidateTime " + candidateTime);
				System.out.println("wait " + wait);
				
				if(bestTime == -1 || candidateTime < bestTime){
					bestTime = candidateTime;
					bestTimeWait = wait;
				}
			}
			best[i][j] = new Time(bestTime, bestTimeWait);	
		}
		System.out.println("ThenL (" + i + " , " + j + "). Best[i][j] = " + best[i][j].real + ","  + best[i][j].wait);
		return best[i][j];
	}
	
	public static OrderPair derive(int i, int k, int offset){
		Room roomI = rooms.get(i);
		Room roomK = rooms.get(k);
		int newOffset = 0;
		int waitTime = 0;		// wait: from i -> k (will be changed)
		int d = roomI.d(roomK);	// real: from i -> k 
		int del = Math.abs(roomK.openTime - roomI.openTime);
		if(offset + d > del){
			newOffset = 0;
			waitTime += offset + d - del;
		} else 
			newOffset = del - offset - d;
		return new OrderPair(newOffset, waitTime, d);
	}
}