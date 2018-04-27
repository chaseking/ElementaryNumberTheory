package me.chaseking.numbertheory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chase King
 */
public class Fibonacci {

    public static void main(String[] args){
        int length = 61;
        List<Long> fibonacci = new ArrayList<>(length);

        fibonacci.add((long) 1);
        fibonacci.add((long) 1);

        for(int i = fibonacci.size(); i < length; i++){
            fibonacci.add(fibonacci.get(i - 2) + fibonacci.get(i - 1));
        }

        fibonacci.forEach(System.out::println);
    }
}