import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class outofhay {
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
		               d[i][j] = Integer.MAX_VALUE;
		           }
		           d[i][i] = 0;
		       }
			
			// add in the roads
			for(int i = 0; i < K ; i++){
				tokenizer = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(tokenizer.nextToken());
				int b = Integer.parseInt(tokenizer.nextToken());
				int distance = Integer.parseInt(tokenizer.nextToken());
				d[a - 1][b - 1] = Math.min(d[a - 1][b - 1], distance);
				d[b - 1][a - 1] = d[a - 1][b -1];
			}
			
			
			// ================ SETUP ====================	 
			
		       // FW
		       for (int k = 0; k < N; k++) {
		           for (int i = 0; i < N; i++) {
		               for (int j = 0; j < N; j++) {
		                   d[i][j] = Math.min(d[i][j], Math.max(d[i][k], d[k][j]));
		               }
		           }
		       }
		       // now we have all-pairs distance
		       
		   	// Test Input
				/*for (int i = 0; i < K; i++) {
			           for (int j = 0; j < K; j++) {
			               System.out.print(d[i][j] + " ");
			           }
			           System.out.println();
			       }*/
			       
		    
			//=================== RUN ====================
		    int distance = 0;
		    for (int i = 0; i < N; i++) {
		    	distance = Math.max(d[0][i], distance);
		    }
		    System.out.println(distance);
		    	
			// =================== OUTPUT ==================
			//System.out.println(step); // back to the mathematician counting!
		} // Write before this line!
		catch (Exception e) {
			System.out.println("throwing up");
			System.out.println(e.toString());
			
		}
	}
}