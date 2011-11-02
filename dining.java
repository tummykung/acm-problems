import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class dining {

    private static int numCows;
    private static int numFoods;
    private static int numDrinks;
    private static int numNodes;

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        numCows = s.nextInt();
        numFoods = s.nextInt();
        numDrinks = s.nextInt();
        numNodes = numCows * numFoods * numDrinks;

        // Objective function
        double[] c = new double[numNodes];
        Arrays.fill(c, 1);

        ArrayList<double[]> A = new ArrayList<double[]>();
        ArrayList<Double> b = new ArrayList<Double>();

        // System.out.println("Generating food constraints");
        // Constrain each food can only be consumed once
        for (int food = 0; food < numFoods; food++) {
            double[] constraint = new double[numNodes];
            Arrays.fill(constraint, 0);
            for (int drink = 0; drink < numDrinks; drink++) {
                for (int cow = 0; cow < numCows; cow++) {
                    constraint[index(cow, food, drink)] = 1;
                }
            }
            A.add(constraint);
            b.add(1.0);
        }

        // System.out.println("Generating drink constraints");
        // Constrain each drink can only be consumed once
        for (int drink = 0; drink < numDrinks; drink++) {
            double[] constraint = new double[numNodes];
            Arrays.fill(constraint, 0);
            for (int food = 0; food < numFoods; food++) {
                for (int cow = 0; cow < numCows; cow++) {
                    constraint[index(cow, food, drink)] = 1;
                }
            }
            A.add(constraint);
            b.add(1.0);
        }

        // Read data for each cow
        boolean[] acceptableFoods = new boolean[numFoods];
        boolean[] acceptableDrinks = new boolean[numDrinks];
        for (int cow = 0; cow < numCows; cow++) {
            // System.out.println("Generating cow constraints");
            int foods = s.nextInt();
            int drinks = s.nextInt();

            for (int food = 0; food < foods; food++) {
                acceptableFoods[s.nextInt() - 1] = true;
            }

            for (int drink = 0; drink < drinks; drink++) {
                acceptableDrinks[s.nextInt() - 1] = true;
            }

            // Constrain each cow can only have one meal
            double[] singleMeal = new double[numNodes];
            for (int food = 0; food < numFoods; food++) {
                for (int drink = 0; drink < numDrinks; drink++) {
                    singleMeal[index(cow, food, drink)] = 1;
                }
            }
            A.add(singleMeal);
            b.add(1.0);

            // Constrain each cow can only have meals it likes
            for (int food = 0; food < numFoods; food++) {
                for (int drink = 0; drink < numDrinks; drink++) {
                    double[] constraint = new double[numNodes];
                    Arrays.fill(constraint, 0);
                    constraint[index(cow, food, drink)] = 1;
                    A.add(constraint);
                    double possible = (acceptableFoods[food] && acceptableDrinks[drink]) ? 1 : 0;
                    b.add(possible);
                }
            }
        }
        double[][] AArray = new double[A.size()][numNodes];
        A.toArray(AArray);
        double[] bArray = new double[b.size()];
        int i = 0;
        for (Double bValue : b) {
            bArray[i++] = bValue.doubleValue();
        }

        diningSimplex solver = new diningSimplex(AArray, bArray, c);
        System.out.println(new Double(solver.value()).intValue());
    }

    private static int index(int cow, int food, int drink) {
        return cow * numFoods * numDrinks + food * numDrinks + drink;
    }

    public static class diningSimplex {

        private static final double EPSILON = 1.0E-10;
        private double[][] a;   // tableaux
        private int M;          // number of constraints
        private int N;          // number of original variables
        private int[] basis;    // basis[i] = basic variable corresponding to row i
        // only needed to print out solution, not book

        // sets up the diningSimplex tableaux
        public diningSimplex(double[][] A, double[] b, double[] c) {
            M = b.length;
            N = c.length;
            a = new double[M + 1][N + M + 1];
            for (int i = 0; i < M; i++) {
                System.arraycopy(A[i], 0, a[i], 0, N);
            }
            for (int i = 0; i < M; i++) {
                a[i][N + i] = 1.0;
            }
            System.arraycopy(c, 0, a[M], 0, N);
            for (int i = 0; i < M; i++) {
                a[i][M + N] = b[i];
            }

            basis = new int[M];
            for (int i = 0; i < M; i++) {
                basis[i] = N + i;
            }

            solve();

            // check optimality conditions
            assert check(A, b, c);
        }

        // run diningSimplex algorithm starting from initial BFS
        private void solve() {
            while (true) {

                // find entering column q
                int q = bland();
                if (q == -1) {
                    break;  // optimal
                }
                // find leaving row p
                int p = minRatioRule(q);
                if (p == -1) {
                    throw new RuntimeException("Linear program is unbounded");
                }

                // pivot
                pivot(p, q);

                // update basis
                basis[p] = q;
            }
        }

        // lowest index of a non-basic column with a positive cost
        private int bland() {
            for (int j = 0; j < M + N; j++) {
                if (a[M][j] > 0) {
                    return j;
                }
            }
            return -1;  // optimal
        }

        // index of a non-basic column with most positive cost
        private int dantzig() {
            int q = 0;
            for (int j = 1; j < M + N; j++) {
                if (a[M][j] > a[M][q]) {
                    q = j;
                }
            }

            if (a[M][q] <= 0) {
                return -1;  // optimal
            } else {
                return q;
            }
        }

        // find row p using min ratio rule (-1 if no such row)
        private int minRatioRule(int q) {
            int p = -1;
            for (int i = 0; i < M; i++) {
                if (a[i][q] <= 0) {
                    continue;
                } else if (p == -1) {
                    p = i;
                } else if ((a[i][M + N] / a[i][q]) < (a[p][M + N] / a[p][q])) {
                    p = i;
                }
            }
            return p;
        }

        // pivot on entry (p, q) using Gauss-Jordan elimination
        private void pivot(int p, int q) {

            // everything but row p and column q
            for (int i = 0; i <= M; i++) {
                for (int j = 0; j <= M + N; j++) {
                    if (i != p && j != q) {
                        a[i][j] -= a[p][j] * a[i][q] / a[p][q];
                    }
                }
            }

            // zero out column q
            for (int i = 0; i <= M; i++) {
                if (i != p) {
                    a[i][q] = 0.0;
                }
            }

            // scale row p
            for (int j = 0; j <= M + N; j++) {
                if (j != q) {
                    a[p][j] /= a[p][q];
                }
            }
            a[p][q] = 1.0;
        }

        // return optimal objective value
        public double value() {
            return -a[M][M + N];
        }

        // return primal solution vector
        public double[] primal() {
            double[] x = new double[N];
            for (int i = 0; i < M; i++) {
                if (basis[i] < N) {
                    x[basis[i]] = a[i][M + N];
                }
            }
            return x;
        }

        // return dual solution vector
        public double[] dual() {
            double[] y = new double[M];
            for (int i = 0; i < M; i++) {
                y[i] = -a[M][N + i];
            }
            return y;
        }

        // is the solution primal feasible?
        private boolean isPrimalFeasible(double[][] A, double[] b) {
            double[] x = primal();

            // check that x >= 0
            for (int j = 0; j < x.length; j++) {
                if (x[j] < 0.0) {
                    System.out.println("x[" + j + "] = " + x[j] + " is negative");
                    return false;
                }
            }

            // check that Ax <= b
            for (int i = 0; i < M; i++) {
                double sum = 0.0;
                for (int j = 0; j < N; j++) {
                    sum += A[i][j] * x[j];
                }
                if (sum > b[i] + EPSILON) {
                    System.out.println("not primal feasible");
                    System.out.println("b[" + i + "] = " + b[i] + ", sum = " + sum);
                    return false;
                }
            }
            return true;
        }

        // is the solution dual feasible?
        private boolean isDualFeasible(double[][] A, double[] c) {
            double[] y = dual();

            // check that y >= 0
            for (int i = 0; i < y.length; i++) {
                if (y[i] < 0.0) {
                    System.out.println("y[" + i + "] = " + y[i] + " is negative");
                    return false;
                }
            }

            // check that yA >= c
            for (int j = 0; j < N; j++) {
                double sum = 0.0;
                for (int i = 0; i < M; i++) {
                    sum += A[i][j] * y[i];
                }
                if (sum < c[j] - EPSILON) {
                    System.out.println("not dual feasible");
                    System.out.println("c[" + j + "] = " + c[j] + ", sum = " + sum);
                    return false;
                }
            }
            return true;
        }

        // check that optimal value = cx = yb
        private boolean isOptimal(double[] b, double[] c) {
            double[] x = primal();
            double[] y = dual();
            double value = value();

            // check that value = cx = yb
            double value1 = 0.0;
            for (int j = 0; j < x.length; j++) {
                value1 += c[j] * x[j];
            }
            double value2 = 0.0;
            for (int i = 0; i < y.length; i++) {
                value2 += y[i] * b[i];
            }
            if (Math.abs(value - value1) > EPSILON || Math.abs(value - value2) > EPSILON) {
                System.out.println("value = " + value + ", cx = " + value1 + ", yb = " + value2);
                return false;
            }

            return true;
        }

        private boolean check(double[][] A, double[] b, double[] c) {
            return isPrimalFeasible(A, b) && isDualFeasible(A, c) && isOptimal(b, c);
        }

        // print tableaux
        public void show() {
            System.out.println("M = " + M);
            System.out.println("N = " + N);
            for (int i = 0; i <= M; i++) {
                for (int j = 0; j <= M + N; j++) {
                    System.out.printf("%7.2f ", a[i][j]);
                }
                System.out.println();
            }
            System.out.println("value = " + value());
            for (int i = 0; i < M; i++) {
                if (basis[i] < N) {
                    System.out.println("x_" + basis[i] + " = " + a[i][M + N]);
                }
            }
            System.out.println();
        }

        /*public static void test(double[][] A, double[] b, double[] c) {
            diningSimplex lp = new diningSimplex(A, b, c);
            System.out.println("value = " + lp.value());
            double[] x = lp.primal();
            for (int i = 0; i < x.length; i++) {
                System.out.println("x[" + i + "] = " + x[i]);
            }
            double[] y = lp.dual();
            for (int j = 0; j < y.length; j++) {
                System.out.println("y[" + j + "] = " + y[j]);
            }
        }

        public static void test1() {
            double[][] A = {
                {-1, 1, 0},
                {1, 4, 0},
                {2, 1, 0},
                {3, -4, 0},
                {0, 0, 1},};
            double[] c = {1, 1, 1};
            double[] b = {5, 45, 27, 24, 4};
            test(A, b, c);
        }

        // x0 = 12, x1 = 28, opt = 800
        public static void test2() {
            double[] c = {13.0, 23.0};
            double[] b = {480.0, 160.0, 1190.0};
            double[][] A = {
                {5.0, 15.0},
                {4.0, 4.0},
                {35.0, 20.0},};
            test(A, b, c);
        }

        // unbounded
        public static void test3() {
            double[] c = {2.0, 3.0, -1.0, -12.0};
            double[] b = {3.0, 2.0};
            double[][] A = {
                {-2.0, -9.0, 1.0, 9.0},
                {1.0, 1.0, -1.0, -2.0},};
            test(A, b, c);
        }

        // degenerate - cycles if you choose most positive objective function coefficient
        public static void test4() {
            double[] c = {10.0, -57.0, -9.0, -24.0};
            double[] b = {0.0, 0.0, 1.0};
            double[][] A = {
                {0.5, -5.5, -2.5, 9.0},
                {0.5, -1.5, -0.5, 1.0},
                {1.0, 0.0, 0.0, 0.0},};
            test(A, b, c);
        }

        // test client
        public static void main(String[] args) {

            try {
                test1();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("--------------------------------");

            try {
                test2();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("--------------------------------");

            try {
                test3();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("--------------------------------");

            try {
                test4();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("--------------------------------");


            int M = Integer.parseInt(args[0]);
            int N = Integer.parseInt(args[1]);
            double[] c = new double[N];
            double[] b = new double[M];
            double[][] A = new double[M][N];
            for (int j = 0; j < N; j++) {
                c[j] = (new Random()).nextDouble() * 1000;
            }
            for (int i = 0; i < M; i++) {
                b[i] = (new Random()).nextDouble() * 1000;
            }
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    A[i][j] = (new Random()).nextDouble() * 1000;
                }
            }
            diningSimplex lp = new diningSimplex(A, b, c);
            System.out.println(lp.value());
        }*/
    }
}