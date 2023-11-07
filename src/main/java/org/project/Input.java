package org.project;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Input {
    String filepath; 
    HashMap<String, descriptionObject> dataMap;
    ArrayList<String> totalList;

    Input(String filepath) {
        this.filepath = filepath;
        this.dataMap = new HashMap<String, descriptionObject>();
    }

    public HashMap<String, descriptionObject> processInputFile() {        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            NoiseRemover noiseRemover = new NoiseRemover();
            CircularShift circularShifter = new CircularShift();
            Alphabetizer alphabetizer = new Alphabetizer();
            ArrayList<String> circularShifted = new ArrayList<>();
            ArrayList<String> alphabetized = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming tab-separated values
                if (parts.length == 2) {
                    //Break the input string into URL and Descriptor
                    String key = parts[0];
                    String originalDescription = parts[1];
                    
                    String quietWord = noiseRemover.removeNoiseWordsAndSymbols(originalDescription);
                    circularShifted = circularShifter.shift(quietWord);
                    alphabetized = alphabetizer.Alphabetize(circularShifted, null);
                    
                    descriptionObject descObject = new descriptionObject(key, originalDescription, quietWord, circularShifted, alphabetized);
                    dataMap.put(key, descObject);

                    //Merge the new alphabetized title with the total list of alphabetized titles
                    totalList = alphabetizer.Alphabetize(alphabetized, totalList);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }

            reader.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap; 
    }

    public ArrayList<String> getTotalList() { return totalList; }
    public HashMap<String, descriptionObject> getInputItems() { return dataMap; }
    public void addInputItem(String url, String newTitle) {
        NoiseRemover noiseRemover = new NoiseRemover();
        CircularShift circularShifter = new CircularShift();
        Alphabetizer alphabetizer = new Alphabetizer();
        ArrayList<String> circularShifted = new ArrayList<>();
        ArrayList<String> alphabetized = new ArrayList<>();

        String quietWord = noiseRemover.removeNoiseWordsAndSymbols(newTitle);
        circularShifted = circularShifter.shift(quietWord);
        alphabetized = alphabetizer.Alphabetize(circularShifted, null);

        descriptionObject descObject = new descriptionObject(url, newTitle, quietWord, circularShifted, alphabetized);
        dataMap.put(url, descObject); 

        //Merge the new alphabetized title with the total list of alphabetized titles
        totalList = alphabetizer.Alphabetize(alphabetized, totalList);
    }
    public boolean removeInputItem(String key) { 
        if(dataMap.containsKey(key)){
            removeInputFromTotal(dataMap.get(key));
            dataMap.remove(key); 
            return true;
        }
        return false;
    }

    private void removeInputFromTotal(descriptionObject original){
        for(String entry: original.circularShifted){
            if(totalList.contains(entry)){
                totalList.remove(entry);
            }
        }
    }
}
