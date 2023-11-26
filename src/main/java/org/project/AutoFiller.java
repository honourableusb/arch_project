package org.project;

//import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.*;
import org.apache.commons.text.*;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//This component holds all the unique words from the titles to use for autofill
public class AutoFiller {
    private Set<String> uniqueWords;
    private double autofillThreshold = .700;

    public AutoFiller() {
        uniqueWords = new HashSet<>();
    }

    public double getAutofillThreshold() {
        return autofillThreshold;
    }

    public String setAutofillThreshold(double autofillThreshold) {
        this.autofillThreshold = autofillThreshold;
        return "Threshold has been set to " + this.autofillThreshold;
    }

    public void addUniqueWordsFromString(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return;
        }

        // Split the input string into words using whitespace and commas as delimiter. this ignores intra-word punctuation,
        // such as dash (-) and apostrophes (')
        // if we need to add other ones, we can just like, put them in the brackets
        String[] words = inputString.split("(?!['-])\\W+");
        // Create a set to store unique words
        Set<String> uniqueWordSet = new HashSet<>(List.of(words));

        // Add unique words from the input to the set
        uniqueWordSet.addAll(uniqueWordSet);

        // Update the ArrayList with unique words
        uniqueWords.addAll(uniqueWordSet);
    }

    public Set<String> getUniqueWords() {
        return uniqueWords;
    }

    public Set<String> getPotentialMatches(String input) {
        String[] words = input.split(" ");
        //we only care about the last word, we assume all others are complete -- this may be a bad assumption
        String lastWord = words[words.length-1];
        Set<String> uniqueWords = getUniqueWords();
        Set<String> candidates = new HashSet<>();
        for (String uniqueWord : uniqueWords) {
//            if (uniqueWord.length() >= lastWord.length()) {
//                String sub = uniqueWord.substring(0, lastWord.length());
                LevenshteinDistance ld = new LevenshteinDistance();
                double percentSimilar = ((double) ld.apply(uniqueWord, lastWord) / uniqueWord.length());
                if (percentSimilar <= autofillThreshold) { // fun fact, we need this under the threshold because 100% means we have to change everything. the more ya know.
                    System.out.println(percentSimilar + " for substring " + uniqueWord);
                    System.out.println("This is the levenshtein distance for opposite: "+ ld.apply(lastWord, uniqueWord));
                    candidates.add(uniqueWord);
                }
//                StringUtils.compare(lastWord, sub);
//                if (sub.equals(lastWord)) {
//                    candidates.add(uniqueWord);
//                }
//            }

        }
        return candidates;
    }
}
