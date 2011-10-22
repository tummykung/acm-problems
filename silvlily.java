import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class silvlily {
	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			//================ INPUT ===============
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int N = Integer.parseInt(tokenizer.nextToken()); // no of farms
			
			int K = Integer.parseInt(tokenizer.nextToken()); // no of roads
			
			int d[][] = new int[N][N];
			
			// fill out the distance matrices
			for (int i = 0; i < N; i++) {
		           for (int j = 0; j < N; j++) {
		               d[i][j] = 10000;
		           }
		           d[i][i] = 0;
		       }
			
			// add in the roads
			for(int i = 0; i < K ; i++){
				tokenizer = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(tokenizer.nextToken());
				int b = Integer.parseInt(tokenizer.nextToken());
				int distance = Integer.parseInt(tokenizer.nextToken());
				d[a - 1][b - 1] = distance;
				d[b - 1][a - 1] = distance;
			}
			
			
			// ================ SETUP ====================	 
			
		       // FW
		       for (int k = 0; k < N; k++) {
		           for (int i = 0; i < N; i++) {
		               for (int j = 0; j < N; j++) {
		                   d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
		               }
		           }
		       }
		       // now we have all-pairs distance, so generate the second-smallest paths
		   	// Test Input
				/*for (int i = 0; i < K; i++) {
			           for (int j = 0; j < K; j++) {
			               System.out.print(d[i][j] + " ");
			           }
			           System.out.println();
			       }*/
			       
		    
			//=================== RUN ====================
		    int shortestDistance = d[0][N-1];
		    int secondShortest = Integer.MAX_VALUE;
		    for (int i = 0; i < N; i++) {
		    	int putativeSecondShortest = d[0][i] + d[i][N-1];
		    	if (putativeSecondShortest > shortestDistance)
		    		secondShortest = Math.min(putativeSecondShortest, secondShortest);
		    }
		    System.out.println(secondShortest);
		    
		    	
			// =================== OUTPUT ==================
			//System.out.println(step); // back to the mathematician counting!
		} // Write before this line!
		catch (Exception e) {
			System.out.println("throwing up");
			System.out.println(e.toString());
			
		}
	}
}