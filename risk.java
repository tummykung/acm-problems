import java.util.Arrays;
import java.util.Vector;
import java.util.Scanner;
public class risk {

    int numRegions;
    int borderRegions;
    int internalRegions;

    public static void main(String[] args){

    }

    public Boolean isPossible(int strength){
        int[][] matrix = new int[0][0];
        int desiredCapacity = borderRegions * strength + internalRegions;
        int achievableCapacity = (new MaxFlow(0, matrix)).capacity;

        return (desiredCapacity == achievableCapacity);
    }

    public class MaxFlow {
        public int capacity;
        private int numNodes;
        private int[][] adjacencies;
        private int[] previous;

        public MaxFlow(int nodes, int[][] adjMatrix){
            this.numNodes = nodes;
            this.adjacencies = adjMatrix;
            int flow = 0;
            while (true) {
                int path = getPath();
                if (path == 0) {
                    break;
                } else {
                    flow += path;
                }
            }
            this.capacity = flow;
        }
        private int getPath() {
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
                if (previous[numNodes - 1] >= 0) {
                    //System.out.println("Reached last node...");
                    int capacity = Integer.MAX_VALUE;
                    int prev;
                    int end = numNodes - 1;

                    // Find the minimum capacity
                    while (end != 0) {
                        prev = previous[end];
                        capacity = Math.min(capacity, adjacencies[prev][end]);
                        //System.out.println("Backtracing from " + end + " to " + prev + ", whose capacity is " + adjacencies[prev][end]);
                        end = prev;
                    }
                    // Fill up that path
                    end = numNodes - 1;
                    while (end != 0) {
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

}
