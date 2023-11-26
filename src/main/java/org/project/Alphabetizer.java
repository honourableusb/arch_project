//UTD SE 6362 F23 Deliverable -- KWIC Algorithm
//Team Members:
// - Shariq Azeem
// - Ayush Bhardwaj
// - Jeremiah De Luna
// - Matthew Haskell
// - Eddie Villareal

//Alphabetizer.java -- this sorts our circular shifts for output.
package org.project;

import java.util.*;

public class Alphabetizer {
    public Alphabetizer(){}
    public List<Map.Entry<String, String>> Alphabetize(List<Map.Entry<String, String>> inputList, List<Map.Entry<String, String>> totalList) {
        // If you are alphabetizing a list
        if(totalList == null){
            return mergeSort(inputList);
        }

        //If you are merging two list that are already alphabetized
        return merge(inputList, totalList);
    }

    //If you are alphabetizing one list then call this function so that it may be split in two
    public static List<Map.Entry<String, String>> mergeSort(List<Map.Entry<String, String>> inputList) {
        if (inputList.size() <= 1) {
            return inputList;
        }
        List<Map.Entry<String,String>> firstList = new ArrayList<>();
        List<Map.Entry<String,String>> secondList = new ArrayList<>();

        int i = 0;
        for(Map.Entry<String, String> e : inputList){
            (i++ % 2 == 0 ? firstList:secondList).add(e);
        }

//        int middle = inputList.size() / 2;
//        ArrayList<String> left = new ArrayList<>(inputList.subList(0, middle));
//        ArrayList<String> right = new ArrayList<>(inputList.subList(middle, inputList.size()));

        firstList = mergeSort(firstList);
        secondList = mergeSort(secondList);

        return merge(firstList, secondList);
    }

    //Merge two arraylist and alphabetize them
    private static List<Map.Entry<String, String>> merge(List<Map.Entry<String, String>> left, List<Map.Entry<String, String>> right) {
        List<Map.Entry<String, String>> result = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            Map.Entry<String, String> leftElement = left.get(leftIndex);
            Map.Entry<String, String> rightElement = right.get(rightIndex);

            if (leftElement.getKey().compareTo(rightElement.getKey()) < 0) {
                result.add(leftElement);
                leftIndex++;
            } else {
                result.add(rightElement);
                rightIndex++;
            }
        }

//        result.addAll(left.subList(leftIndex, left.size()));
//        result.addAll(right.subList(rightIndex, right.size()));

        return result;
    }
}