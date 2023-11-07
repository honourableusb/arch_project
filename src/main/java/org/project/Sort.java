package org.project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Sort {
    public ArrayList<String> sortURLMatches(HashMap<String, descriptionObject> inputItems, ArrayList<String> urlMatches, String sortMethod) {
        if ("alphabetically".equals(sortMethod)) {
            return Alphabetical(inputItems, urlMatches);
        } else if ("frequency".equals(sortMethod)) {
            return Frequency(inputItems, urlMatches);
        } else {
            throw new IllegalArgumentException("Invalid sortMethod");
        }
    }

    public ArrayList<String> Alphabetical(HashMap<String, descriptionObject> inputItems, ArrayList<String> urlMatches) {
        Collections.sort(urlMatches, new Comparator<String>() {
            @Override
            public int compare(String url1, String url2) {
                descriptionObject obj1 = inputItems.get(url1);
                descriptionObject obj2 = inputItems.get(url2);
                if (obj1 != null && obj2 != null) {
                    return obj1.getOriginalDescriptor().compareTo(obj2.getOriginalDescriptor());
                }
                return 0;
            }
        });

        return urlMatches;
    }

    public ArrayList<String> Frequency(HashMap<String, descriptionObject> inputItems, ArrayList<String> urlMatches) {
        Collections.sort(urlMatches, new Comparator<String>() {
            @Override
            public int compare(String url1, String url2) {
                descriptionObject obj1 = inputItems.get(url1);
                descriptionObject obj2 = inputItems.get(url2);
                if (obj1 != null && obj2 != null) {
                    // Sort in descending order of frequency
                    return Integer.compare(obj2.getFrequency(), obj1.getFrequency());
                }
                return 0;
            }
        });

        return urlMatches;
    }
}

