import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.Math;

public class books {

	private static int B;
	private static int W;
	private static long[][][] costs;
	private static long[] P;
	private static long[] R;
	
	public static void main(String[] args) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			String st = in.readLine();
            StringTokenizer tokenizer = new StringTokenizer(st);
            B = Integer.parseInt(tokenizer.nextToken());
            W = Integer.parseInt(tokenizer.nextToken());
            P = new long[B];
            R = new long[B];
            
            st = in.readLine();
            tokenizer = new StringTokenizer(st);
            for(int book = 0; book < B; book++){
            	P[book] = Long.parseLong(tokenizer.nextToken());
            }
            
            st = in.readLine();
            tokenizer = new StringTokenizer(st);
            for(int book = 0; book < B; book++){
            	R[book] = Long.parseLong(tokenizer.nextToken());
            }
            costs = new long[W][B][B]; // number of workers fired, 
            // number of books done, current wage level
            
            for (int i = 0; i < W; i++)
            	for(int j = 0; j < B; j++)
            		for (int k = 0; k < B; k++)
            			costs[i][j][k] = -2;
            
            System.out.println(calculate(0, 0, 0));
            
		}
		catch (java.io.IOException e) {
		}
	}
	
	private static long calculate(int workersFired, int booksDone, int wageLevel){
		// long largeNo = 10000000L * 10000000L * 200L * 100L;
		
		if (booksDone == B)
			return 0;
		
		else{
			/*System.out.println("workers fired: " + workersFired);
			System.out.println("books done: " + booksDone);
			System.out.println("wage level: " + wageLevel);*/
			long value = costs[workersFired][booksDone][wageLevel];
			
			if (value != -2)
				return value;
			
			else{
				long useIt = calculate(workersFired, booksDone + 1, wageLevel + 1) + P[booksDone] * R[wageLevel];
				
				if (workersFired == W - 1) // only one worker left
					value = useIt;
				else // multiple workers left
					{
					long loseIt = calculate(workersFired + 1, booksDone, 0);
					value = Math.min(useIt, loseIt);
					}
				
				costs[workersFired][booksDone][wageLevel] = value;
				return value;
			}
		}
	}
}