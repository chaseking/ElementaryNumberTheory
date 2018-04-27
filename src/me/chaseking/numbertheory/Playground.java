package me.chaseking.numbertheory;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Chase King
 */
public class Playground {
    public static void main(String[] args){
//        for(int i = 10000000; i <= 99999999; i++){
//            for(int n = 1; n <= 10; n++){
//                int result = (int) (Math.pow((i + n), 2) - Math.pow(i, 2));
//                int digits = allSameDigits(result);
//
//                if(digits != -1){
//                    System.out.println((i + n) + " and " + i + " (" + result + ")");
//                }
//            }
//        }

//        System.out.println("LCM of 5 and 8 is " + lcm(5, 8));
//        System.out.println("LCM of 5 and 25 is " + lcm(5, 25));
//        System.out.println("Reversed digits in 1234 is " + reverseDigits(1234));
//        System.out.println("Reversed digits in 5001 is " + reverseDigits(5001));
//        digitInfo(123450741);
//        //guessingGame();
//        System.out.println("Max product of [10,3,1,9,2] is " + maxNonconsecutiveProduct(new int[]{10,3,1,9,2})); //10*9 = 90
//        System.out.println("Max product of [5,10,5,10,5] is " + maxNonconsecutiveProduct(new int[]{5,10,5,10,5})); //5*5*5 = 125
//        System.out.println("Possible stair ways of 3 is " + countStairWays(3));
//        System.out.println("Possible stair ways of 4 is " + countStairWays(4));
    }

    public static int countStairWays(int n){
        if(n == 1){
            return 1;
        } else if(n == 2){
            return 2;
        } else {
            return countStairWays(n - 1) + countStairWays(n - 2);
        }
    }

    public static int maxNonconsecutiveProduct(int[] list){
        if(list.length == 0){
            return 1;
        } else if(list.length == 1){
            return list[0];
        } else {
            return Math.max(maxNonconsecutiveProduct(Arrays.copyOfRange(list, 1, list.length)), list[0] * maxNonconsecutiveProduct(Arrays.copyOfRange(list, 2, list.length)));
        }
    }

    public static void guessingGame(){
        int number = Main.RANDOM.nextInt(100) + 1;
        Scanner scanner = new Scanner(System.in);
        int numGuesses = 0;

        while(true){
            System.out.println("Guess a number:");
            int guess = scanner.nextInt();
            numGuesses++;

            if(guess == number){
                System.out.println("Ding ding! You guessed the number after " + numGuesses + " " + (numGuesses == 1 ? "try" : "tries") + "!");
                break;
            } else if(guess < number){
                System.out.println("Too low!");
            } else {
                System.out.println("Too high!");
            }
        }
    }

    public static void digitInfo(long number){
        int odd = 0;
        int even = 0;
        int zero = 0;

        while(number != 0){
            int lastDigit = (int) (number % 10);

            if(lastDigit % 2 == 1) odd++;
            if(lastDigit % 2 == 0 && lastDigit != 0) even++;
            if(lastDigit == 0) zero++;

            number = (number - lastDigit) / 10;
        }

        System.out.println("The number " + number + " has " + odd + " odd digits, " + even + " non-zero even digits, and " + zero + " zero digits.");
    }

    public static int lcm(int a, int b){
        int lcm = (a < b ? a : b);

        while(lcm % a != 0 || lcm % b != 0){
            lcm++;
        }

        return lcm;
        //Or just
        //return (a * b) / gcd(a, b);
    }

    public static int gcd(int a, int b){
        if(b == 0){
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    public static int reverseDigits(int number){
        int result = 0;

        while(number != 0){
            int lastDigit = number % 10;

            result = (result * 10) + lastDigit;
            number = (number - lastDigit) / 10;
        }

        return result;
        //return Integer.valueOf(new StringBuilder(String.valueOf(1234)).reverse().toString());
    }

    public static int allSameDigits(int number){
        int prevLast = -1;

        while(number != 0){
            int lastDigit = number % 10;

            if(prevLast == -1 || prevLast == lastDigit){
                prevLast = lastDigit;
                number = (number - lastDigit) / 10;
            } else {
                return -1;
            }
        }

        return prevLast;
    }

    public static boolean isNumericalPalindrome(int number){
        return reverseDigits(number) == number;
    }
}