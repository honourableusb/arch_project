package org.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Output {
    public ArrayList<String> Search(String userInput, String searchMode, HashMap<String, descriptionObject> inputItems, ArrayList<String> totalList) {
        ArrayList<String> matches;
        
        // Analyze userInput for quotations and split it into parts
        String[] inputParts = analyzeUserInput(userInput);

        // Perform the search based on the searchMode
        if ("or".equals(searchMode)) {
            matches = OrSearch(inputParts, totalList);
        } else if ("and".equals(searchMode)) {
            matches = AndSearch(inputParts, totalList);
        } else if ("not".equals(searchMode)) {
            matches = NotSearch(inputParts, totalList);
        } else {
            throw new IllegalArgumentException("Invalid searchMode");
        }
        
        // Find matching URLs in inputItems
        ArrayList<String> matchingURLs = findMatchingURLs(matches, inputItems);
        
        return matchingURLs;
    }
    
    private String[] analyzeUserInput(String userInput) {
        return userInput.split("\"");
    }

    private ArrayList<String> OrSearch(String[] inputParts, ArrayList<String> totalList) {
        ArrayList<String> matches = new ArrayList<>();
        
        for (String inputPart : inputParts) {
            String[] keywords = inputPart.trim().split("\\s+");
            for (String totalListEntry : totalList) {
                boolean isMatch = false;
                for (String keyword : keywords) {
                    if (totalListEntry.contains(keyword)) {
                        isMatch = true;
                        break;
                    }
                }
                if (isMatch) {
                    matches.add(totalListEntry);
                }
            }
        }
        
        return matches;
    }

    private ArrayList<String> AndSearch(String[] inputParts, ArrayList<String> totalList) {
        ArrayList<String> matches = new ArrayList<>();
        
        for (String inputPart : inputParts) {
            String[] keywords = inputPart.trim().split("\\s+");
            for (String totalListEntry : totalList) {
                boolean isMatch = true;
                for (String keyword : keywords) {
                    if (!totalListEntry.contains(keyword)) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    matches.add(totalListEntry);
                }
            }
        }
        
        return matches;
    }

    private ArrayList<String> NotSearch(String[] inputParts, ArrayList<String> totalList) {
        ArrayList<String> matches = new ArrayList<>();
        
        for (String totalListEntry : totalList) {
            boolean isMatch = true;
            for (String inputPart : inputParts) {
                String[] keywords = inputPart.trim().split("\\s+");
                for (String keyword : keywords) {
                    if (totalListEntry.contains(keyword)) {
                        isMatch = false;
                        break;
                    }
                }
                if (!isMatch) {
                    break;
                }
            }
            if (isMatch) {
                matches.add(totalListEntry);
            }
        }
        
        return matches;
    }

    public ArrayList<String> findMatchingURLs(ArrayList<String> matches, HashMap<String, descriptionObject> inputItems) {
        ArrayList<String> matchingURLs = new ArrayList<>();

        for (descriptionObject descriptionObject : inputItems.values()) {
            List<Map.Entry<String, String>> alphabetized = descriptionObject.getAlphabetized();

            if (alphabetized != null) {
                for (Map.Entry<String, String> attributeValue : alphabetized) {
                    if (matches.contains(attributeValue.getKey())) {
                        // If the attribute value is found in 'matches', add the URL to the result
                        matchingURLs.add(attributeValue.getValue());
                        break; // No need to check other attributes of the same DescriptionObject
                    }
                }
            }
        }

        return matchingURLs;
    }
}

