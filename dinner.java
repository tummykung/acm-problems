
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class dinner {
    private static int people;
    private static int numNodes;
    private static int[][] adjacencies;
    private static int[] previous;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        
        while (true) {
            int teams = scan.nextInt();
            if (teams == 0) break;

            int tables = scan.nextInt();
            numNodes = teams + tables + 2;
            
            previous = new int[numNodes];
            adjacencies = new int[numNodes][numNodes];
            people = 0;
            // Add links between source and team
            for (int team = 0; team < teams; team++) {
                int size = scan.nextInt();
                int reIndexTeam = 1 + team;
                adjacencies[0][reIndexTeam] = size;
                adjacencies[reIndexTeam][0] = size;
                people += size;
            }

            // Add link between table and destination
            for (int table = 0; table < tables; table++) {
                int size = scan.nextInt();
                int reIndexTable = 1 + teams + table;
                adjacencies[numNodes-1][reIndexTable] = size;
                adjacencies[reIndexTable][numNodes-1] = size;

                for (int team = 0; team < teams; team++){
                    adjacencies[1+team][reIndexTable] = 1;
                    adjacencies[reIndexTable][1+team] = 1;
                }
            }

            // Compute max flow
            int flow = 0;
            while (true) {
                int path = getPath();
                if (path == 0) {
                    break;
                } else {
                    flow += path;
                }
            }

            if (flow == people)
                System.out.println(1);
            else
                System.out.println(0);
        }

    }

    public static int getPath() {
        Arrays.fill(previous, -1);
        List<Integer> nearbyVertices = new ArrayList<Integer>();
        nearbyVertices.add(0);

        while (nearbyVertices.size() > 0) {
            int start = nearbyVertices.get(0);
            nearbyVertices = nearbyVertices.subList(1, nearbyVertices.size());
            
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
