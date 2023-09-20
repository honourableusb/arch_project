//UTD SE 6362 F23 Deliverable -- KWIC Algorithm
//Team Members:
// - Shariq Azeem
// - Ayush Bhardwaj
// - Jeremiah De Luna
// - Matthew Haskell
// - Eddie Villareal

//Alphabetizer.java -- this sorts our circular shifts for output.
package org.project;

import java.util.ArrayList;
import java.util.List;

public class Alphabetizer {
    public static List<String> Alphabetizer(List<String> inputList) {
       return mergeSort(inputList);
    }

    public static List<String> mergeSort(List<String> inputList) {
        if (inputList.size() <= 1) {
            return inputList;
        }

        int middle = inputList.size() / 2;
        List<String> left = inputList.subList(0, middle);
        List<String> right = inputList.subList(middle, inputList.size());

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static List<String> merge(List<String> left, List<String> right) {
        List<String> result = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            String leftElement = left.get(leftIndex);
            String rightElement = right.get(rightIndex);

            if (leftElement.compareTo(rightElement) < 0) {
                result.add(leftElement);
                leftIndex++;
            } else {
                result.add(rightElement);
                rightIndex++;
            }
        }

        result.addAll(left.subList(leftIndex, left.size()));
        result.addAll(right.subList(rightIndex, right.size()));

        return result;
    }
}