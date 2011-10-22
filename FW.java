/*
// Thanks to Kevin Oelze for this code...
// _after_ graduating from HMC!

public class FWexample {

   public static void main(String[] args) {
       Scanner s = new Scanner(System.in);
       int t = s.nextInt();
       int c = s.nextInt();
       int ts = s.nextInt() - 1;
       int te = s.nextInt() - 1;
       int[][] array = new int[t][t];
       for (int i = 0; i < t; i++) {
           for (int j = 0; j < t; j++) {
               array[i][j] = 7000000;
           }
       }
       for (int i = 0; i < c; i++) {
           int vert1 = s.nextInt() - 1;
           int vert2 = s.nextInt() - 1;
           int value = s.nextInt();
           array[vert1][vert2] = value;
           array[vert2][vert1] = value;
       }
       //Oh come on, Zach, like I'd use any other algorithm
       for (int k = 0; k < t; k++) {
           for (int i = 0; i < t; i++) {
               for (int j = 0; j < t; j++) {
                   array[i][j] = Math.min(array[i][j],
                                          array[i][k] + array[k][j]);
               }
           }
       }
       System.out.println(array[ts][te]);
   }
}*/
