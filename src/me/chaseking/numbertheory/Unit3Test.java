package me.chaseking.numbertheory;

/**
 * @author Chase King
 */
public class Unit3Test {
    public static void main(String[] args){
        System.out.println("Least residue of 1458^30 mod 17: " + leastResidue(1458, 30, 17));
        affine('e', 'M', 't', 'N');
    }

    private static int leastResidue(int a, int b, int m){
        int r = a%m;
        int n = leastPowerCongruent1(a, m);

        System.out.println(a + "^" + n + " = 1 (mod " + m + ")");

        System.out.println(a + "^" + b + " = (" + a + "^" + n + ")^" + ((b - b%n) / n) + " * " + a + "^" + (b%n));
        return (int) (Math.pow(a, b % n) % m);
    }

    /**
     * Returns the least value of N such that:
     * A^N = 1 (mod M)
     */
    private static int leastPowerCongruent1(int a, int m){
        int n = 1;

        while((Math.pow(a, n) - 1) % m != 0){
            n++;
        }

        return n;
    }

    private static int alphabetIndex(char c){
        return "abcdefghijklmnopqrstuvwxyz".indexOf(Character.toLowerCase(c)) + 1;
    }

    private static void affine(char p1, char c1, char p2, char c2){
        System.out.println("C = AP + B (mod 26)");
        int c1i = alphabetIndex(c1);
        int p1i = alphabetIndex(p1);
        int c2i = alphabetIndex(c2);
        int p2i = alphabetIndex(p2);
        System.out.println(c1i + " = A*" + p1i + " + B (mod 26)");
        System.out.println(c2i + " = A*" + p2i + " + B (mod 26)");
        System.out.println((c2i - c1i) + " = A*" + (p2i - p1i) + " (mod 26)");

        System.out.println((p2i - p1i) + "A - 26y = " + (c2i - c1i));

        int[] gcd = GCD.euclid((p2i - p1i), -26);

        System.out.println();
    }
}