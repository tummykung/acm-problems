
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class obstacle {

    public static void main(String[] args){
        // Setup
        Scanner s = new Scanner(System.in);
        while (true){
            int N = s.nextInt();
            if (N == 0)
                break;
                    int[] heights = new int[N*N];
        int[] distances = new int[N*N];
        ArrayList<Point> unvisited = new ArrayList<Point>();
        for (int x = 0; x < N; x++)
            for (int y = 0; y < N; y++){
                Point p = new Point(x, y, N);
                distances[p.index] = 10000000; // large number
                heights[p.index] = s.nextInt();
                unvisited.add(p);
            }
        distances[0] = heights[0];
        
        while (!unvisited.isEmpty()) {
            // Calculate nearest node and remove it
            Point intermediate = unvisited.get(0);
            for (Point p : unvisited) {
                if (distances[p.index] < distances[intermediate.index]) {
                    intermediate = p;
                }
            }
            unvisited.remove(intermediate);
            if (intermediate.index == (N*N - 1))
                break;
            
            // Put neighbours of min point nearer
            for (Point p : intermediate.neighbours()){
                int alternate = distances[intermediate.index] + heights[p.index];
                distances[p.index] = Math.min(alternate, distances[p.index]);
            }
        }
        System.out.println(distances[N*N-1]);
        }
    }
    
    public static class Point {
        public int x;
        public int y;
        public int N;
        public int index;
        
        public Point(int x, int y, int N){
            this.x = x;
            this.y = y;
            this.N = N;
            this.index = x*N + y;
        }
        
        public Set<Point> neighbours(){
            Set<Point> neighbours = new HashSet<Point>();
            if (x > 0)
                neighbours.add(new Point(x-1, y, N));
            if (x < N - 1)
                neighbours.add(new Point(x+1, y, N));
            if (y > 0)
                neighbours.add(new Point(x, y-1, N));
            if (y < N - 1)
                neighbours.add(new Point(x, y+1, N));
            return neighbours;
        }
    }
}
