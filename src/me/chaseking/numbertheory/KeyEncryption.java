package me.chaseking.numbertheory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Chase King
 */
public class KeyEncryption {
    public static void main(String[] args) {
        List<Character> alphabet = new ArrayList<>();

        for(char c : Main.ALPHABET_UPPER){
            alphabet.add(c);
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter key phrase:");
        String keyPhrase = scanner.nextLine().toUpperCase();

        List<Character> encryptedAlphabet = new ArrayList<>(Main.ALPHABET_UPPER.length);

        //Strip all repeated characters
        for(int i = 0; i < keyPhrase.length(); i++){
            char keyChar = keyPhrase.charAt(i);

            //Ignore duplicates
            if(encryptedAlphabet.contains(keyChar)){
                continue;
            }

            encryptedAlphabet.add(keyChar);
        }

        //Add remainder of alphabet
        int startIndex = alphabet.indexOf(encryptedAlphabet.get(encryptedAlphabet.size() - 1)) + 1;

        while(encryptedAlphabet.size() < Main.ALPHABET_UPPER.length){
            char c = Main.ALPHABET_UPPER[startIndex++ % Main.ALPHABET_UPPER.length];

            if(encryptedAlphabet.contains(c)){
                continue;
            }

            encryptedAlphabet.add(c);
        }

        System.out.println();
        System.out.println("Encrypted alphabet:");

        for(char c : Main.ALPHABET_UPPER){
            System.out.print(" ");
            System.out.print(c);
        }

        System.out.println();

        for(char c : encryptedAlphabet){
            System.out.print(" ");
            System.out.print(c);
        }

        System.out.println();
        System.out.println();
        System.out.println("Enter a message to encrypt:");

        String message = scanner.nextLine();

        System.out.println();
        System.out.println("Cyphertext:");

        for(char c : message.toCharArray()){
            if(Character.isLetter(c)){
                int alphabetIndex = alphabet.indexOf(Character.toUpperCase(c));
                char encryptedChar = encryptedAlphabet.get(alphabetIndex);

                System.out.print(matchCase(encryptedChar, c));
            } else {
                System.out.print(c);
            }
        }
    }

    private static char matchCase(char input, char match){
        if(Character.isUpperCase(match)){
            return Character.toUpperCase(input);
        } else {
            return Character.toLowerCase(input);
        }
    }
}