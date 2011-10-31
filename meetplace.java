import java.util.Arrays;
import java.util.Scanner;

public class meetplace {
    private static int pastures;
    private static int[] parents;
    private static int[] levels;
    
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        pastures = s.nextInt();
        int days = s.nextInt();
        parents = new int[pastures];
        for (int pasture = 1; pasture < pastures; pasture++)
            parents[pasture] = s.nextInt() - 1;
        levels = new int[pastures];
        Arrays.fill(levels, -1);
        levels[0] = 0;
        for (int i = 0; i < pastures; i++){
            computeLevel(i);
        }
        for (int day = 0; day < days; day++){
            int firstPasture = s.nextInt() - 1;
            int secondPasture = s.nextInt() - 1;
            System.out.println(ancestor(firstPasture, secondPasture) + 1);
        }
    }

    private static int computeLevel(int pasture){
        if (levels[pasture] != -1)
            return levels[pasture];
        else {
            int level = 1 + computeLevel(parents[pasture]);
            levels[pasture] = level;
            return level;
        }
    }
    
    private static int ancestor(int first, int second){
        int level = Math.min(levels[first], levels[second]);
        while (levels[first] > level)
            first = parents[first];
        while (levels[second] > level)
            second = parents[second];
        
        while (first != second){
            first = parents[first];
            second = parents[second];
        }
        return first;
    }
}
