package me.chaseking.numbertheory;

import java.io.*;
import java.util.*;

public class Main {
    public static final char[] ALPHABET_UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] ALPHABET_LOWER = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final Random RANDOM = new Random();

    public static void main(String[] args){
//        KeyEncryption.main(args);
//        SubstitutionDecryption.main(args);
//        MagicSquares.main(args);
//        SquareRoot.main(args);
//        Fibonacci.main(args);
//        Affine.main(args);
//        EasterProgram.main(args);
//        Playground.main(args);
//        Unit3Test.main(args);
//        GCD.main(args);
//        TriangleArea.main(args);
//        Modular.main(args);
//        FrequencyAnalysisChallenge.main(args);
//        VigenereSquare.main(args);
//        Sorting.main(args);
//        Primes.main(args);
//        LinearCongruence.main(args);
//        ModularExponentiation.main(args);
        RSA.main(args);
    }

    public static Set<String> getEnglishWords(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("./words.txt"));
            Set<String> words = new HashSet<>();
            String word;

            while((word = reader.readLine()) != null){
                words.add(word);
            }

            return words;
        } catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public static int getAlphabetIndex(char letter){
        letter = Character.toLowerCase(letter);

        for(int i = 0; i < ALPHABET_LOWER.length; i++){
            if(ALPHABET_LOWER[i] == letter){
                return i;
            }
        }

        return -1;
    }
}
