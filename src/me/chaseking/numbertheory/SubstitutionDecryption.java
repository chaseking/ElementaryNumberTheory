package me.chaseking.numbertheory;

import java.util.*;

/**
 * @author Chase King
 */
public class SubstitutionDecryption {
    public static void main(String[] args){
        Set<String> words = Main.getEnglishWords();
        char[] englishFrequencies = "e t a o i n s r h l d c u m f p g w y b v k x j q z".replaceAll(" ", "").toCharArray();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input the encrypted message:");
        String encryptedMessage = scanner.nextLine().toLowerCase(); //Dealing with everything in lowercase

        System.out.println();
        System.out.println("Enter any known letters (e.g. \"A=B\"). Leave blank for none.");

        Character[] realAlphabet = new Character[26];

        while(true){
            String input = scanner.nextLine();

            if(input.isEmpty()){
                break;
            }

            char encryptedLetter = Character.toLowerCase(input.charAt(0));
            char realLetter = Character.toLowerCase(input.charAt(2));

            realAlphabet[Main.getAlphabetIndex(encryptedLetter)] = realLetter;

            System.out.println(" - " + Character.toUpperCase(encryptedLetter) + " is " + realLetter);
        }

        System.out.println();
        System.out.println("Starting matching process...");

        //Compute word frequencies in encrypted message
        final Map<Character, Integer> occurrences = new HashMap<>();
        Character[] frequencies = new Character[26];

        for(int i = 0; i < Main.ALPHABET_LOWER.length; i++){
            occurrences.put(Main.ALPHABET_LOWER[i], 0);
            frequencies[i] = Main.ALPHABET_LOWER[i];
        }

        for(char c : encryptedMessage.toCharArray()){
            if(Character.isLetter(c)){
                if(occurrences.containsKey(c)){
                    occurrences.put(c, occurrences.get(c) + 1);
                } else {
                    occurrences.put(c, 1);
                }
            }
        }

        Arrays.sort(frequencies, new Comparator<Character>(){
            @Override
            public int compare(Character c1, Character c2){
                return Integer.compare(occurrences.get(c2), occurrences.get(c1));
            }
        });

        for(int i = 0; i < frequencies.length; i++){
            System.out.println((i + 1) + ": " + frequencies[i]);
        }

        //
        Character[] message = new Character[encryptedMessage.length()];

        for(int i = 0; i < encryptedMessage.length(); i++){
            char encryptedChar = encryptedMessage.charAt(i);
            int encryptedCharIndex = Main.getAlphabetIndex(encryptedChar);

            if(realAlphabet[encryptedCharIndex] != null){
                message[i] = realAlphabet[encryptedCharIndex];
            }
        }

        System.out.println();
        System.out.println("Initial message:");
        System.out.println(charToString(message));
        System.out.println();

        testLetter(encryptedMessage, message, 'e', 'n');
        testLetter(encryptedMessage, message, 'e', 'd');
        testLetter(encryptedMessage, message, 'e', 't');
        testLetter(encryptedMessage, message, 'e', 's');
        testLetter(encryptedMessage, message, 'e', 'g');

        System.out.println();

        for(int i = 0; i < 3; i++){
            testLetter(encryptedMessage, message, frequencies[i], englishFrequencies[i]);
        }
    }

    //NYHDJYDSTFMYTXMGYOTVVAVADMCZADGYONYHDVTOCAXEGUCYXVNJANGYPCGD

    public static void testLetter(String encryptedMessage, Character[] message, char encryptedLetter, char realLetter){
        message = message.clone();
        int encryptedLetterIndex = Main.getAlphabetIndex(encryptedLetter);
        int realLetterIndex = Main.getAlphabetIndex(realLetter);

        for(int i = 0; i < encryptedMessage.length(); i++){
            if(encryptedMessage.charAt(i) == encryptedLetter){
                message[i] = realLetter;
            }
        }

        System.out.println(charToString(message));
    }

    private static String charToString(Character[] array){
        StringBuilder builder = new StringBuilder();

        for(Character c : array){
            if(c == null){
                builder.append("_");
            } else {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    private static class LetterFrequencies {
        Map<Character, Integer> occurrences;
        Character[] frequencies;

        private LetterFrequencies(Collection<String> words){
            occurrences = new HashMap<>();
            frequencies = new Character[Main.ALPHABET_LOWER.length];

            for(int i = 0; i < Main.ALPHABET_LOWER.length; i++){
                occurrences.put(Main.ALPHABET_LOWER[i], 0);
                frequencies[i] = Main.ALPHABET_LOWER[i];
            }

            for(String word : words){
                for(char c : word.toCharArray()){
                    if(Character.isLetter(c)){
                        if(occurrences.containsKey(c)){
                            occurrences.put(c, occurrences.get(c) + 1);
                        } else {
                            occurrences.put(c, 1);
                        }
                    }
                }
            }
        }

        public Character[] getFrequencies(){
            return frequencies;
        }
    }
}