package me.chaseking.numbertheory;

import java.util.Arrays;

/**
 * @author Chase King
 */
public class Sorting {
    public static void main(String[] args){
//        try(Scanner scanner = new Scanner(System.in)){
//            System.out.println("Enter a length for the random list:");
//            int[] numbers = generateList(scanner.nextInt(), 10000);
//            System.out.println("Unsorted: " + Arrays.toString(numbers));
//            bubbleSort(numbers);
//            System.out.println("Sorted: " + Arrays.toString(numbers));
//        }

        int[] sequence = new int[5];

        for(int i = 1; i < 10; i++){
            sequence[0] = i;

            for(int j = 1; j < sequence.length; j++){
                sequence[j] = 1;

                for(int k = 0; k < j; k++){
                    sequence[j] += sequence[k];
                }
            }

            System.out.println(Arrays.toString(sequence));
        }

        int[] numbers = generateList(10, 100);
        System.out.println("Unsorted: " + Arrays.toString(numbers));
//        unknownNameSort(numbers);
        insertionSort(numbers);
        System.out.println("Sorted: " + Arrays.toString(numbers));
    }

    /**
     * Generate a random integer list of the given length.
     * @param length length of the list
     * @param maxValue the maximum value of elements in the list [1, maxValue]
     * @return a list of random integers
     */
    public static int[] generateList(int length, int maxValue){
        int[] list = new int[length];

        for(int i = 0; i < length; i++){
            list[i] = (int) (Math.random() * maxValue) + 1;
        }

        return list;
    }

    /**
     * Swaps two elements in a list.
     *
     * @param list
     * @param a first index
     * @param b second index
     */
    public static void swap(int[] list, int a, int b){
        int temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }

    /**
     * Performs the bubble sort algorithm
     *  - Start for loop running over all elements in the array
     *  - Compare the 0th pair (i.e. the first two numbers)
     *    - If second one smaller than first: swap numbers
     *    - Otherwise: move on
     *  - Move on to the next pair... all the way up to the n-1 pair
     *  - Once the first iteration has been run, the last number in the array must be the largest
     *  - Compare all pairs up to n-2
     *  - Keep going until done
     *
     * @param list the list of numbers to sort
     */
    public static void bubbleSort(int[] list){
        for(int i = list.length - 1; i >= 0; i--){ //Every time this inner loop finishes, the i-th element will be sorted
            for(int x = 0; x < i; x++){
                if(list[x] > list[x + 1]){
                    //Swap the two numbers
                    swap(list, x, x + 1);
                }
            }
        }
    }

    /**
     *  - Start at first index
     *  - Loop over all numbers
     *    - Find min and max
     *    - Swap the min with the first
     *    - Swap the max with the last
     *  - Continue until middle of list
     *
     * @param list the list of numbers to sort
     */
    public static void unknownNameSort(int[] list){
        for(int i = 0; i < list.length / 2; i++){
            int minIndex = i, maxIndex = i;

            for(int x = i + 1; x < list.length - 1 - i; x++){
                int value = list[x];

                if(value < list[minIndex]){
                    minIndex = x;
                } else if(value > list[maxIndex]){
                    maxIndex = x;
                }
            }

            swap(list, minIndex, i); //Put min element in current position
            System.out.println(Arrays.toString(list));
            System.out.println("Putting " + list[maxIndex] + " at the end " + (list.length - 1 - i));
            swap(list, maxIndex, list.length - 1 - i); //Put max element in last position
            System.out.println(Arrays.toString(list));
            System.out.println();
        }
    }

    public static void insertionSort(int[] list){
        for(int i = 1; i < list.length; i++){ //First element is automatically "sorted"
            int current = list[i];
            int pos;

            //Move all elements that are greater than the current element up one space
            for(pos = i - 1; pos >= 0 && list[pos] > current; pos--){
                list[pos + 1] = list[pos];
            }

            //Insert the current element into the position where it belongs
            list[pos + 1] = current;

            System.out.println("Step " + i + ": " + Arrays.toString(list));
        }
    }
}