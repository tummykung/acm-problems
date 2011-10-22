// thanks to Jesse Bellister for this Prim's algorithm code!
//

/********** PRIM ALGORITHM *******************/

import java.util.Scanner;

class cheer {

   public static void main(String[] args) {

       Scanner sc = new Scanner(System.in);

       int numPastures = sc.nextInt();
       int numPaths = sc.nextInt();

       // Whether or not we've considered the vertex yet
       boolean[] inTree = new boolean[numPastures+1];
       // List of min distance to be updated
       int[] distance = new int[numPastures+1];
       // Current vertex under consideration
       int currVertex = -1;
       // Next vertex to be considered
       int nextVertex = -1;
       // Temp variable for holding edge wieghts
       int edgeWeight = -1;
       // Number of verticies leaving each village
       int[] numOutVerts = new int[numPastures+1];
       // Amount of time we need to wait per pasture
       int[] waitTimes = new int[numPastures+1];

       // Note: It's also possible to have a parent array, to actually
       // construct the list of vertices along the min spanning tree,
       // but this isn't necessary in this problem

       for(int i=1; i<numPastures+1; i++) {
           inTree[i] = false;
           distance[i] = 999999;
           numOutVerts[i] = 0;
       }


       Edge[][] graph = new Edge[numPastures+1][numPastures+1];

       // Initialize wait times
       for(int i=1; i<numPastures+1; i++) {
           waitTimes[i] = sc.nextInt();
       }

       int minCost = 99999999;
       int minPasture = -1;

       // Construct the graph
       for(int i=0; i<numPaths; i++) {
           int startPasture = sc.nextInt();
           int endPasture = sc.nextInt();
           int someDist = sc.nextInt();
           numOutVerts[startPasture] += 1;
           int actualCost = (someDist*2) + waitTimes[startPasture]
               + waitTimes[endPasture];

           // Get the pasture with the min wait time, so we know
           // where to start
           if (waitTimes[startPasture] < minCost) {
               minCost = waitTimes[startPasture];
               minPasture = startPasture;
           }
           if (waitTimes[endPasture] < minCost) {
               minCost = waitTimes[endPasture];
               minPasture = endPasture;
           }

           Edge e = new Edge(endPasture, actualCost);
           graph[startPasture][numOutVerts[startPasture]-1] = e;

           // Make sure we get ALL roads, in this case including
           // the road we are going to.
           numOutVerts[endPasture] += 1;

           // Undirected graph, so add in the corresponding edge
           Edge e2 = new Edge(startPasture, actualCost);
           graph[endPasture][numOutVerts[endPasture]-1] = e2;
       }
       // Input read in correctly //


       // Prim's Algorithm!
       // Start with the min vertex
       distance[minPasture] = 0; // We started here, so 0 distance
       currVertex = minPasture;

       while(inTree[currVertex] == false) {
           inTree[currVertex] = true; // We've considered it now
           for(int i=0; i<numOutVerts[currVertex]; i++) {
               nextVertex = graph[currVertex][i].v;
               edgeWeight = graph[currVertex][i].weight;

               // If distance between these two is less than the
               // current min we have on record, then update the
               // distance array with the new min.
               if( (distance[nextVertex] > edgeWeight) &&
                   (inTree[nextVertex] == false)) {
                   distance[nextVertex] = edgeWeight;
               }
           }

           currVertex = minPasture;
           int dist = 999999;
           // Get the next vertex to consider by finding the one which
           // from the ones we have yet to consider that is closest
           // to the starting vertex.
           for(int i=1; i<numPastures+1; i++) {
               if( (inTree[i] == false) && (dist > distance[i])) {
                   dist = distance[i];
                   currVertex = i;
               }
           }

       }

       // Sum up values of the distance array
       int minAnswer = 0;
       for(int i=1; i<numPastures+1; i++) {
           minAnswer += distance[i];
       }

       // Print answer and add in the double wait time for the
       // starting pasture
       System.out.println(minAnswer + waitTimes[minPasture]);
   }
}

// Class representing edges
class Edge {
   int v; // The vertex this edge leads to
   int weight; // The weight of the edge

   Edge(int v, int weight) {
       this.v = v;
       this.weight = weight;
   }
}
