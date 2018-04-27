package me.chaseking.numbertheory;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.BiFunction;

/**
 * @author Chase King
 */
public class TriangleArea {
    private static final BiFunction<Integer, Integer, Integer> MIN = new BiFunction<Integer, Integer, Integer>(){
        @Override
        public Integer apply(Integer a, Integer b){
            return (a < b ? a : b); //Or just Math.min(a, b);
        }
    };

    private static final BiFunction<Integer, Integer, Integer> MAX = new BiFunction<Integer, Integer, Integer>(){
        @Override
        public Integer apply(Integer a, Integer b){
            return (a > b ? a : b); //Or just Math.max(a, b);
        }
    };

    public static void main(String[] args){
        int[][] points = new int[3][2];
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < points.length; i++){
            System.out.println("Please enter point " + (i + 1) + " X-coordinate:");

            int x = scanner.nextInt();

            System.out.println("Please enter point " + (i + 1) + " Y-coordinate:");

            int y = scanner.nextInt();

            points[i] = new int[]{ x, y };
        }

        //Test points
//        points = new int[][]{
//                { 5, 1 },
//                { -2, 2 },
//                {  3, -1 }
//        };

        int minX = chain(MIN, points[0][0], points[1][0], points[2][0]);
        int maxX = chain(MAX, points[0][0], points[1][0], points[2][0]);
        int minY = chain(MIN, points[0][1], points[1][1], points[2][1]);
        int maxY = chain(MAX, points[0][1], points[1][1], points[2][1]);

        if(minX == maxX || minY == maxY){
            System.out.println("Error: please enter a valid triangle.");
            return;
        }

        //Loop over all points
        for(int i = 0; i < points.length; i++){
            int x = points[i][0];
            int y = points[i][1];

            //Check if the point is at any corner of the boxed rectangle
            if((x == minX && y == minY) //Lower left corner
                    || (x == minX && y == maxY) //Upper left corner
                    || (x == maxX && y == maxY) //Upper right corner
                    || (x == maxX && y == minY)){ //Lower right corner
                //If the point is on a corner of the cube, then it can be translated to the origin
                System.out.println("Point " + (i + 1) + " (" + x + ", " + y + ") can be translated to the origin."); //Could get the midpoint of the two other points to determine which quadrant the triangle would be in

                //Translate all points so that the current point moves to the origin (0, 0)
                for(int[] point : points){
                    point[0] -= x;
                    point[1] -= y;
                }

                //Get the coordinates of the other two points: (a, b) and (c, d)
                int a = 0, b = 0, c = 0, d = 0;

                for(int j = 0; j < points.length; j++){
                    if(i != j){ //Get the two other points
                        if(a == 0 && b == 0){ //This will be true for the first point
                            a = points[j][0];
                            b = points[j][1];
                        } else {
                            c = points[j][0];
                            d = points[j][1];
                        }
                    }
                }

                //Translate all points back
                for(int[] point : points){
                    point[0] += x;
                    point[1] += y;
                }

                System.out.println("Other points are (" + a + ", " + b + ") and (" + c + ", " + d + ").");
                System.out.println("The area is " + getArea(a, b, c, d));
                break; //Remove this to show other possibilities of points being moved to the origin (if there are any)
            }
        }
    }

    /**
     * @return Area of a triangle with one point at the origin and the other points (a, b) and (c, d).
     */
    public static double getArea(int a, int b, int c, int d){
        return Math.abs((a * d) - (b * c)) / 2.0;
    }

    /**
     * Recursively chain a 2-input function over a set of multiple inputs.
     * E.g.: Given [a, b, c] with function f, the result will be f(a, f(b, c)).
     */
    public static <T> T chain(BiFunction<T, T, T> function, T... inputs){
        if(inputs.length == 1){
            return inputs[0];
        } else {
            return function.apply(inputs[0], chain(function, Arrays.copyOfRange(inputs, 1, inputs.length)));
        }
    }
}
