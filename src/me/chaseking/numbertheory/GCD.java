package me.chaseking.numbertheory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chase King
 */
public class GCD {
    public static void main(String[] args){
//        euclid(2145, 462);
//        euclid(15, 21);
//        euclid(1820, 429);
//        euclid(663, 154);
//        euclid(1939938, 14784055);
//        gcd(6, 8);
//        euclid(6, -13);
//        euclid(138, 9864);
        long a = 9864;
        long b = 138;
        long[] gcd = gcd_nolists(a, b);
        System.out.println("The GCD of " + a + " and " + b + " is " + gcd[0]);
        System.out.println(a + "*" + gcd[1] + " + " + b + "*" + gcd[2] + " equals " + gcd[0]);
    }

    /**
     * Calculate the GCD and values of x and y that satisfy the linear combination: ax + by = (a, b)
     * Key relationships:
     *   x[n] = x[n-2] - Q[n]*x[n-1]
     *   y[n] = y[n-2] - Q[n]*y[n-1]
     *
     *   x[0] = 0; x[1] = 1
     *   y[0] = 1; y[1] = -Q[1]
     *
     * @param a First number
     * @param b Second number
     * @return Array storing [ GCD, x, y ]
     */
    public static long[] gcd(long a, long b){
        //Make both numbers ≥ 0
        a = Math.abs(a);
        b = Math.abs(b);

        //Ensure that a ≥ b
        boolean swapped = a < b;
        if(swapped){
            long temp = a;
            a = b;
            b = temp;
        }

        List<Long> q = new ArrayList<>();
        List<Long> r = new ArrayList<>();
        List<Long> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();

        q.add((long) 0);
        r.add(b);
        xList.add((long) 0); // x[0]
        yList.add((long) 1); // y[0]

        int step = 1;

        while(true){
            long remainder = a % b;
            long quotient = (a - remainder) / b;

            a = b;
            b = remainder;

            q.add(quotient);
            r.add(remainder);

            if(step == 1){
                xList.add((long) 1);
                yList.add(-quotient);
            } else {
                xList.add(xList.get(xList.size() - 2) - (quotient * xList.get(xList.size() - 1)));
                yList.add(yList.get(yList.size() - 2) - (quotient * yList.get(yList.size() - 1)));
            }

            step++;

            if(remainder == 0){
                break;
            }
        }

        //Debug the lists
//        System.out.println(String.format("%6s | %6s | %6s | %6s | %6s", "Step", "Q", "R", "x", "y"));
//        for(int i = 0; i < xList.size(); i++){
//            System.out.println(String.format("%6s | %6s | %6s | %6s | %6s", i, q.get(i), r.get(i), xList.get(i), yList.get(i)));
//        }

        //Adjust the order of the returned values depending on the inputs
        if(swapped){
            return new long[]{ r.get(r.size() - 2), yList.get(yList.size() - 2), xList.get(xList.size() - 2) };
        } else {
            return new long[]{ r.get(r.size() - 2), xList.get(xList.size() - 2), yList.get(yList.size() - 2) };
        }
    }

    /**
     * Calculate the GCD and values of x and y that satisfy the linear combination: ax + by = (a, b)
     * Key relationships:
     *   x[n] = x[n-2] - Q[n]*x[n-1]
     *   y[n] = y[n-2] - Q[n]*y[n-1]
     *
     *   x[0] = 0; x[1] = 1
     *   y[0] = 1; y[1] = -Q[1]
     *
     * @param a First number
     * @param b Second number
     * @return Array storing [ GCD, x, y ]
     */
    public static long[] gcd_nolists(long a, long b){
        //Make both numbers ≥ 0
        a = Math.abs(a);
        b = Math.abs(b);

        //Ensure that a ≥ b
        if(a < b){
            long temp = a;
            a = b;
            b = temp;
        }

        //Store all previous x and y variables, along with the last remainder
        long x2 = 0, x1 = 0, x0 = 0;
        long y2 = 0, y1 = 1, y0 = 0;
        long lastRemainder = 0;
        int step = 1;

        while(true){
            long remainder = a % b;
            long quotient = (a - remainder) / b;

            //"Shift" the two numbers
            a = b;
            b = remainder;

            if(step == 1){
                //On the first step, store -q1 into y0
                x0 = 1;
                y0 = -quotient;
            } else {
                x2 = x1;
                x1 = x0;
                x0 = x2 - (quotient * x1); //x[n] = x[n-2] - Q[n]*x[n-1]

                y2 = y1;
                y1 = y0;
                y0 = y2 - (quotient * y1); //y[n] = y[n-2] - Q[n]*y[n-1]
            }

            step++;

            if(remainder == 0){
                break;
            }

            //Set the last remainder as long as it's not 0
            lastRemainder = remainder;
        }

        return new long[]{ lastRemainder, x1, y1 };
    }

    public static int[] euclid(int a, int b){
        //Ensure that a ≥ b
        if(a < b){
            int temp = a;
            a = b;
            b = temp;
        }

        List<Integer> q = new ArrayList<>();
        List<Integer> r = new ArrayList<>();

        q.add(0); r.add(a);
        q.add(0); r.add(b);

        while(true){
            int remainder = a % b;
            int coefficient = (a - remainder) / b;

            q.add(coefficient);
            r.add(remainder);

            System.out.println(a + " = " + b + "*" + coefficient + " + " + remainder);
            System.out.println("R" + r.lastIndexOf(a) + " = " + "R" + r.lastIndexOf(b) + "*Q" + q.lastIndexOf(coefficient) + " + " + "R" + (r.lastIndexOf(a) + 2));
            System.out.println();

            a = b;
            b = remainder;

            if(remainder == 0){
                break;
            }
        }

        System.out.println();
        System.out.println("Backwards:");

        //Print out the debug data
        System.out.println();
        System.out.println(String.format("%4s | %4s | %4s", "i", "Q", "R"));
        for(int i = 0; i < q.size(); i++){
            System.out.println(String.format("%4s | %4s | %4s", i, q.get(i), r.get(i)));
        }

        int x = 1;
        int y = q.get(q.size() - 2);
        int gcd = r.get(r.size() - 2);

        System.out.println();
        System.out.println(gcd);

        int length = r.size();
        int tempA = r.get(Math.max(0, length - 1 - 3));
        int tempB = r.get(length - 1 - 2);

        System.out.println(" = " + tempA + "*" + x + " - " + tempB + "*" + y);

        for(int step = 1; step < length - 3; step++){
            boolean modifyRight = step % 2 == 1; //On the first step, modify the right side

            if(modifyRight){
                tempB = r.get(length - 1 - step - 3);
                x += q.get(length - 1 - step - 1) * y;
            } else {
                tempA = r.get(length - 1 - step - 3);
                y += q.get(length - 1 - step - 1) * x;
            }

            System.out.println(" = " + tempA + "*" + x + " - " + tempB + "*" + y);
        }

        return new int[]{ r.get(r.size() - 2), x, y };
    }
}