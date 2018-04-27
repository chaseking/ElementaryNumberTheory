package me.chaseking.numbertheory;

/**
 * @author Chase King
 */
public class FrequencyAnalysisChallenge {
    public static final double[] ENGLISH_LETTER_FREQUENCIES = {8.12, 1.49, 2.71, 4.32, 12.02, 2.3, 2.03, 5.92, 7.31, 0.10, 0.69, 3.98, 2.61, 6.95, 7.68, 1.82, 0.11, 6.02, 6.28, 9.1, 2.88, 1.11, 2.09, 0.17, 2.11, 0.07};

    public static void main(String[] args){
        //TODO - Get user input...

        String ciphertext = "fwzdvfwevtwegbosndtfxbwnstwawkybrvwkybubwbawstvrsezorlzrrlboubwbabwnbcrgodswfzgrlzdhostybwoftclrlboubwbrlbgzerabsagbostvbiabcrrsxbkdysgybvkdzdorlkdmerwzdmbswfoerbwkstexbcztebrlbojtervkvdrlsgvukrletcldsdebdebfwvtwegbouzerlbvkwbcrswsnznkwfczggbvmwtddkdmeulkclfzvbvwkggelbuzezxkmxbbnofzdukrllzwvgozdodbchzgrlstmllbvkvlzybzybwogzwmbfterzclbfwevtwegbouzerlkdzdvxgsdvbzdvlzvdbzwgorukcbrlbtetzgzfstdrsndbchulkclczfbkdybwotebntgzeelbeabdresftclsnlbwrkfbcwzdkdmsybwmzwvbdnbdcbeeaokdmsdrlbdbkmlxswerlbvtwegboelzvzefzggesdczggbvvtvgbozdvkdrlbkwsakdksdrlbwbuzedsnkdbwxsozdoulbwbrlbvtwegboelzvbybworlkdmrlbouzdrbvxtrrlbozgeslzvzebcwbrzdvrlbkwmwbzrbernbzwuzerlzresfbxsvoustgvvkecsybwkrrlbovkvdrrlkdhrlbocstgvxbzwkrknzdosdbnstdvstrzxstrrlbasrrbwefweasrrbwuzefwevtwegboeekerbwxtrrlbolzvdrfbrnswebybwzgobzwekdnzcrfwevtwegboawbrbdvbvelbvkvdrlzybzekerbwxbczteblbwekerbwzdvlbwmssvnswdsrlkdmltexzdvubwbzetdvtwegbokelzekruzeaseekxgbrsxbrlbvtwegboeeltvvbwbvrsrlkdhulzrrlbdbkmlxswe".toUpperCase();

        //Get the occurrences of each letter
        int[] letterCounters = new int[26];

        for(int i = 0; i < ciphertext.length(); i++){
            char c = ciphertext.charAt(i);

            if(Character.isAlphabetic(c)){
                int alphabetIndex = (int) c - 65; //65 = A

                letterCounters[alphabetIndex]++;
            }
        }

        //Get the frequencies of each letter
        double[] cipherFrequencies = new double[26];

        for(int i = 0; i < 26; i++){
            cipherFrequencies[i] = letterCounters[i] / (double) ciphertext.length() * 100.0; //Convert to %
        }

        //Find the actual letter based on comparing frequency to actual frequency
        int[] plaintextAlphabet = new int[26];

        for(int i = 0; i < 26; i++){ //Loop over all cipher frequencies
            System.out.println("Frequency of " + (char) (i + 65) + ": " + cipherFrequencies[i]);

            double min = 0;

            for(int realAlphabetIndex = 0; realAlphabetIndex < 26; realAlphabetIndex++){ //Loop over all English frequencies
                double rms = rootMeanSquare(cipherFrequencies[i], ENGLISH_LETTER_FREQUENCIES[realAlphabetIndex]);

                if(min == 0 || rms < min){
                    min = rms;
                    plaintextAlphabet[i] = realAlphabetIndex;
                }
            }

            System.out.println("   Likely " + (char) (plaintextAlphabet[i] + 65));
        }

        //Print out the decrypted string
        StringBuilder decryptedString = new StringBuilder();

        for(int i = 0; i < ciphertext.length(); i++){
            int encryptedIndex = (int) ciphertext.charAt(i) - 65;

            decryptedString.append((char) (plaintextAlphabet[encryptedIndex] + 65));

            System.out.println();
        }

        System.out.println(decryptedString.toString());
    }

    public static double rootMeanSquare(double observed, double expected){
        return Math.pow(observed - expected, 2) / expected;
    }


}