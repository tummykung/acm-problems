import java.util.Arrays;
import java.util.Vector;
import java.util.Scanner;

public class stake {
    private static int numNodes;
    private static int[][] adjacencies;
    private static int[] previous;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int fieldSize = scan.nextInt();
        numNodes = 2 * fieldSize + 2;
        previous = new int[numNodes];
        adjacencies = new int[numNodes][numNodes];

        // Process input by adding links between rows and columns
        for (int row = 0; row < fieldSize; row++)
            for (int col = 0; col < fieldSize; col++){
                int livable = scan.nextInt();
                int rowNode = 1 + row;
                int colNode = 1 + fieldSize + col;
                adjacencies[rowNode][colNode] = livable;
            }

        // Add the links between source and rows.
        for (int row = 0; row < fieldSize; row++){
            int rowNode = 1 + row;
            adjacencies[0][rowNode] = 1;
        }

        // Add the links between columns and sink.
        for (int col = 0; col < fieldSize; col++){
            int colNode = 1 + fieldSize + col;
            adjacencies[colNode][numNodes - 1] = 1;
        }

        int flow = 0;
        while (true) {
            int path = getPath();
            if (path == 0)
                break;
            else
                flow += path;
            //System.out.println("current path's capacity is " + flow);
        }
        System.out.println(flow);
    }

    public static int getPath(){
        Arrays.fill(previous, -1);
        Vector<Integer> nearbyVertices = new Vector<Integer>();
        nearbyVertices.add(0);

        while (nearbyVertices.size() > 0) {
            int start = nearbyVertices.firstElement();
            nearbyVertices.removeElement(start);
            //System.out.println("BFSing node " + start);

            for (int end = 1; end < numNodes; end++) {
                // If node has not already been visited and is reachable, queue
                // it to be visited.
                if (previous[end] == -1 && adjacencies[start][end] > 0) {
                    //System.out.println("Adding link between " + start + " and " + end);
                    previous[end] = start;
                    nearbyVertices.add(end);
                }
            }

            
            // Is last node reachable? Find path and remove.
            if (previous[numNodes - 1] >= 0){
                //System.out.println("Reached last node...");

                int capacity = Integer.MAX_VALUE;
                int prev;
                int end = numNodes - 1;

                // Find the minimum capacity
                while(end != 0){
                    prev = previous[end];
                    capacity = Math.min(capacity, adjacencies[prev][end]);
                    //System.out.println("Backtracing from " + end + " to " + prev + ", whose capacity is " + adjacencies[prev][end]);
                    end = prev;
                }
                
                // Fill up that path
                end = numNodes - 1;
                while(end != 0){
                    prev = previous[end];
                    adjacencies[prev][end] -= capacity;
                    end = prev;
                }
                
                //System.out.println("Capacity of this path is " + capacity);
                return capacity;

            }
        }
        return 0;
    }

}
