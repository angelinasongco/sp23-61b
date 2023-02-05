package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PAGALetterFreqGuesser implements Guesser  {
    private final List<String> words;
    private static final int ASCII_a = 97;
    private static final int ASCII_z = 122;

    public PAGALetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }


    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN and the GUESSES that have been made. */
    public char getGuess(String pattern, List<Character> guesses) {
        List<String> matchedWords = keepOnlyWordsThatMatchPattern(pattern);
        char[] pat = pattern.toCharArray();
        List<Character> listC = new ArrayList<Character>();
        for (char c : pat) {
            listC.add(c);
        }
//        Map<Character, Integer> freqMapPattern = getFreqMapThatMatchesPattern(listC);
        Map<Character, Integer> freqMap = getFreqMapThatMatchesPattern(matchedWords);
        Map<Character, Integer> filteredMap = new TreeMap<>();
        for (Character c : freqMap.keySet()) {
            if(c == '-'){
                continue;
            }
        }
        for (Character guess : guesses) {
            freqMap.remove(guess);
        }

        return getMostCommonLetter(freqMap);
    }

    private char getMostCommonLetter(Map<Character, Integer> freqMap){
        char guess = 'a';
        int maxCount = 0;
        for (int i = ASCII_a; i <= ASCII_z; i++) {
            char c = (char) i;
            if (freqMap.getOrDefault(c, 0) > maxCount) {
                guess = c;
                maxCount = freqMap.get(c);
            }
        }

        return guess;
    }

    private List<String> keepOnlyWordsThatMatchPattern(String pattern) {
        List<String> matchedWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() != pattern.length()) {
                continue;
            }
            boolean matches = true;
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != '-' && pattern.charAt(i) != word.charAt(i)) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                matchedWords.add(word);
            }
        }
        return matchedWords;
    }

    private Map<Character, Integer> getFreqMapThatMatchesPattern(List<String> matchedWords ) {
        Map<Character, Integer> freqMap = new TreeMap<>();
        for (String word : matchedWords) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
            }
        }
        return freqMap;
    }



    public static void main(String[] args) {
        PAGALetterFreqGuesser pagalfg = new PAGALetterFreqGuesser("data/example.txt");
        System.out.println(pagalfg.getGuess("----", List.of('e')));
    }
}
