package me.chaseking.numbertheory;

import java.util.Scanner;

/**
 * @author Chase King
 */
public class EasterProgram {
    public static void main(String[] args){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Enter the year: ");
            int year = scanner.nextInt();

            int a = year % 19;
            int b = year / 100;
            int c = year % 100;
            int d = b / 4;
            int e = b % 4;
            int f = (b + 8) / 25;
            int g = (b - f + 1) / 3;
            int h = (19*a + b - d - g + 15) % 30;
            int i = c / 4;
            int k = c % 4;
            int r = (32 + 2*e + 2*i - h - k);
            int m = (a + 11*h + 22*r) / 451;
            int n = (h + r - 7*m + 114) / 31;
            int p = ((h + r - 7*m + 114) % 31) + 1;
            StringBuilder date = new StringBuilder();

            if(n == 3){
                date.append("March");
            } else if(n == 4){
                date.append("April");
            }

            date.append(" ");
            date.append(p);
            date.append(", ");
            date.append(year);

            System.out.println("a = " + a);
            System.out.println("b = " + b);
            System.out.println("c = " + c);
            System.out.println("d = " + d);
            System.out.println("e = " + e);
            System.out.println("f = " + f);
            System.out.println("g = " + g);
            System.out.println("h = " + h);
            System.out.println("i = " + i);
            System.out.println("k = " + k);
            System.out.println("r = " + r);
            System.out.println("m = " + m);
            System.out.println("n = " + n);
            System.out.println("p = " + p);
            System.out.println();
            System.out.println("Date for Easter in " + year + ": " + date.toString());
        }
    }
}