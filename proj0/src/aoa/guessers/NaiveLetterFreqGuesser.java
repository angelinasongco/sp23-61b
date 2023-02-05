package aoa.guessers;

import aoa.utils.FileUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class NaiveLetterFreqGuesser implements Guesser {
    private final List<String> words;

    public NaiveLetterFreqGuesser(String dictionaryFile) {
        words = FileUtils.readWords(dictionaryFile);
    }

    @Override
    /** Makes a guess which ignores the given pattern. */
    public char getGuess(String pattern, List<Character> guesses) {
        return getGuess(guesses);
    }

    /** Returns a map from a given letter to its frequency across all words.
     *  This task is similar to something you did in hw0b! */
    public Map<Character, Integer> getFrequencyMap() {
        Map<Character, Integer> frequencyMap = new TreeMap<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }
        return frequencyMap;
    }

    /** Returns the most common letter in WORDS that has not yet been guessed
     *  (and therefore isn't present in GUESSES). */
    public char getGuess(List<Character> guesses) {
        Map<Character, Integer> sortedFrequencyMap = getFrequencyMap();
        char nextGuess = ' ';
        int maxFrequency = -1;
        for (Map.Entry<Character, Integer> entry : sortedFrequencyMap.entrySet()) {
            char letter = entry.getKey();
            int frequency = entry.getValue();
            if (!guesses.contains(letter) && frequency > maxFrequency) {
                nextGuess = letter;
                maxFrequency = frequency;
            } else if (!guesses.contains(letter) && frequency == maxFrequency) {
                if (letter < nextGuess) {
                    nextGuess = letter;
                }
            }
        }
        return nextGuess;

    }

    public static void main(String[] args) {
        NaiveLetterFreqGuesser nlfg = new NaiveLetterFreqGuesser("data/example.txt");
        System.out.println("list of words: " + nlfg.words);
        System.out.println("frequency map: " + nlfg.getFrequencyMap());

        List<Character> guesses = List.of('e', 'l');
        System.out.println("guess: " + nlfg.getGuess(guesses));
    }
}
