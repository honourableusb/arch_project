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

public class Alphabetizer {
    public Alphabetizer(){}
    public ArrayList<String> Alphabetize(ArrayList<String> inputList, ArrayList<String> totalList) {
        // If you are alphabetizing a list
        if(totalList == null){
            return mergeSort(inputList);
        }

        //If you are merging two list that are already alphabetized
        return merge(inputList, totalList);
    }

    //If you are alphabetizing one list then call this function so that it may be split in two
    public static ArrayList<String> mergeSort(ArrayList<String> inputList) {
        if (inputList.size() <= 1) {
            return inputList;
        }

        int middle = inputList.size() / 2;
        ArrayList<String> left = new ArrayList<>(inputList.subList(0, middle));
        ArrayList<String> right = new ArrayList<>(inputList.subList(middle, inputList.size()));

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    //Merge two arraylist and alphabetize them
    private static ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right) {
        ArrayList<String> result = new ArrayList<>();
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