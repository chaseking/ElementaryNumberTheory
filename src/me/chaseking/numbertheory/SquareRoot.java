package me.chaseking.numbertheory;

/**
 * @author Chase King
 */
public class SquareRoot {
    public static final double THRESHOLD = Math.pow(10, -5);

    public static void main(String[] args){
        System.out.println("Square root of 30 is " + sqrt(30));
    }

    public static double sqrt(double number){
        double lowerBound = 0;
        double upperBound = number;
        int step = 1;

        while(true){
            double middle = (lowerBound + upperBound) / 2;
            double squared = Math.pow(middle, 2);

            System.out.println("Step " + step++ + ": " + "[" + lowerBound + ", " + upperBound + "]");

            if(Math.abs(squared - number) <= THRESHOLD){
                return middle;
            } else if(squared > number){
                upperBound = middle;
            } else {
                lowerBound = middle;
            }
        }
    }
}