//UTD SE 6362 F23 Deliverable -- KWIC Algorithm
//Team Members:
// - Shariq Azeem
// - Ayush Bhardwaj
// - Jeremiah De Luna
// - Matthew Haskell
// - Eddie Villareal

//Main.java -- this is our orchestrator
package org.project;

import java.lang.module.Configuration;

public class Main {
    public int main(String[] input) {
        try {
            //read input
            //TODO update this to a conf.xml property? to discuss with team.
            String filepath = "resources/input.txt";
            //reverse initialization due to dependencies on each object
            //maybe add resourcewatcher to see if checksum is valid on file every second, recompile if not?
            //alphabetize due to circular having dependencies on it.
            Alphabetizer a = new Alphabetizer();
            //create circular shifts
            CircularShift cs = new CircularShift(a);
            //do we explicitly invoke or have circularshift methods start in constructor?
            //store input
            Storage s = new Storage(cs);
            Input i = new Input(filepath, s);

            Output.print(a);

            //output
            return 1;
        } catch (Exception e) {
            System.out.println(e);
            //either continue or throw -1
            return -1;
        }
    }
}