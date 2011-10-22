import java.util.Scanner;
import java.util.StringTokenizer;
import java.math.*;


public class ripoff {
	private static int numSquares;
	private static int maxJump;
	private static int numTurns;
	private static int[][] rebates;
	private static int[] values;
	
	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		while(true){
			numSquares = s.nextInt();
			if (numSquares == 0)
				break;
			
			maxJump = s.nextInt();
			numTurns = s.nextInt();
			rebates = new int[numSquares][numTurns];
			values = new int[numSquares];
			
			for (int sq = 0; sq < numSquares; sq++)
				values[sq] = s.nextInt();
				
			for (int i = 0; i < numSquares; i++)
				for (int j = 0; j < numTurns; j++)
					rebates[i][j] = Integer.MAX_VALUE;
			
			int maxRebate = calculate();
			System.out.println(maxRebate);
		}
	}
	
	public static int calculate(){
		int max = Integer.MIN_VALUE;
		
		for (int step = 1; step <= maxJump; step++) {
			if ((step - 1 + (numTurns - 1) * maxJump) >= numSquares) {
				int thisStep = calculate(step - 1,  1);
				max = Math.max(max, thisStep);
			}
		}
		return max;
		
	}
			
	public static int calculate(int startPoint, int turnsUsed){
		if (startPoint >= numSquares)
			return 0;
		
		else if (rebates[startPoint][turnsUsed] != Integer.MAX_VALUE)
			return rebates[startPoint][turnsUsed];
		
		else {
			int max = Integer.MIN_VALUE;
			
			// if you have only one turn left, take this step
			if (turnsUsed == numTurns - 1)
				return values[startPoint];
			
			else { // try stepping once
				for (int step = 1; step <= maxJump; step++) {
					if ((startPoint + step + (numTurns - turnsUsed - 1) * maxJump) >= numSquares) {
						int thisStep = values[startPoint] + calculate(startPoint + step, turnsUsed + 1);
						/*System.out.println("at point " + startPoint);
						System.out.println("taking step " + step);
						System.out.println("value is " + thisStep);*/
						max = Math.max(max, thisStep);
					}
				}
			}
			rebates[startPoint][turnsUsed] = max;
			return max;
		}
		
	}
}
