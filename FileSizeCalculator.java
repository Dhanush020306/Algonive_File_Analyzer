import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileSizeCalculator extends JFrame implements ActionListener {

    private JTextArea resultArea;
    private JButton selectButton;

 public FileSizeCalculator() {
    setTitle("Algonive Document Analyzer"); // Mentioning the company [cite: 1]
    setSize(500, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(20, 20));

    // 1. Header with Background Color
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(41, 128, 185)); // Professional Blue
    JLabel header = new JLabel("FILE ANALYZER", JLabel.CENTER);
    header.setForeground(Color.WHITE);
    header.setFont(new Font("Segoe UI", Font.BOLD, 24));
    header.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
    headerPanel.add(header);

    // 2. Center: Result Display
    resultArea = new JTextArea();
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    resultArea.setEditable(false);
    resultArea.setBackground(new Color(245, 245, 245)); // Light Grey
    resultArea.setMargin(new Insets(10, 10, 10, 10));

    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Analysis Results"));

    // 3. Bottom: Action Button
    JPanel footerPanel = new JPanel();
    selectButton = new JButton("Select Document");
    selectButton.setPreferredSize(new Dimension(200, 45));
    selectButton.setFocusPainted(false);
    selectButton.addActionListener(this);
    footerPanel.add(selectButton);
    footerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

    // Add components to Frame
    add(headerPanel, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    add(footerPanel, BorderLayout.SOUTH);

    setVisible(true);
}

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
    fileChooser.setFileFilter(filter);
    
    // Optional: Disable the "All Files" option to force text selection
    fileChooser.setAcceptAllFileFilterUsed(false);
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            File file = fileChooser.getSelectedFile();

            try {

                long sizeBytes = file.length();
                double sizeKB = sizeBytes / 1024.0;
                double sizeMB = sizeKB / 1024.0;

                String text = new String(Files.readAllBytes(file.toPath()));

                int charCount = text.length();
                String[] words = text.trim().split("\\s+");
                int wordCount = words.length;

                String longestWord = "";
                int totalLength = 0;

                for (String word : words) {
                    totalLength += word.length();
                    if (word.length() > longestWord.length()) {
                        longestWord = word;
                    }
                }

                double averageWordLength = (double) totalLength / wordCount;

                resultArea.setText(
                        "File Name           : " + file.getName() + "\n\n" +
                        "File Size (Bytes)   : " + sizeBytes + "\n" +
                        "File Size (KB)      : " + String.format("%.2f", sizeKB) + "\n" +
                        "File Size (MB)      : " + String.format("%.4f", sizeMB) + "\n\n" +
                        "Word Count          : " + wordCount + "\n" +
                        "Character Count     : " + charCount + "\n" +
                        "Longest Word        : " + longestWord + "\n" +
                        "Average Word Length : " + String.format("%.2f", averageWordLength)
                );

            } catch (IOException ex) {
                resultArea.setText("Error reading file.");
            }
        }
    }

    public static void main(String[] args) {
        new FileSizeCalculator();
    }
}