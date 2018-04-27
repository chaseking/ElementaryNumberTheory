package me.chaseking.numbertheory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * @author Chase King
 */
public class RSA {
    private static long PRIME_P = 73;
    private static long PRIME_Q = 113;
    private static long N = PRIME_P * PRIME_Q;
    private static long E = 65;
    private static long PRIVATE_KEY;
    private static boolean DEBUG = false;

    private static final String FILE_NAME = "rsa.txt";

    public static void main(String[] args){
        try(Scanner scanner = new Scanner(System.in)){
            System.out.println("Welcome to RSA!");

            File file = new File(FILE_NAME);
            boolean recalculatePrivateKey;

            if(!file.exists()){
                System.out.println("Since this is your first time running the program, we need to first setup your private key.");
                file.createNewFile();
                recalculatePrivateKey = true;
            } else {
                //Validate the private key
                List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));

                if(lines.size() == 4){
                    PRIME_P = Long.parseLong(lines.get(0));
                    PRIME_Q = Long.parseLong(lines.get(1));
                    E = Long.parseLong(lines.get(2));
                    PRIVATE_KEY = Long.parseLong(lines.get(3));

                    //Likely a valid file
                    System.out.println("We found a private key on record. Would you like to change it? (y/n)");

                    switch(scanner.nextLine().toLowerCase()){
                        case "y":
                            recalculatePrivateKey = true;
                            break;

                        case "n":
                        default:
                            recalculatePrivateKey = false;
                            break;
                    }
                } else {
                    recalculatePrivateKey = true;
                }
            }

            if(recalculatePrivateKey){
                System.out.println();
                System.out.println("=== Changing your private key: ===");
                System.out.println("Please enter the first prime (P) (private):");
                PRIME_P = scanner.nextLong();

                System.out.println("Please enter the second prime (Q) (private):");
                PRIME_Q = scanner.nextLong();
                N = PRIME_P * PRIME_Q;
                System.out.println("Your N value (public) is: " + N);

                System.out.println("Please enter E (public):");
                E = scanner.nextLong();

                PRIVATE_KEY = getPrivateKey(PRIME_P, PRIME_Q, E);
                System.out.println("Your new private key (D) is: " + PRIVATE_KEY);
                System.out.println();

                //Save to file
                Files.write(Paths.get(FILE_NAME), (PRIME_P + "\n" + PRIME_Q + "\n" + E + "\n" + PRIVATE_KEY).getBytes());
            }

