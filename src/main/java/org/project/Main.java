package org.project;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static JFrame frame;
    private static JTextPane originalInputPane;
    private static JTextPane circularShiftedTitlesPane;
    private static JTextPane individualTitlesAlphabetizedPane;
    private static JTextPane totalAlphabetizedListPane;
    private static Map<String, Color> inputColorMap = new HashMap<>();
    private static ArrayList<String> totalAlphabetizedList = new ArrayList<>();
    private static ArrayList<ArrayList<String>> alphabetizedLists = new ArrayList<>();
    private static List<Color> assignedColor = new ArrayList<>();
    private static CircularShift circularShift = new CircularShift();
    private static Alphabetizer alphabetize = new Alphabetizer();
    private static List<Color> availableColors = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        frame = new JFrame("KWIC Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold all the output boxes with GridLayout
        JPanel outputPanel = new JPanel(new GridLayout(2, 2));

        originalInputPane = createTextPane("Original Input");
        circularShiftedTitlesPane = createTextPane("Circular Shifted Titles");
        individualTitlesAlphabetizedPane = createTextPane("Individual Titles Alphabetized");
        totalAlphabetizedListPane = createTextPane("Total Alphabetized List");

        // Add output panes to the output panel
        outputPanel.add(originalInputPane);
        outputPanel.add(circularShiftedTitlesPane);
        outputPanel.add(individualTitlesAlphabetizedPane);
        outputPanel.add(totalAlphabetizedListPane);

        // Create an input field
        JTextField inputField = createInputField();

        // Create a panel to hold the input field and the output panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputField, BorderLayout.NORTH);
        mainPanel.add(outputPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }

    private static JTextPane createTextPane(String title) {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK), title));
        return textPane;
    }
 
    private static JTextField createInputField() {
        JTextField inputField = new JTextField();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userInput = inputField.getText();
                Color inputColor = getNextAvailableColor();
                assignedColor.add(inputColor);
                totalAlphabetizedListPane.setText("");

                // Store the input color
                inputColorMap.put(userInput, inputColor);

                // Display original input with the specified color
                appendToTextPane(originalInputPane, userInput, inputColor);

                // Perform Circular Shift
                ArrayList<String> circularShiftedTitles = circularShift.shift(userInput);

                // Display Circular Shifted Titles with the specified color
                appendToTextPane(circularShiftedTitlesPane, circularShiftedTitles, inputColor);

                // Perform Alphabetization
                ArrayList<String> individualTitlesAlphabetized = alphabetize.Alphabetize(circularShiftedTitles);

                // Display Individual Titles Alphabetized with the specified color
                appendToTextPane(individualTitlesAlphabetizedPane, individualTitlesAlphabetized, inputColor);

                alphabetizedLists.add(individualTitlesAlphabetized);

                // Update and display Total Alphabetized List
                totalAlphabetizedList.addAll(individualTitlesAlphabetized);
                totalAlphabetizedList = alphabetize.Alphabetize(totalAlphabetizedList);
                appendToAlphabeticalPane(totalAlphabetizedListPane, totalAlphabetizedList);

                // Clear the input field for the next input
                inputField.setText("");
            }
        });

        return inputField;
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

    private static void appendToTextPane(JTextPane textPane, List<String> lines, Color color) {
        for (String line : lines) {
            appendToTextPane(textPane, line, color);
        }
    }

    private static void appendToAlphabeticalPane(JTextPane textPane, String text, Color color){
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet style = new SimpleAttributeSet();
        StyleConstants.setForeground(style, color);

        try {
            doc.insertString(doc.getLength(), text + "\n", style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

    }

    private static void appendToAlphabeticalPane(JTextPane textPane, List<String> lines) {
        Color color = Color.BLACK;
        for (String line : lines) {
            for(ArrayList<String> title : alphabetizedLists){
                if(title.contains(line)){
                    color = assignedColor.get(alphabetizedLists.indexOf(title));
                }
            }
            appendToAlphabeticalPane(textPane, line, color);
        }
    }

    private static Color getNextAvailableColor() {
        if (availableColors.isEmpty()) {
            // If all colors have been used, reset the list
            availableColors.addAll(Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.CYAN));
        }
        return availableColors.remove(0);
    }
}
      