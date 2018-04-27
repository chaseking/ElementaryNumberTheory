package me.chaseking.numbertheory;

/**
 * @author Chase King
 */
public class VigenereSquare {
    public static final char[][] VIGENERE_SQUARE = new char[26][26];

    static {
        for(int offset = 0; offset < 26; offset++){
            VIGENERE_SQUARE[offset] = new char[26];

            for(int letter = 0; letter < 26; letter++){
                VIGENERE_SQUARE[offset][letter] = Main.ALPHABET_LOWER[(letter + offset) % 26];
            }
        }
    }

    public static void main(String[] args){
        printSquare();

        //Phrase taken from Wikipedia (https://en.wikipedia.org/wiki/Vigenère_cipher#Description)
        System.out.println(encrypt("ATTACK AT DAWN", "LEMON"));
    }

    public static String encrypt(String plaintext, String key){
//        plaintext = plaintext.toUpperCase();
//        key = key.toUpperCase();
        StringBuilder encrypted = new StringBuilder();
        int keyIndex = 0;

        for(char c : plaintext.toCharArray()){
            if(Character.isLetter(c)){
                //Only encrypt the character if it is a letter
                int alphabetIndex = getCharIndex(c);
                int keyAlphabetIndex = getCharIndex(key.charAt(keyIndex));

                encrypted.append(VIGENERE_SQUARE[keyAlphabetIndex][alphabetIndex]);

                keyIndex = (keyIndex + 1) % key.length();
            } else {
                encrypted.append(c);
            }
        }

        return encrypted.toString();
    }

    /**
     * Prints out the Vigenere square in a table view.
     */
    public static void printSquare(){
        for(int offset = 0; offset <= 26; offset++){
            if(offset == 0){
                //Print out the top numbers
                System.out.print("  ");

                for(int letter = 0; letter < 26; letter++){
                    System.out.print(letter <= 10 ? "  " : " "); //Space out the letters (two spaces for <= 10)
                    System.out.print(Character.toLowerCase(letter));
                }
            } else {
                System.out.print(offset);

                if(offset < 10) System.out.print(" ");

                for(int letter = 0; letter < 26; letter++){
                    System.out.print("  " + getChar((letter + offset) % 26));
                }
            }

            //Print a new line
            System.out.println();
        }
    }

    /**
     * Convert the number to a character (0 = A).
     * @param num the number to convert
     * @return the alphabet letter at the given index
     */
    public static char getChar(int num){
        return (char) (num + 65);
    }

    /**
     * Get the alphabet index of a given character (A/a = 0). Case insensitive.
     * @param c the character to convert.
     * @return the alphabet index of the given character
     */
    public static int getCharIndex(char c){
        return (int) Character.toUpperCase(c) - 65;
    }
}