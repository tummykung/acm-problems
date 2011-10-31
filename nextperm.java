import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class nextperm {
    public static void main(String[] args) {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try{
        //====  Input =======
        //==== SETUP ========
        StringTokenizer tokenizer = new StringTokenizer(in.readLine());
        int numRuns = Integer.parseInt(tokenizer.nextToken());
        
        //===== RUN ========
        for (int run = 0; run < numRuns; run++) {
            tokenizer = new StringTokenizer(in.readLine());
            tokenizer.nextToken();
            String input = tokenizer.nextToken();

            int[] n = new int[input.length()];
            int[] out = new int[n.length];
            for (int i = 0; i < input.length(); i++) {
                n[i] = input.charAt(i)-'0';
                out[i] = n[i];
            }
            int start = 0;
            if (isDecreasing(n, 0)) {
                System.out.println(""+(run+1)+" BIGGEST");
                continue;
            }
            while (start < n.length) {
//                System.out.println("start is "+start);
                if (!isDecreasing(n, start+1)) {
//                    System.out.println("rest is not decreasing. keeping (start)th digit");
                    start++;
                }
                else {
//                    System.out.println("rest is decreasing. swapping with next largest digit");
                    out = swapWithNextLargestDigit(out, start);
//                    System.out.print("  out is now ");
//                    for (int i = 0; i < out.length; i++) {
//                        System.out.print(out[i]);
//                    }
//                    System.out.println();
    
                    start++;
                    
                    int[] rest = new int[out.length-start];
                    for (int j = start; j < out.length; j++) {
                        rest[j-start] = out[j];
                    }
                    Arrays.sort(rest);
                    for (int j = 0; j < rest.length; j++) {
                        out[start+j] = rest[j];
                    }
                    break;
                }
            }
            //===== OUTPUT =====
            System.out.print(""+(run+1)+" ");
            for (int i = 0; i < out.length; i++) {
                System.out.print(out[i]);
            }
            System.out.println();
        }
        
        } // Write before this line
        catch(Exception e){
            System.out.println("throwing up");
            System.out.println(e);
        }
    }
    
    public static boolean isDecreasing(int[] n, int i) {
        if (n.length <= i) return true;
        
        int x = n[i];
        for (i++; i < n.length; i++) {
            if (n[i] > x) return false;
            x = n[i];
        }
        return true;
    }
    
    public static int[] swapWithNextLargestDigit(int[] n, int i) {
        // Swap the ith position with the next largest from i+1..-1
        // Assumes i+1..-1 is in decreasing order
        int out[] = new int[n.length];
        for (int j = 0; j < n.length; j++) {
            out[j] = n[j];
        }
        
        for (int j = n.length-1; j > i; j--) {
            if (n[j] > n[i]) {
                out[j] = n[i];
                out[i] = n[j];
                break;
            }
        }
        return out;
    }
}
