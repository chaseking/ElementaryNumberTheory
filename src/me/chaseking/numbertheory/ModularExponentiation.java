package me.chaseking.numbertheory;

import java.util.Scanner;

/**
 * @author Chase King
 */
public class ModularExponentiation {
    private static final boolean DEBUG = false;

    public static void main(String[] args){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Enter the values of base, exponent, and m for the linear congruence statement: base^exponent = x (mod m)");
            long base = scanner.nextLong();
            long exponent = scanner.nextLong();
            long mod = scanner.nextLong();

            solve(base, exponent, mod);
        }
    }

    public static long solve(long base, long exponent, long mod){
        //Step 1: Convert to binary
        boolean[] binary = Binary.convertToBinary(exponent);

        //Print out the binary numbers
        if(DEBUG){
            System.out.print(exponent + " = ");

            for(int i = 0; i < binary.length; i++){
                System.out.print(binary[i] ? "1 " : "0 ");
            }

            System.out.println();
        }

        //Step 2: Table out the powers and least residue
        long[] leastResidues = new long[binary.length];

        for(int i = 0; i < binary.length; i++){
            leastResidues[i] = (i == 0 ? base : leastResidues[i - 1] * leastResidues[i - 1]) % mod;

            if(DEBUG){
                System.out.print(base + "^" + ((int) Math.pow(2, i)) + " = ");

                if(i != 0){
                    System.out.print(leastResidues[i - 1] + "^2 = ");
                }

                System.out.println(leastResidues[i] + " (mod " + mod + ")");
            }
        }

        long solution = 1;

        for(int i = 0; i < binary.length; i++){
            if(binary[binary.length - 1 - i]){
                solution *= leastResidues[i];
                solution %= mod; //Mod the solution after multiplying by the least residue to prevent capping out the long
            }
        }

        if(DEBUG) System.out.println("Least residue solution: " + solution);

        return solution;
    }
}