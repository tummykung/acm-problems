import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class block {
	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			//================ INPUT ===============
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int N = Integer.parseInt(tokenizer.nextToken()); // no of farms
			
			int K = Integer.parseInt(tokenizer.nextToken()); // no of roads
			
			int d[][] = new int[N][N];
			int s[][] = new int[N][N];
			
			// fill out the distance matrices
			for (int i = 0; i < N; i++) {
		           for (int j = 0; j < N; j++) {
		               d[i][j] = 10000;
		               s[i][j] = 10000;
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
			
			
			// ================ SETUP  ====================	 
			
		       // FW
		       for (int k = 0; k < N; k++) {
		           for (int i = 0; i < N; i++) {
		               for (int j = 0; j < N; j++) {
		            	   int direct = d[i][j];
		            	   int indirect = d[i][k] + d[k][j];
		            	   if (direct < indirect) {
		            		   s[i][j] = Math.min(s[i][j], indirect);
		            	   } else if (indirect < direct) {
		            		   d[i][j] = indirect;
		            		   int possibleSecond = Math.min(s[i][j], direct);
		            		   int possibleSecondIndirect = Math.min(d[i][k] + s[k][j], 
		            				   s[i][k] + d[k][j]);
		            		   s[i][j] = Math.min(possibleSecond, possibleSecondIndirect);
		            	   }   
		               }
		           }
		       }
			       
		    
			//=================== RUN ====================
		    System.out.println(s[0][N - 1]);
		    
		    	
			// =================== OUTPUT ==================
			//System.out.println(step); // back to the mathematician counting!
		} // Write before this line!
		catch (Exception e) {
			System.out.println("throwing up");
			System.out.println(e.toString());
			
		}
	}
}