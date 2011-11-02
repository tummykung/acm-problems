import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class cell {

	public static int numTowers;
	public static int numSegments;
	public static int[] towerXs, towerYs, towerPowers;
	public static int[] pointXs, pointYs;

	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try{
		while (true) {
			//====  Input =======
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			numTowers = Integer.parseInt(tokenizer.nextToken());
			if (numTowers == 0) break;
			numSegments = Integer.parseInt(tokenizer.nextToken());
			towerXs = new int[numTowers];
			towerYs = new int[numTowers];
			towerPowers = new int[numTowers];
			for (int i = 0; i < numTowers; i++) {
				tokenizer = new StringTokenizer(in.readLine());
				towerXs[i] = Integer.parseInt(tokenizer.nextToken());
				towerYs[i] = Integer.parseInt(tokenizer.nextToken());
				towerPowers[i] = Integer.parseInt(tokenizer.nextToken());
			}
			pointXs = new int[numSegments+1];
			pointYs = new int[numSegments+1];
			tokenizer = new StringTokenizer(in.readLine());
			for (int i = 0; i < numSegments+1; i++) {
				pointXs[i] = Integer.parseInt(tokenizer.nextToken());
				pointYs[i] = Integer.parseInt(tokenizer.nextToken());
			}
			//==== SETUP ========
			int curPoint = 0;
			int prevPointX = pointXs[curPoint];
			int prevPointY = pointYs[curPoint];
			int curTower = strongestTower(prevPointX, prevPointY);
			System.out.print("(0,"+Character.toString((char)('A'+curTower))+")");
			int mileMarker = 0;
			double curX = prevPointX;
			double curY = prevPointY;
			
			double leftoverDist = 0; // leftover distance from last segment
			for (curPoint = 1; curPoint < numSegments+1; curPoint++) {
				int pointX = pointXs[curPoint];
				int pointY = pointYs[curPoint];
				double dx =  pointX - prevPointX;
				double dy = pointY - prevPointY;
				prevPointX = pointX;
				prevPointY = pointY;
				double len = Math.sqrt(dx*dx + dy*dy);
				dx /= len;
				dy /= len;
				
				// Go any remaining distance that overflowed from the last segment
				if (leftoverDist > 0) {
//					System.out.println("took remaining step "+leftoverDist);
					curX += dx*leftoverDist;
					curY += dy*leftoverDist;
					mileMarker++;
					leftoverDist = 0;
				}
				
				while (true) {
//					System.out.println("mile "+mileMarker+"  at "+curX+","+curY);
					// distance to next point
					double dist = Math.sqrt((pointX-curX)*(pointX-curX) + (pointY-curY)*(pointY-curY));
					if (dist < 1) {
//						System.out.println("took partial step "+dist);
						// if one step will go too far, just go to the point and take care of the extra distance later
						leftoverDist = 1 - dist;
						curX = pointX;
						curY = pointY;
					}
					else {
						// take a full step
						curX += dx;
						curY += dy;
						mileMarker++;
						int newTower = strongestTower(curX, curY);
						if (curTower != newTower) {
							System.out.print(" ("+mileMarker+","+Character.toString((char)('A'+newTower))+")");
							curTower = newTower;
						}
					}
					if (curX == pointX && curY == pointY) break; // move on to the next segment
				}
			}
			if (leftoverDist < 0.5) {
				mileMarker++;
//				System.out.println("finishing path with mile "+mileMarker);
				int newTower = strongestTower(curX, curY);
				if (curTower != newTower) {
					System.out.print(" ("+mileMarker+","+Character.toString((char)('A'+newTower))+")");
					curTower = newTower;
				}
			}
			System.out.println();
		//===== RUN ========
			
		//===== OUTPUT =====
		// System.out.println();
		}
		} // Write before this line
		catch(Exception e){
			System.out.print("throwing up");
		}
	}
	
	public static int strongestTower(double x, double y) {
		int bestStrength = -1;
		int bestTower = -1;
		for (int i = 0; i < numTowers; i++) {
			int tx =  towerXs[i];
			int ty =  towerYs[i];
			double distance = Math.sqrt((tx-x)*(tx-x) + (ty-y)*(ty-y));
			int strength = (int)Math.round(towerPowers[i]/(distance*distance));
			if (strength > bestStrength ||
			    (strength == bestStrength && i < bestTower)) {
				bestStrength = strength;
				bestTower = i;
			}
		}
		return bestTower;
	}
}
