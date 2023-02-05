package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PatternAwareLetterFreqGuesser implements Guesser {
    public final List<String> words;

    public PatternAwareLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }
    private static final int ASCII_a = 97;
    private static final int ASCII_z = 122;
    @Override
    /** Returns the most common letter in the set of valid words based on the current
     *  PATTERN. */
    public char getGuess(String pattern, List<Character> guesses) {
        List<String> words = keepOnlyWordsThatMatchPattern(pattern);
        Map<Character, Integer> freqMap = getFreqMapThatMatchesPattern(words);

        char guess = 'a';
        int maxCount = 0;
        for (int i = ASCII_a; i <= ASCII_z; i++) {
            char c = (char) i;
            if (!guesses.contains(c) && freqMap.getOrDefault(c, 0) > maxCount) {
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

    private Map<Character, Integer> getFreqMapThatMatchesPattern(List<String> words) {
        Map<Character, Integer> freqMap = new TreeMap<>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
            }
        }
        return freqMap;
    }



    public static void main(String[] args) {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");
        System.out.println(palfg.getGuess("-e--", List.of('e')));
    }
}