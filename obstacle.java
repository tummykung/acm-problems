import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class obstacle {

	private static int[] costs;
	private static int N;
	private static Set<Integer> unvisitedNodes;
	private static int[] distances;
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int iteration = 0;
		
		while (true){
			N = s.nextInt();
			if (N == 0)
				break;
			else 
				iteration++;
			
			costs = new int[N * N];
			for (int i = 0; i < N*N; i++)
				costs[i] = s.nextInt();
			
			calculate();
			System.out.println("Problem " + iteration + ": " + 1);
		}
		return;
	}
		
	private static void calculate(){
		unvisitedNodes = new HashSet<Integer>(N*N);
		distances = new int[N*N];
		for (int i = 0; i < N * N; i++){
			unvisitedNodes.add(i);
			distances[i] = 10000000;
		}
		distances[N*N - 1] = costs[N*N - 1];
		while (unvisitedNodes.size() > 0){
			int u = 1;
			
		}
	}
}
