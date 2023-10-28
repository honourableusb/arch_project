//UTD SE 6362 F23 Deliverable -- KWIC Algorithm
//Team Members:
// - Shariq Azeem
// - Ayush Bhardwaj
// - Jeremiah De Luna
// - Matthew Haskell
// - Eddie Villareal

//CircularShift.java -- this creates the circular shifts for each input line
// example: I like programming -> like programming I -> programming I like
package org.project;

import java.util.ArrayList;

public class CircularShift {
    public CircularShift(){}
    public ArrayList<String> shift(String input) {
        String[] words = input.split(" ");
        ArrayList<String> permutations = new ArrayList<>();
        
        // Iterate through each word in the input string
        for (int i = 0; i < words.length; i++) {
            String[] rotatedWords = new String[words.length];
            
            // Create a rotated version of the words array
            for (int j = 0; j < words.length; j++) {
                rotatedWords[j] = words[(j + i) % words.length];
            }
            
            // Combine the rotated words into a single string
            String rotatedString = String.join(" ", rotatedWords);
            permutations.add(rotatedString);
        }
        
        return permutations;
    }
}