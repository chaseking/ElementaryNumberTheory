package me.chaseking.numbertheory;

import java.util.Scanner;

/**
 * @author Chase King
 */
public class Modular {
    public static void main(String[] args){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.print("Please enter a number: ");
            int number = scanner.nextInt();
            System.out.print("Please enter a modulus: ");
            int modulus = scanner.nextInt();

            try{
                int inverse = getModularInverse(number, modulus);

                System.out.println("The multiplicative inverse of " + number + " (mod " + modulus + ") is " + inverse);
                System.out.println(number + " * " + inverse + " = 1 (mod " + modulus + ")");
            } catch(ArithmeticException e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Get the modular inverse of [value] * [inverse] = 1 (mod M)
     *
     * @param value
     * @param modulus
     * @return
     */
    public static int getModularInverse(int value, int modulus){
        long[] gcd = GCD.gcd(value, modulus); //Call the GCD method and store [GCD, X, Y] in an array

        if(gcd[0] == 1){
//            System.out.println("[DEBUG] " + gcd[0] + " = " + value + "*" + gcd[1] + " + " + modulus + "*" + gcd[2]);

            int inverse = (int) gcd[1]; //The inverse is the coefficient of the value is the multiplicative inverse because: value*X + mod*Y = GCD = 1

            //Ensure that the inverse is between [0, modulus)
            while(inverse < 0) inverse += modulus;
            while(inverse >= modulus) inverse -= modulus;

            return inverse;
        } else {
            //If the GCD is not 1, then value and modulus are not relatively prime.
            //Therefore, there is no modular inverse.
            throw new ArithmeticException("There is no multiplicative inverse for " + value + " (mod " + modulus + ")!");
        }
    }
}