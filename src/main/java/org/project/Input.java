package org.project;

import java.io.*;
import java.util.*;

public class Input {
    String filepath; 
    HashMap<String, descriptionObject> dataMap;
    List<Map.Entry<String, String>> totalList;
    AutoFiller auto = new AutoFiller();

    Input(String filepath) {
        this.filepath = filepath;
        this.dataMap = new HashMap<String, descriptionObject>();
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setAutoFiller(AutoFiller auto) {
        this.auto = auto;
    }

    public AutoFiller getAutofiller() {
        return auto;
    }

    public HashMap<String, descriptionObject> processInputFile() {        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            NoiseRemover noiseRemover = new NoiseRemover();
            CircularShift circularShifter = new CircularShift();
            Alphabetizer alphabetizer = new Alphabetizer();
            List<Map.Entry<String,String>> circularShifted;
            List<Map.Entry<String,String>> alphabetized;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming tab-separated values
                if (parts.length == 2) {
                    //Break the input string into URL and Descriptor
                    String key = parts[0];
                    String originalDescription = parts[1];
                    
                    String quietWord = noiseRemover.removeNoiseWordsAndSymbols(originalDescription);
                    circularShifted = circularShifter.shift(quietWord, originalDescription);
                    alphabetized = alphabetizer.Alphabetize(circularShifted, null);
                    
                    descriptionObject descObject = new descriptionObject(key, originalDescription, quietWord, circularShifted, alphabetized, 0);
                    dataMap.put(originalDescription, descObject);
                    auto.addUniqueWordsFromString(originalDescription);
                    //Merge the new alphabetized title with the total list of alphabetized titles
                    totalList = alphabetizer.Alphabetize(alphabetized, totalList);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }

            reader.close();
            writeTotalList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap; 
    }

    private void writeTotalList() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("totalList.txt"));
        bw.write(totalList.toString());
        bw.close();
    }

    public List<Map.Entry<String, String>> getTotalList() { return totalList; }
    public HashMap<String, descriptionObject> getInputItems() { return dataMap; }
    public void addInputItem(String url, String newTitle) {
        NoiseRemover noiseRemover = new NoiseRemover();
        CircularShift circularShifter = new CircularShift();
        Alphabetizer alphabetizer = new Alphabetizer();
        List<Map.Entry<String, String>> circularShifted = new ArrayList<>();
        List<Map.Entry<String, String>> alphabetized = new ArrayList<>();

        String quietWord = noiseRemover.removeNoiseWordsAndSymbols(newTitle);
        circularShifted = circularShifter.shift(quietWord, newTitle);
        alphabetized = alphabetizer.Alphabetize(circularShifted, null);

        descriptionObject descObject = new descriptionObject(url, newTitle, quietWord, circularShifted, alphabetized, 0);
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
        for(Map.Entry<String, String> entry: original.circularShifted){
            totalList.remove(entry.getKey());
        }
    }
}
