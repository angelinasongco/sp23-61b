package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

import java.util.List;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;


    public RandomChooser(int wordLength, String dictionaryFile) {
        if (wordLength < 1) {
            throw new IllegalArgumentException("Word length must be at least 1");
        }
        if (wordLength == 0) {
            throw new IllegalStateException("No words found of length " + wordLength);
        }
        int numWords = FileUtils.readWordsOfLength(dictionaryFile, wordLength).size();
        int randomlyChosenWordNumber = StdRandom.uniform(numWords);
        chosenWord = FileUtils.readWordsOfLength(dictionaryFile, wordLength).get(randomlyChosenWordNumber);
        pattern = "";
        for (int i = 0; i < chosenWord.length(); i++) {
            pattern += "-";
        }

    }

    @Override
    public int makeGuess(char letter) {
        int count = 0;
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == letter) {
                count++;
                pattern = pattern.substring(0, i) + letter + pattern.substring(i + 1);
            }
        }
        return count;
    }

    @Override
    public String getPattern() {
        return pattern;
    }

    @Override
    public String getWord() {
        return chosenWord;
    }
}
