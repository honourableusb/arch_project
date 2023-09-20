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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class Main {

    private static Color color1 = Color.BLUE;
    private static Color color2 = Color.RED;
    private static Color currentColor = color1;
    /*
    public static void main(String[] input) {
        try {
            //read input
            //TODO update this to a conf.xml property? to discuss with team.
            //String filepath = "resources/input.txt";
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
            drawGUI();
            //output
            return 1;
        } catch (Exception e) {
            System.out.println(e);
            //either continue or throw -1
            return -1;
        }
    }
    */
    public static void main(String[] input){
        // Create a JFrame (window)
        JFrame frame = new JFrame("The KWIC System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300); // Set the window size

        // Create a JPanel to hold components using a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        // Create a GridBagConstraints object to control component placement
        GridBagConstraints gbc = new GridBagConstraints();

        // Create a JLabel
        JLabel label = new JLabel("Enter text:");

        // Create a JTextField (input box) with a dynamic width and fixed height
        JTextField textField = new JTextField(10); // 10 columns wide
        textField.setPreferredSize(new Dimension(0, 30)); // Set the height, width is calculated dynamically

        // Create a JButton
        JButton submitButton = new JButton("Submit");

        // Create a JTextPane with a dynamic width and fixed height
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false); // Make it read-only
        textPane.setPreferredSize(new Dimension(0, 150)); // Set the height, width is calculated dynamically

        // Create a JScrollPane for the text pane
        JScrollPane scrollPane = new JScrollPane(textPane);

        // Add a border to the textPane (for example, a black line border)
        textPane.setBorder(new LineBorder(Color.BLACK));

        // Add components to the panel using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 20, 0, 20); // 10% margin on both sides
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.8; // Allow the textField to expand horizontally (80% of available width)
        panel.add(textField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.1; // Make some space between textField and submitButton (10% of available width)
        panel.add(submitButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // Allow the textPane to expand horizontally
        panel.add(scrollPane, gbc);

        // Define an ActionListener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the text from the text field
                String inputText = textField.getText();

                // Append the submitted text to the textPane with alternating colors
                appendToTextPane(textPane, inputText, currentColor);

                // Toggle between the two colors
                currentColor = (currentColor == color1) ? color2 : color1;

                // Clear the text field for the next input
                textField.setText("");
            }
        });

        // Add the panel to the frame
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static void appendToTextPane(JTextPane textPane, String text, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);

        try {
            doc.insertString(doc.getLength(), text + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    //TODO Creates popup but needs to change to send data where it is needed
    private static void processInput(String inputText) {
        JOptionPane.showMessageDialog(null, "You entered: " + inputText, "Input Received", JOptionPane.INFORMATION_MESSAGE);
    }
}