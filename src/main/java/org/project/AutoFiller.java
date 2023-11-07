package org.project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//This component holds all the unique words from the titles to use for autofill
public class AutoFiller {
    private ArrayList<String> uniqueWords;

    public AutoFiller() {
        uniqueWords = new ArrayList<>();
    }

    public void addUniqueWordsFromString(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return;
        }

        // Split the input string into words using whitespace as the delimiter
        String[] words = inputString.split("\\s+");

        // Create a set to store unique words
        Set<String> uniqueWordSet = new HashSet<>(uniqueWords);

        // Add unique words from the input to the set
        for (String word : words) {
            uniqueWordSet.add(word);
        }

        // Update the ArrayList with unique words
        uniqueWords.clear();
        uniqueWords.addAll(uniqueWordSet);
    }

    public List<String> getUniqueWords() {
        return uniqueWords;
    }
}