            while(true){
                System.out.println();
                System.out.println("Would you like to encrypt (e), decrypt (d), of quit (c)?");

                switch(scanner.nextLine().toLowerCase()){
                    case "e":
                    case "encrypt":
                        System.out.println();
                        System.out.println("=== Encrypting ===");

                        System.out.println("Enter plaintext message:");
                        String message = scanner.nextLine();

                        System.out.println("Enter your public key N value (blank for the one saved):");
                        long encryptN = getOrDefault(scanner, N);

                        System.out.println("Enter your public key E value (blank for the one saved):");
                        long encryptE = getOrDefault(scanner, E);

                        System.out.println("Encrypted cipher text: " + encrypt(message, encryptN, encryptE));
                        break;

                    case "d":
                    case "decrypt":
                        System.out.println();
                        System.out.println("=== Decrypting ===");
                        System.out.println("Please enter the cipher text:");
                        String ciphertext = scanner.nextLine();

                        System.out.println("Enter your public key N value (blank for the one saved):");
                        long decryptN = getOrDefault(scanner, N);

                        System.out.println("Please enter your private key (blank for the one saved):");
                        long decryptPrivateKey = getOrDefault(scanner, PRIVATE_KEY);

                        System.out.println("Decrypted plaintext: " + decrypt(ciphertext, decryptN, decryptPrivateKey));
                        break;

                    case "c":
                    case "quit":
                    default:
                        System.out.println();
                        System.out.println("Goodbye!");
                        System.exit(0);
                        return;

                }
            }
        } catch(IOException e1){
            e1.printStackTrace();
        }
    }

    public static String encrypt(String message, long n, long e){
        message = message.toUpperCase();

        // Convert to ASCII array
        int[] ascii = new int[message.length()];
        for(int i = 0; i < message.length(); i++) ascii[i] = (int) message.charAt(i);
        if(DEBUG) System.out.println("ASCII array: " + Arrays.toString(ascii));

        int[] chunked = blockArray(ascii, 3, 0/*(int) Math.ceil(Math.log10(maxVal))*/);
        if(DEBUG) System.out.println("Chunked ASCII array: " + Arrays.toString(chunked));
        int nDigits = (int) Math.ceil(Math.log10(n)); //Number of digits in n

        // Encrypt the chunked ASCII array
        int[] encrypted = Arrays.stream(chunked).map(x -> (int) ModularExponentiation.solve(x, e, n)).toArray();
        if(DEBUG) System.out.println("encrypted = " + Arrays.toString(encrypted));

        StringBuilder ciphertext = new StringBuilder();

        for(int num : encrypted){
            if(ciphertext.length() > 0) ciphertext.append(" ");
            // Append zeros so it is the correct length
            for(int i = (int) Math.ceil(Math.log10(num)); i < nDigits; i++) ciphertext.append("0");
            ciphertext.append(num);
        }

        if(DEBUG) System.out.println(ciphertext.toString());

        return ciphertext.toString();
    }

    public static String decrypt(String cipherText, long n, long privateKey){
        // Convert cipherText to blocked int array (n digits long)
        int nDigits = (int) Math.ceil(Math.log10(n));
        int[] encryptedArray = blockArray(cipherText.replaceAll(" ", ""), nDigits);

        if(DEBUG){
            System.out.println("encryptedArray = " + Arrays.toString(encryptedArray));
            System.out.println("n = " + n);
            System.out.println("privateKey = " + privateKey);
        }

        // Find all the least residues of the encrypted digits
        int[] decrypted = Arrays.stream(encryptedArray).map(x -> (int) ModularExponentiation.solve(x, privateKey, n)).toArray();

        if(DEBUG) System.out.println("decrypted = " + Arrays.toString(decrypted));

        // Convert to ASCII
        int[] decryptedAscii = blockArray(decrypted, 2, (int) Math.ceil(Math.log10(n)) - 1); //Convert it back into blocks of two-digit numbers that represent ASCII chars

        if(DEBUG){
            System.out.println("chunk length = " + (int) Math.ceil(Math.log10(n) - 1));
            System.out.println("Decrypted ASCII array: " + Arrays.toString(decryptedAscii));
        }

        StringBuilder plaintext = new StringBuilder();
        for(int i = 0; i < decryptedAscii.length; i++){
            if(decryptedAscii[i] != 0){
                plaintext.append((char) decryptedAscii[i]);
            }
        }
        return plaintext.toString();
    }

    private static long getOrDefault(Scanner scanner, long def){
        String input = scanner.nextLine();

        try{
            return Long.parseLong(input);
        } catch(Exception e){
            return def;
        }
    }

    private static int[] blockArray(int[] array, int chunkLength, int expectedDigits){
        //Convert the number array to a string so it's easier to chunk up
        StringBuilder builder = new StringBuilder();

        for(int num : array){
            if(expectedDigits > 0){
                for(int i = (int) Math.ceil(Math.log10(num)); i < expectedDigits; i++){
                    builder.append("0");
                }
            }

            builder.append(num);
        }

        String arrayStr = builder.toString();

        System.out.println("arrayStr = " + arrayStr);

        int[] chunkedArray = new int[(int) Math.ceil((double) arrayStr.length() / chunkLength)];

        for(int i = 0; i < chunkedArray.length; i++){
            String str = arrayStr.substring(i * chunkLength, Math.min((i + 1) * chunkLength, arrayStr.length())); //Use min() to prevent OOB
            int num = Integer.parseInt(str);

            //Add zeros to the end of array elements that are less than the chunk length
            for(int x = str.length(); x < chunkLength; x++) num *= 10;

            chunkedArray[i] = num;
        }

        return chunkedArray;
    }

    private static int[] blockArray(String str, int chunkLength){
        int[] array = new int[str.length()];
        for(int i = 0; i < str.length(); i++) array[i] = Integer.parseInt(str.substring(i, i + 1));
        return blockArray(array, chunkLength, 0);
    }

    public static long getPrivateKey(long p, long q, long e){
        long lcm = (p - 1) * (q - 1) / GCD.gcd(p - 1, q - 1)[0]; //The LCM of p-1 and q-1

        //ed = 1 (mod b)
        //ed - by = 1

        long[] gcd = GCD.gcd(e, -lcm);
        long solution = gcd[1];

        while(solution < 0) solution += lcm; //Ensure that it is positive

        return solution;
    }
}