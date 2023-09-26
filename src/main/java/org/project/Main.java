//UTD SE 6362 F23 Deliverable -- KWIC Algorithm
//Team Members:
// - Shariq Azeem
// - Ayush Bhardwaj
// - Jeremiah De Luna
// - Matthew Haskell
// - Eddie Villareal

//Main.java -- this is our orchestrator
package org.project;

import java.util.ArrayList;
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
    private static ArrayList<String> totalList = new ArrayList<>();
   
    public static void main(String[] input){
        // Instantiate the necessaru classes 
        CircularShift circularShift = new CircularShift();
        Alphabetizer alphabetizer = new Alphabetizer();
        
        // Create a JFrame (window)
        JFrame frame = new JFrame("The KWIC System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300); // Set the window size

        // Create a JPanel to hold components using a GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());

        // Create a GridBagConstraints object to control component placement
        GridBagConstraints gbc = new GridBagConstraints();

        // Create a JLabel for each text box
        JLabel label = new JLabel("Enter text:");
        JLabel inputLabel = new JLabel("Titles entered");
        JLabel shiftLabel = new JLabel("Circular Shifted");
        JLabel alphaLabel = new JLabel("Alphabetical per Title");
        JLabel alphaTotalLabel = new JLabel("Alphabetical");

        // Create a JTextField (input box) with a dynamic width and fixed height
        JTextField inputField = new JTextField(10); // 10 columns wide
        inputField.setPreferredSize(new Dimension(0, 30)); // Set the height, width is calculated dynamically

        // Create a JButton
        JButton submitButton = new JButton("Submit");

        // Create a JTextPane to display all inputted titles
        JTextPane inputPanel = new JTextPane();
        inputPanel.setEditable(false); // Make it read-only
        inputPanel.setPreferredSize(new Dimension(0, 150)); // Set the height, width is calculated dynamically

        // Create a JScrollPane for the text pane
        JScrollPane scrollPanel = new JScrollPane(inputPanel);

        // Add a border to the inputPanel 
        inputPanel.setBorder(new LineBorder(Color.BLACK));

        // Create a JTextPane to display all Circular Shifted titles
        JTextPane circularShiftPanel = new JTextPane();
        circularShiftPanel.setEditable(false); // Make it read-only
        circularShiftPanel.setPreferredSize(new Dimension(0, 150)); // Set the height, width is calculated dynamically
        JScrollPane scrollPanelCS = new JScrollPane(circularShiftPanel);
        circularShiftPanel.setBorder(new LineBorder(Color.BLACK));

        // Create a JTextPane to display alphabetized Shifted titles
        JTextPane alphabetizerPanel = new JTextPane();
        alphabetizerPanel.setEditable(false); // Make it read-only
        alphabetizerPanel.setPreferredSize(new Dimension(0, 150)); // Set the height, width is calculated dynamically
        JScrollPane scrollPanelAlpha = new JScrollPane(alphabetizerPanel);
        alphabetizerPanel.setBorder(new LineBorder(Color.BLACK));

        // Create a JTextPane to display all alphabetized titles
        JTextPane alphabetizerTotalPanel = new JTextPane();
        alphabetizerTotalPanel.setEditable(false); // Make it read-only
        alphabetizerTotalPanel.setPreferredSize(new Dimension(0, 150)); // Set the height, width is calculated dynamically
        JScrollPane scrollPanelAlphaTotal = new JScrollPane(alphabetizerTotalPanel);
        alphabetizerTotalPanel.setBorder(new LineBorder(Color.BLACK));

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
        panel.add(inputField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.1; // Make some space between textField and submitButton (10% of available width)
        panel.add(submitButton, gbc);

        // Add components to the panel using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 20, 0, 20); // 10% margin on both sides
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(inputLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // Allow the textPane to expand horizontally
        panel.add(scrollPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 20, 0, 20); // 10% margin on both sides
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(shiftLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // Allow the textPane to expand horizontally
        panel.add(scrollPanelCS, gbc);

        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 20, 0, 20); // 10% margin on both sides
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(alphaLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 16;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // Allow the textPane to expand horizontally
        panel.add(scrollPanelAlpha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 19;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 20, 0, 20); // 10% margin on both sides
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(alphaTotalLabel, gbc);

         gbc.gridx = 0;
        gbc.gridy = 22;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; // Allow the textPane to expand horizontally
        panel.add(scrollPanelAlphaTotal, gbc);

        // Allows 'enter' to activate button along with pressing it
        frame.getRootPane().setDefaultButton(submitButton);

        // Define an ActionListener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear our the total Alphabetical panel
                alphabetizerTotalPanel.setText("");
                // Get the text from the text field
                String inputText = inputField.getText();
                // Handle Ciruclar Shift
                ArrayList<String> csResults = new ArrayList<>();
                csResults = circularShift.shift(inputText);
                // Alphaetize single title
                ArrayList<String> alphaResults = new ArrayList<>();
                alphaResults = alphabetizer.Alphabetize(csResults);
                // Alphabetize all titles
                totalList.addAll(alphaResults);
                totalList = alphabetizer.Alphabetize(totalList);

                // Append the submitted text to the textPane with alternating colors
                appendToInputPanel(inputPanel, inputText, currentColor);
                appendToCSPanel(circularShiftPanel, csResults, currentColor);
                appendToAlphaPanel(alphabetizerPanel, alphaResults, currentColor);
                appendToAlphaTotalPanel(alphabetizerTotalPanel, totalList, color1, color2);

                // Toggle between the two colors
                currentColor = (currentColor == color1) ? color2 : color1;

                // Clear the text field for the next input
                inputField.setText("");
            }
        });

        // Add the panel to the frame
        frame.add(panel);

        // Make the frame visible
        frame.setVisible(true);
    }

    private static void appendToInputPanel(JTextPane textPane, String text, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);

        try {
            doc.insertString(doc.getLength(), text + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private static void appendToCSPanel(JTextPane textPane, ArrayList<String> text, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);

        try {
            for (String result : text) {
                doc.insertString(doc.getLength(), result + "\n", style);
            }
            doc.insertString(doc.getLength(), "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private static void appendToAlphaPanel(JTextPane textPane, ArrayList<String> text, Color color) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);

        try {
            for (String result : text) {
                doc.insertString(doc.getLength(), result + "\n", style);
            }
            doc.insertString(doc.getLength(), "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private static void appendToAlphaTotalPanel(JTextPane textPane, ArrayList<String> text, Color color1, Color color2) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        Color color = color1;

        try {
            for (String result : text) {
                color = (color == color1) ? color2 : color1;
                StyleConstants.setForeground(style, color);
                doc.insertString(doc.getLength(), result + "\n", style);
            }
            doc.insertString(doc.getLength(), "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}