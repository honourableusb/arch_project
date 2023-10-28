package org.project;

import java.util.ArrayList;

public class descriptionObject {
    String originalDescriptor;
    String noiselessDescriptor;
    ArrayList<String> circularShifted;
    ArrayList<String> alphabetized;

    public descriptionObject(String originalDescriptor, String noiselessDescriptor, ArrayList<String> circularShifted, ArrayList<String> alphabetized) {
        this.originalDescriptor = originalDescriptor;
        this.noiselessDescriptor = noiselessDescriptor;
        this.circularShifted = circularShifted;
        this.alphabetized = alphabetized;
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
}
