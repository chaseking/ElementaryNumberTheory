package me.chaseking.numbertheory;

import java.util.Scanner;

/**
 * @author Chase King
 */
public class LinearCongruence {
    public static void main(String[] args){
        //Alternatively use a scanner to get input
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Enter the values of a, b, and n for the linear congruence statement: ax = b (mod n)");
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int mod = scanner.nextInt();
            //42, 14, 385

            solve(a, b, mod);
        }
    }

    public static int[] solve(int a, int b, int mod){
        System.out.println("Finding solutions to: " + a + "x = " + b + " (mod " + mod + ")");
        System.out.println(a + "x - " + b + " = " + mod + "y");
        System.out.println(a + "x - " + mod + "y = " + b);

        long[] gcd = GCD.gcd(a, -mod); //Calculate the GCD, x, and y

        //Check if there actually is a solution
        //No solutions when (a, mod) does not divide into b
        if(b % gcd[0] != 0){
            System.out.println("There are no solutions!");
            throw new ArithmeticException("No solutions");
        }

        //"Scale up" the x and y values to satisfy the solution
        gcd[1] *= b / gcd[0];
        gcd[2] *= b / gcd[0];

        System.out.println();
        System.out.println("Initial solution: " + a + "(" + gcd[1]+ ") - " + mod + "(" + -gcd[2] + ") = " + b);
        System.out.println("x = " + gcd[1] + " + " + mod + "/" + gcd[0] + "t");
        System.out.println();
        System.out.println("  t |  x  |  x (mod " + mod + ")");
        int[] solutions = new int[(int) gcd[0]];

        for(int t = 0; t < gcd[0]; t++){ //The number of solutions is equal to the GCD before the cycle repeats
            int x = (int) (gcd[1] + (mod / gcd[0]) * t); //We only care about the x value (not the y)
            int solution = x % mod;

            while(solution < 0) solution += mod; //Make sure the solution is positive

            System.out.println(String.format("%3s%6s%6s", t, x, solution)); //Print it out in table format
            solutions[t] = solution;
        }

        //Print out the solutions
        System.out.println();
        System.out.println("Solutions to: " + a + "x = " + b + " (mod " + mod + ")");

        //Find the minimum solution
        int minSolution = solutions[0];

        for(int i = 0; i < solutions.length; i++){
            System.out.print("x = " + solutions[i]);

            if(solutions[i] < minSolution) minSolution = solutions[i];

            if(i < solutions.length - 1){
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }

        System.out.println("Minimum solution is x = " + minSolution);

        return solutions;
    }
}