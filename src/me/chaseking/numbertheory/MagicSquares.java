package me.chaseking.numbertheory;

/**
 * @author Chase King
 */
public class MagicSquares {
    public static void main(String[] args){
        //Numbers taken from http://mathworld.wolfram.com/MagicSquare.html
        //This just prints "true" "true" "true" since all of the squares are valid

        //3x3
        System.out.println(isMagicSquare(new int[]{
                8, 1, 6,
                3, 5, 7,
                4, 9, 2
        }));

        //4x4
        System.out.println(isMagicSquare(new int[]{
                16, 3, 2, 13,
                5, 10, 11, 8,
                9, 6, 7, 12,
                4, 15, 14, 1
        }));

        //5x5
        System.out.println(isMagicSquare(new int[]{
                17, 24, 1, 8, 15,
                23, 5, 7, 14, 16,
                4, 6, 13, 20, 22,
                10, 12, 19, 21, 3,
                11, 18, 25, 2, 9
        }));
    }

    public static boolean isMagicSquare(int[] square){
        int size = (int) Math.sqrt(square.length);
        int[][] positions = getPositions(size); //Get the indices of the rows, columns, and diagonals
        int[] sums = getSums(positions, square); //Get the sums of positions

        //Check if all the sums are the same
        for(int i = 1; i < sums.length; i++){
            if(sums[i] != sums[i - 1]){
                return false;
            }
        }

        return true;
    }

    public static int[][] getPositions(int size){
        int[][] positions = new int[size + size + 2][];
        int index = 0;

        //Rows
        for(int row = 0; row < size; row++){
            int[] indices = new int[size];


            for(int i = 0; i < size; i++){
                indices[i] = (row * size) + i;
            }

            positions[index++] = indices;
        }

        //Columns
        for(int col = 0; col < size; col++){
            int[] indices = new int[size];

            for(int i = 0; i < size; i++){
                indices[i] = col + (i * size);
            }

            positions[index++] = indices;
        }

        //Diagonals
        //Top right to bottom left
        int[] indices = new int[size];
        for(int i = 0; i < size; i++){
            indices[i] = (size - 1) //Index of the top right square
                    + (i * (size - 1)); //Add (size - 1) to get to the lower left square
        }
        positions[index++] = indices;

        //Top left to bottom right
        indices = new int[size];
        for(int i = 0; i < size; i++){
            indices[i] = i * (size + 1); //Add (size + 1) to get to the lower right square
        }
        positions[index++] = indices;

        return positions;
    }

    public static int[] getSums(int[][] positions, int[] square){
        int[] sums = new int[positions.length];

        for(int i = 0; i < positions.length; i++){
            int sum = 0;

            for(int squareIndex : positions[i]){
                sum += square[squareIndex];
            }

            sums[i] = sum;
        }

        return sums;
    }
}