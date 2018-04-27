package me.chaseking.numbertheory;

import java.util.Scanner;
import java.util.function.Function;

/**
 * @author Chase King
 */
public class Affine {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int a, b;

        do {
            System.out.println("Enter A value (between 0 and 25):");
            a = scanner.nextInt() % 26; //Making sure it is between 0 and 25 (ignoring negatives for now)
        } while(a != 0 && GCD.gcd(a, 26)[0] != 1); //A must be relatively prime with 26

        System.out.println("Enter B value:");
        b = scanner.nextInt();

        System.out.println("Enter phrase you want encoded:");
        scanner.nextLine(); //Apparently the line break needs to be purged to properly get strings... (https://stackoverflow.com/questions/23450524/java-scanner-doesnt-wait-for-user-input)

        String plaintext = scanner.nextLine();

        //Since a and b are manipulated before, they must be declared final to be used in the lambda method
        final int finalA = a;
        final int finalB = b;

        //Print out the encrypted text
        System.out.println(encode(plaintext, p -> (finalA * p) + finalB));
    }

    public static String encode(String plaintext, Function<Integer, Integer> encryption){
        plaintext = plaintext.toUpperCase();
        StringBuilder ciphertext = new StringBuilder(); //StringBuilders are more efficient than using += every time. Appending Strings means the compiler creates a new StringBuilder.

        for(char c : plaintext.toCharArray()){ //Loop through all characters in plaintext
            if(Character.isAlphabetic(c)){
                //Only encrypt if a letter
                int plainIndex = (int) c - 65; //Since A = 65 in ASCII
                int cipherIndex = encryption.apply(plainIndex) % 26; //Since A = 65 in ASCII

                ciphertext.append((char) (cipherIndex + 65));
            } else {
                //If it's not a letter (space, punctuation, number, etc.), then append it normally
                ciphertext.append(c);
            }
        }

        //Return the compiled ciphertext string
        return ciphertext.toString();
    }
}