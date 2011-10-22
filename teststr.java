import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.lang.Math;

public class teststr {
	
	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			String st = in.readLine();
			StringTokenizer tokenizer = new StringTokenizer(st);
			int N = Integer.parseInt(tokenizer.nextToken());
			int K = Integer.parseInt(tokenizer.nextToken());
			int s[] = new int[K];
			for(int i = 0 ;i<K ; i++){
				s[i] = N - Integer.parseInt(in.readLine());
			}
		
			Arrays.sort(s);
			int maxCorrect = 0;
			
			for(int i = 0 ; i< K - 1; i++){
				int height = Math.abs((s[i+1] - s[i])/2);
				maxCorrect = Math.max(maxCorrect, height);
			}
			// Find min, max for guessing all T/all F
			int minK = N;
			int maxK = 0;
			for(int i = 0 ; i < K ; i++)
			{
				if(s[i] < minK)
					minK = s[i];
				if(s[i] > maxK)
					maxK= s[i];
			}
			int maxCandidate = N - maxK;
			maxCorrect = Math.max(maxCorrect, maxCandidate);
			maxCorrect = Math.max(maxCorrect, minK);
			System.out.println(maxCorrect);
		}
		catch (Exception e) {
			System.out.println("throwing up");
			System.out.println(e.toString());
		}
	}
	

}
