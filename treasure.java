import java.io.*;

public class treasure {
	
	private static int N = 0;
	private static int C[] = new int[5000];
	private static int maxi[][] = new int[5000][5000];
	
	public static void main(String[] args){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			N = Integer.parseInt(in.readLine());
			for(int i = 0; i < N; i++)
				C[i] = Integer.parseInt(in.readLine());
		}
		catch (Exception e) {
		}
		System.out.print(count());
	}
	
	private static int count()
	{
		for(int d = 1; d < N; d++){
		   for(int i = 0; i < N - d; i++){
			   calculate(i, d);
		   }
		}
		return maxi[0][N - 1];
	}
	
	private static void calculate(int i, int delta)
	{
		if (delta == 1) 
			maxi[i][delta] = Math.max(C[i], C[i+1]);
		else {
			int leftMax = maxi[i + 1][delta - 1];
			int rightMax = maxi[i][delta - 1];
			int current, theOtherMax;
			if(leftMax > rightMax){
				// choose the right end
				//current = C[i + delta];
				theOtherMax = maxi[i][delta - 1];
			} else {
				//current = C[i];
				theOtherMax = maxi[i + 1][delta - 1];
			}
			maxi[i][delta] = sum(i, delta) - theOtherMax;
		}
	}
	
	private static int sum(int i, int delta)
	{
		int sum = 0;
		for(int k = 0; k <= delta; k++)
			sum += C[i + k];
		return sum;
	}
	
}
