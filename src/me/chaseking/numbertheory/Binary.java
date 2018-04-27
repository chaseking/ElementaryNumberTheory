package me.chaseking.numbertheory;

/**
 * @author Chase King
 */
public class Binary {
    public static boolean[] convertToBinary(long number){
        int length = (int) Math.floor(Math.log(number) / Math.log(2)) + 1;
        boolean[] binary = new boolean[length];

        for(int i = 0; i < length; i++){
            boolean isOne = number % 2 == 1;
            number /= 2;
            binary[length - 1 - i] = isOne;
        }

        return binary;
    }
}