package me.chaseking.numbertheory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Chase King
 */
public class Primes {
    public static final int[] FIRST_PRIMES = new int[]{ 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71 };

    public static void main(String[] args){
        int[] primes = getPrimes(10000);

        System.out.println("The 10,000th prime is " + primes[primes.length - 1]);
//        System.out.println(Arrays.toString(primes));
        System.out.println("The full list has been saved in " + new File("primes.txt").getAbsolutePath());

        //Print to file
        try{
            PrintStream out = new PrintStream(new FileOutputStream("primes.txt"));

            //Print out primes here using out.print(...) or out.println(...)

            out.close();
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }


        try(PrintStream out = new PrintStream(new FileOutputStream("primes.txt"))){ //Wrapping it in try() automatically closes the stream
            out.println("The first 10,000 primes:");
            out.println();

            for(int i = 0; i < primes.length; i++){
                out.print(String.format("%8s", primes[i])); //This makes them spaced every 8 characters

                if((i + 1) % 12 == 0){ //12 columns
                    out.println();
                }
            }
        } catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static int[] getPrimes(int length){
        int[] primes = Arrays.copyOf(FIRST_PRIMES, length);

        //We don't need to add any primes
        if(length <= FIRST_PRIMES.length){
            return primes;
        }

        int num = primes[FIRST_PRIMES.length - 1];

        for(int i = FIRST_PRIMES.length; i < primes.length; i++){
            do {
                num += 2;
            } while(!isPrime(num, primes));

            primes[i] = num;
        }

        return primes;
    }

    public static boolean isPrime(int number, int[] primes){
        double sqrt = Math.sqrt(number); //Only run once to increase efficiency

        for(int prime : primes){
            if(prime > sqrt || prime == 0){
                break;
            }

            if(number % prime == 0){
                //Number is composite
                return false;
            }
        }

        return true;
    }

    public static List<Integer> primes(int length){
        List<Integer> primes = new ArrayList<>(length);

        primes.add(2);
        primes.add(3);

        int p = 5;

        while(primes.size() < length){
            boolean isPrime = true;

            for(int prime : primes){
                if(p % prime == 0){
                    isPrime = false;
                    p += 2;
                    break;
                }
            }

            if(isPrime){
                primes.add(p);
            }
        }

        return primes;
    }

    private static void sieveOfEratosthenes(int length){
        /*
         Input: an integer n > 1.

         Let A be an array of Boolean values, indexed by integers 2 to n,
         initially all set to true.

         for i = 2, 3, 4, ..., not exceeding √n:
           if A[i] is true:
             for j = i2, i^2 + i, i^2 + 2i, i^2 + 3i, ..., not exceeding n:
               A[j] := false.

         Output: all i such that A[i] is true.
         */

        int[] isPrime = new int[length];

        isPrime[0] = 0;

        for(int i = 2; i <= length; i++){
            isPrime[i - 1] = 1;
        }

        double sqrt = Math.sqrt(length);

        for(int i = 2; i < sqrt; i++){
            if(isPrime[i - 1] == 1){
                int j = (int) Math.pow(i, 2);

                while(true){
                    if(j > length){
                        break;
                    } else {
                        isPrime[j - 1] = 0;
                        j += i;
                    }
                }
            }
        }

        for(int i = 0; i < length; i++){
            if(isPrime[i] == 1){
                System.out.println((i + 1));
            }
        }
    }
}