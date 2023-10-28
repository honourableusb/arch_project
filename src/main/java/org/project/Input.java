package org.project;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Input {
    String filepath; 
    HashMap<String, descriptionObject> dataMap;

    Input(String filepath) {
        this.filepath = filepath;
        this.dataMap = new HashMap<String, descriptionObject>();
    }

    public HashMap<String, descriptionObject> processInputFile() {        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String line;
            NoiseRemover noiseRemover = new NoiseRemover();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t"); // Assuming tab-separated values
                if (parts.length == 2) {
                    String key = parts[0];
                    String originalDescription = parts[1];
                    String quietWord = noiseRemover.removeNoiseWordsAndSymbols(originalDescription);
                    descriptionObject descObject = new descriptionObject(originalDescription, quietWord, new ArrayList<>(), new ArrayList<>());
                    dataMap.put(key, descObject);
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

    public HashMap<String, descriptionObject> getInputItems() { return dataMap; }
    public void addInputItem(String key, descriptionObject descriptor) { dataMap.put(key, descriptor); }
    public void removeInputItem(String key) { dataMap.remove(key); }
}

/*
 * noiseWords = new ArrayList<>(Arrays.asList(
            "a", "an", "the", "in", "on", "at", "by", "with", "to", 
            "from", "of", "for", "and", "or", "but", "if", "as", "is", "are", "was", 
            "were", "have", "has", "be", "been", "am", "will", "do", "does", "did"
        ));
 */