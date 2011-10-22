import java.util.Scanner;
import java.util.Arrays;

public class cowblank {
	private static int numCows;
	private static int numBlankets;
	
	private static int[] cows;
	private static int[] blankets;
	
	private static int[][] maxUnhappiness;
	
	public static void main(String [] args){
		Scanner s = new Scanner(System.in);
		numCows = s.nextInt();
		numBlankets = s.nextInt();
		
		cows = new int[numCows];
		for (int cow = 0; cow < numCows; cow++)
			cows[cow] = s.nextInt();
		Arrays.sort(cows);
		
		blankets = new int[numBlankets];
		for (int blank = 0; blank < numBlankets; blank++)
			blankets[blank] = s.nextInt();
		Arrays.sort(blankets);
		
		maxUnhappiness = new int[numCows][numBlankets];
		for (int i = 0; i < numCows; i++){
			for (int j = 0; j < numBlankets; j++)
				maxUnhappiness[i][j] = -1;
		}
		
		for (int coldCows = 0; coldCows <= numCows; coldCows++) {
			for (int blankets = 0; blankets <= numBlankets; blankets++) {
				
				
			}
		}
	}
	
	private static int calculate(int cowsUsed, int blanketsUsed){
		if (cowsUsed == numCows)
			return 0;
		else if (blanketsUsed == numBlankets)
			return cows[cowsUsed] + calculate(cowsUsed + 1, blanketsUsed);
		else {
			if (maxUnhappiness[cowsUsed][blanketsUsed] != -1)
				return maxUnhappiness[cowsUsed][blanketsUsed];
			else {
				int giveCow = Math.max(Math.abs(cows[cowsUsed] - blankets[blanketsUsed]), calculate(cowsUsed + 1, blanketsUsed + 1));
				int nextCow = Math.max(cows[cowsUsed], calculate(cowsUsed + 1, blanketsUsed));
				int nextBlankie = calculate(cowsUsed, blanketsUsed + 1);
				int value = Math.min(giveCow, Math.min(nextCow, nextBlankie));
				maxUnhappiness[cowsUsed][blanketsUsed] = value;
				return value;
			}
		}
	}
}
