package org.project;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO this might be a good spot to keep a list of all non-noise words as we come across them to help with the autofill feature later
// TODO might be a good idea to update removeNoiseWordsAndSymbols or add new method to take in HashMap<String, descriptionObject> so that we can take out all noise words at once - 
// would make updated noiseword list act on all titles and not just the new ones
public class NoiseRemover {
    private ArrayList<String> noiseWords;
    private String symbolsToRemove;

    public NoiseRemover() {
        // Initialize noiseWords with the provided common stop words
        noiseWords = new ArrayList<>(Arrays.asList(
            "a", "an", "the", "in", "on", "at", "by", "with", "to", 
            "from", "of", "for", "and", "or", "but", "if", "as", "is", "are", "was", 
            "were", "have", "has", "be", "been", "am", "will", "do", "does", "did"
        ));

        // Initialize symbols to remove
        symbolsToRemove = "!@#$%^&*(),.?;:\\[\\]{}";
    }

    public ArrayList<String> getNoiseWords() { return noiseWords; }

    public void addWordToNoiseWords(String word) { noiseWords.add(word); }

    public void removeWordFromNoiseWords(String word) { noiseWords.remove(word); }

    public String getSymbolsToRemove() { return symbolsToRemove; }

    public void addSymbolToSymbolsToRemove(char symbol) { symbolsToRemove += symbol; }

    public void removeSymbolFromSymbolsToRemove(char symbol) {
        symbolsToRemove = symbolsToRemove.replace(String.valueOf(symbol), "");
    }

    public String removeNoiseWordsAndSymbols(String input) {
        String[] words = input.split("\\s+"); // Split the input string into words
        List<String> cleanWords = new ArrayList<>();

        for (String word : words) {
            // Clean the word by removing specified symbols and keeping its original case
            String cleanWord = word.replaceAll("[" + symbolsToRemove + "]", "");

            // Check if the cleaned word (ignoring case) is not in the noiseWords list
            if (!noiseWords.contains(cleanWord.toLowerCase())) {
                cleanWords.add(word);
            }
        }

        // Reconstruct the cleaned string without noise words and specified symbols
        String cleanedString = String.join(" ", cleanWords);

        return cleanedString;
    }
}
