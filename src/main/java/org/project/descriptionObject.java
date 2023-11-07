package org.project;

import java.util.ArrayList;

public class descriptionObject {
    String url;
    String originalDescriptor;
    String noiselessDescriptor;
    ArrayList<String> circularShifted;
    ArrayList<String> alphabetized;
    int frequency;

    public descriptionObject(String url, String originalDescriptor, String noiselessDescriptor, ArrayList<String> circularShifted, ArrayList<String> alphabetized) {
        this.url = url;
        this.originalDescriptor = originalDescriptor;
        this.noiselessDescriptor = noiselessDescriptor;
        this.circularShifted = circularShifted;
        this.alphabetized = alphabetized;
        frequency = 0;
    }

    public String getURL() { return url; }

    public void setURL(String url) {
        this.url = url;
    }

    public String getOriginalDescriptor() { return originalDescriptor; }

    public void setOriginalDescriptor(String originalDescriptor) {
        this.originalDescriptor = originalDescriptor;
    }

    public String getNoiselessDescriptor() { return noiselessDescriptor; }

    public void setNoiselessDescriptor(String noiselessDescriptor) {
        this.noiselessDescriptor = noiselessDescriptor;
    }

    public ArrayList<String> getCircularShifted() { return circularShifted; }

    public void setCircularShifted(ArrayList<String> circularShifted) {
        this.circularShifted = circularShifted;
    }

    public ArrayList<String> getAlphabetized() { return alphabetized; }

    public void setAlphabetized(ArrayList<String> alphabetized) {
        this.alphabetized = alphabetized;
    }

    public int getFrequency() { return frequency; }

    public void incrementFrequency() {frequency++;}

    public void resetFrequency() {frequency = 0;}
}
