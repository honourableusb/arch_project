package org.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class descriptionObject {
    String url;
    String originalDescriptor;
    String noiselessDescriptor;
    List<Map.Entry<String, String>> circularShifted;
    List<Map.Entry<String, String>> alphabetized;
    int frequency;
    int amtPaid;

    public descriptionObject(String url, String originalDescriptor, String noiselessDescriptor, List<Map.Entry<String, String>> circularShifted, List<Map.Entry<String, String>> alphabetized, int amtPaid) {
        this.url = url;
        this.originalDescriptor = originalDescriptor;
        this.noiselessDescriptor = noiselessDescriptor;
        this.circularShifted = circularShifted;
        this.alphabetized = alphabetized;
        frequency = 0;
        this.amtPaid = amtPaid;
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

    public List<Map.Entry<String, String>> getCircularShifted() { return circularShifted; }

    public void setCircularShifted(List<Map.Entry<String, String>> circularShifted) {
        this.circularShifted = circularShifted;
    }

    public List<Map.Entry<String, String>> getAlphabetized() { return alphabetized; }

    public void setAlphabetized(List<Map.Entry<String, String>> alphabetized) {
        this.alphabetized = alphabetized;
    }

    public int getFrequency() { return frequency; }

    public void incrementFrequency() {frequency++;}

    public void resetFrequency() {frequency = 0;}

    public void setAmtPaid(int amtPaid) {
        this.amtPaid = amtPaid;
    }

    public int getAmtPaid() {
        return this.amtPaid;
    }
}
