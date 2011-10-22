import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class partacq {
	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			//================ INPUT ===============
			StringTokenizer tokenizer = new StringTokenizer(in.readLine());
			int N = Integer.parseInt(tokenizer.nextToken());
			// 1 <= N <= 50000, 
			
			int K = Integer.parseInt(tokenizer.nextToken());
			// 1 <= K <= 1000,
			
			int d[][] = new int[K][K];
			
			for (int i = 0; i < K; i++) {
		           for (int j = 0; j < K; j++) {
		               d[i][j] = 7000000;
		           }
		           d[i][i] = 0;
		       }
			
			for(int i = 0 ;i<N ; i++){
				tokenizer = new StringTokenizer(in.readLine());
				int a = Integer.parseInt(tokenizer.nextToken());
				int b = Integer.parseInt(tokenizer.nextToken());
				d[a - 1][b - 1]= 1;
			}
			
			
			// ================ SETUP ====================	 
			
		       // FW
		       for (int k = 0; k < K; k++) {
		           for (int i = 0; i < K; i++) {
		               for (int j = 0; j < K; j++) {
		                   d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
		               }
		           }
		       }
		       // now we have all-pairs distance
		       
		   	// Test Input
				/*
				for (int i = 0; i < K; i++) {
			           for (int j = 0; j < K; j++) {
			               System.out.print(d[i][j] + " ");
			           }
			           System.out.println();
			       }
			       */
		    
			//=================== RUN ====================
		    int start = 0;
		    int potentialStart = 0;
		    
		    int distance = d[0][K-1];
		    if(distance == 7000000){
		    	System.out.println("-1");
		        return;
		    }
		    System.out.println(distance + 1);
		    while(distance >= 0){
		    	System.out.println(start + 1);
		    	for(int next = 0; next < K ; next++){
		    		if(d[start][next] == 1 && d[next][K-1] == distance - 1){
		    			potentialStart = next;
		    		}
		    	}
		    	start = potentialStart;
		    	distance--;
		    }
		    
			// =================== OUTPUT ==================
			//System.out.println(step); // back to the mathematician counting!
		} // Write before this line!
		catch (Exception e) {
			System.out.println("throwing up");
			System.out.println(e.toString());
			
		}
	}
}