package org.project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;

import java.util.*;

public class Search {

    static Logger logger = LogManager.getLogger(Search.class);
    public static List<descriptionObject> search(Input in, String input, OrderEnum order, SearchType type) {
        //get words in input string
        String[] words = input.split("(?!['-])\\W+");
        //what do we need to do?
        // - search, duh
        //   - for each entry in [alpha index] OR descriptor list (i think this is better, idk why we need kwic at this point)
        //     see if it contains any of the words in the search
        Set<descriptionObject> candidates = new HashSet<>();
        for (descriptionObject description: in.getInputItems().values()) {
            String name = description.getOriginalDescriptor();
            boolean nameContainsAnyInput = Arrays.stream(words).anyMatch(name::contains);
            if (nameContainsAnyInput) {
                //   - switch on SearchType
                //      -- if OR, then add for any match
                //      -- if AND, search for next words in the match. we care about exactness here.
                //      -- if NOT, then continue. if it doesn't contain, then we do the AND logic within it.
                switch(type) {
                    case OR:
                        addAndIncrement(candidates, description);
                        break;
                    case AND:
                        if(checkIfDescContainsAllInput(name, words))
                            addAndIncrement(candidates, description);
                        break;
                    case NOT:
                        break;

                }
            }
            else if (type.equals(SearchType.NOT)) { // if we have a NOT search, and nothing matching, we add the entry in
                addAndIncrement(candidates, description);
            }
        }

        if (candidates.isEmpty())
        {
            logger.info("No results for query. Using levenshtein distance candidates");
        }

        // - levenshtein distance candidates in separate list -- same autofill result threshold.
        // - if exact results = 0, return levenshtein results -- do we want to do this?

        //sort based on input
        return sortBasedOnOrder(new ArrayList<>(candidates), order);
    }

    private static boolean checkIfDescContainsAllInput(String name, String[] words) {
        for (String word : words) {
            if (!name.contains(word)){
                return false;
            }
        }
        return true;
    }

    private static void addAndIncrement(Set<descriptionObject> candidates, descriptionObject d) {
        d.incrementFrequency();
        candidates.add(d);
    }

    private static List<descriptionObject> sortBasedOnOrder(List<descriptionObject> candidates, OrderEnum order) {
        switch (order) {
            case PAY:
                Collections.sort(candidates, Comparator.comparingInt(descriptionObject::getAmtPaid));
                break;
            case FREQ:
                Collections.sort(candidates, Comparator.comparingInt(descriptionObject::getFrequency).reversed());
                break;
            case ALPHA:
                Collections.sort(candidates, Comparator.comparing(descriptionObject::getOriginalDescriptor));
                break;
        }
        return candidates;
        //return new ArrayList<>();
    }
}
