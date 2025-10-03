import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class TimetableApp extends JFrame {
    private String selectedProgram = "";
    private String selectedSemesterType = "";
    private String selectedSemester = "";
    private List<String> selectedDays = new ArrayList<>();
    private String semesterDuration = "";

    private Map<String, Map<String, List<String>>> programSubjects = new HashMap<>();
    private Map<String, Map<String, List<String>>> programLabs = new HashMap<>();

    public TimetableApp() {
        setTitle("Timetable Program");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadSubjectsFromCSV(); // Load subjects from CSV on initialization
        showSelectProgramFrame();
    }

private void loadSubjectsFromCSV() {
    try {
        // Load theory subjects
        loadSubjects("theorySubjects.csv", programSubjects);
        // Load lab subjects
        loadSubjects("labSubjects.csv", programLabs);
    } catch (IOException e) {
        JOptionPane.showMessageDialog(this, "Error loading subjects from CSV files: " + e.getMessage(), "File Load Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace(); // Log the exception to the console for debugging
    }
}

    private void loadSubjects(String filename, Map<String, Map<String, List<String>>> subjectsMap) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String program = parts[0].trim();
                String semester = parts[1].trim();
                String subject = parts[2].trim();

                // If the program is not in the map, add it
                subjectsMap.putIfAbsent(program, new HashMap<>());
                // If the semester is not in the map, add it
                subjectsMap.get(program).putIfAbsent(semester, new ArrayList<>());
                // Add the subject to the semester list
                subjectsMap.get(program).get(semester).add(subject);
            }
        }
    }

    private void showSelectProgramFrame() {
        JFrame frame = new JFrame("Select Program");
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Select Program:");
        String[] programs = {"ASH First Year", "CSE", "IT", "ENTC", "MECH", "ELPO"}; // Added ELPO to the programs list
        JComboBox<String> programComboBox = new JComboBox<>(programs);
        programComboBox.addActionListener(e -> selectedProgram = (String) programComboBox.getSelectedItem());

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            frame.dispose();
            showSelectSemesterTypeFrame();
        });

        frame.add(label);
        frame.add(programComboBox);
        frame.add(nextButton);
        frame.setVisible(true);
    }

    private void showSelectSemesterTypeFrame() {
        JFrame frame = new JFrame("Select Semester Type");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(6, 1));

        ButtonGroup semesterTypeGroup = new ButtonGroup();
        JRadioButton autumnButton = new JRadioButton("Autumn");
        JRadioButton springButton = new JRadioButton("Spring");
        semesterTypeGroup.add(autumnButton);
        semesterTypeGroup.add(springButton);

        autumnButton.addActionListener(e -> selectedSemesterType = "Autumn");
        springButton.addActionListener(e -> selectedSemesterType = "Spring");

        JComboBox<String> semesterComboBox = new JComboBox<>();
        semesterComboBox.addActionListener(e -> selectedSemester = (String) semesterComboBox.getSelectedItem());

        // Conditional semester selection based on program
        autumnButton.addActionListener(e -> {
            semesterComboBox.removeAllItems();
            if (selectedProgram.equals("ASH First Year")) {
                semesterComboBox.addItem("Semester 1 (Group A)");
                semesterComboBox.addItem("Semester 1 (Group B)");
            } else if (selectedProgram.equals("ELPO")) {
                semesterComboBox.addItem("Semester 3");
                semesterComboBox.addItem("Semester 5");
                semesterComboBox.addItem("Semester 7");
            } else {
                semesterComboBox.addItem("Semester 3");
                semesterComboBox.addItem("Semester 5");
                semesterComboBox.addItem("Semester 7");
            }
        });

        springButton.addActionListener(e -> {
            semesterComboBox.removeAllItems();
            if (selectedProgram.equals("ASH First Year")) {
                semesterComboBox.addItem("Semester 2 (Group A)");
                semesterComboBox.addItem("Semester 2 (Group B)");
            } else if (selectedProgram.equals("ELPO")) {
                semesterComboBox.addItem("Semester 4");
                semesterComboBox.addItem("Semester 6");
                semesterComboBox.addItem("Semester 8");
            } else {
                semesterComboBox.addItem("Semester 4");
                semesterComboBox.addItem("Semester 6");
                semesterComboBox.addItem("Semester 8");
            }
        });

        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if (selectedSemester.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Select correct Semester and Try Again!");
            } else {
                frame.dispose();
                showSelectDaysFrame();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            showSelectProgramFrame();
        });

        frame.add(autumnButton);
        frame.add(springButton);
        frame.add(semesterComboBox);
        frame.add(nextButton);
        frame.add(backButton);
        frame.setVisible(true);
    }

    private void showSelectDaysFrame() {
        JFrame frame = new JFrame("Select Active Days");
        frame.setSize(400, 600);
        frame.setLayout(new GridLayout(8, 1));
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        for (String day : days) {
            JCheckBox checkBox = new JCheckBox(day);
            checkBox.addActionListener(e -> {
                if (checkBox.isSelected()) {
                    selectedDays.add(day);
                } else {
                    selectedDays.remove(day);
                }
            });
            frame.add(checkBox);
        }

        JLabel durationLabel = new JLabel("Enter Semester Duration (e.g., July to Dec):");
        JTextField durationField = new JTextField();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            semesterDuration = durationField.getText();
            frame.dispose();
            showGenerateTimetableFrame();
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            showSelectSemesterTypeFrame();
        });

        frame.add(durationLabel);
        frame.add(durationField);
        frame.add(nextButton);
        frame.add(backButton);
        frame.setVisible(true);
    }

    private void showGenerateTimetableFrame() {
        JFrame frame = new JFrame("Generate Timetable");
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        JTextArea timetableArea = new JTextArea();
        timetableArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(timetableArea);

        JButton generateButton = new JButton("Generate Timetable");
        generateButton.addActionListener(e -> {
            String timetable = generateTimetable();
            timetableArea.setText(timetable);
        });

        JButton exportButton = new JButton("Export Timetable");
        exportButton.addActionListener(e -> {
            // Logic to export timetable (not implemented)
            JOptionPane.showMessageDialog(frame, "Export functionality is not implemented yet.");
        });

        // Add Show Syllabus button
        JButton syllabusButton = new JButton("Show Syllabus");
        syllabusButton.addActionListener(e -> {
            showSyllabus(selectedProgram, selectedSemester);
        });

        frame.add(generateButton, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(exportButton, BorderLayout.SOUTH);

        // Create a panel for buttons at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(syllabusButton);
        buttonPanel.add(exportButton);
        
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

private String generateTimetable() {
    StringBuilder sb = new StringBuilder();
    sb.append("Timetable for ").append(selectedProgram).append(" - ").append(selectedSemester).append("\n");
    sb.append("Semester Duration: ").append(semesterDuration).append("\n");

    List<String> weekdaysOrder = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");
    List<String> sortedDays = new ArrayList<>(selectedDays);
    sortedDays.sort((day1, day2) -> Integer.compare(weekdaysOrder.indexOf(day1), weekdaysOrder.indexOf(day2)));

    sb.append("Active Days: ").append(sortedDays).append("\n");
    sb.append("-------------------------------------------------\n");

    int startHour = 11; // Starting time is 11 AM
    for (String day : sortedDays) {
        sb.append(day).append(":\n");

        List<String> semesterTheorySubjects = new ArrayList<>();
        List<String> semesterLabSubjects = new ArrayList<>();

        if (programSubjects.containsKey(selectedProgram) && programSubjects.get(selectedProgram).containsKey(selectedSemester)) {
            semesterTheorySubjects.addAll(programSubjects.get(selectedProgram).get(selectedSemester));
        }
        if (programLabs.containsKey(selectedProgram) && programLabs.get(selectedProgram).containsKey(selectedSemester)) {
            semesterLabSubjects.addAll(programLabs.get(selectedProgram).get(selectedSemester));
        }

        // Shuffle the subjects for the day
        Collections.shuffle(semesterTheorySubjects);
        Collections.shuffle(semesterLabSubjects);

        // Schedule theory subjects first
        for (String subject : semesterTheorySubjects) {
            if (startHour == 13) {
                break;
            }
            sb.append(String.format("%02d:00 - %02d:00: %s (Theory)\n", startHour, startHour + 1, subject));
            startHour += 1;
        }

        sb.append("01:00 - 01:15: Break\n");

        // Schedule lab subjects
        for (String subject : semesterLabSubjects) {
            sb.append(String.format("01:15 - 03:15: %s (Lab)\n", subject));
        }

        sb.append("03:15 - 03:45: Long Break\n");

        int theorySubjectsRemaining = semesterTheorySubjects.size();
        if (theorySubjectsRemaining > 0) {
            sb.append(String.format("03:45 - 04:45: %s (Theory)\n", semesterTheorySubjects.get(theorySubjectsRemaining - 2)));
            sb.append(String.format("04:45 - 05:45: %s (Theory)\n", semesterTheorySubjects.get(theorySubjectsRemaining - 1)));
        }

        sb.append("-------------------------------------------------\n");
        startHour = 11; // Reset hour for the next day
    }

    return sb.toString();
}



    private void showSyllabus(String program, String semester) {
        // Define a map of program and semester to PDF file paths
        String syllabusFilePath = getSyllabusFilePath(program, semester);

        if (syllabusFilePath != null) {
            try {
                File syllabusFile = new File(syllabusFilePath);
                if (syllabusFile.exists()) {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(syllabusFile); // Open the PDF file in the default viewer
                } else {
                    JOptionPane.showMessageDialog(this, "Syllabus file not found for " + program + " - " + semester);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening syllabus file: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Syllabus not available for the selected program and semester.");
        }
    }

    private String getSyllabusFilePath(String program, String semester) {
        // Define the base directory where the syllabus PDFs are stored
        String baseDirectory = "syllabus_pdfs/";

        // Map the program and semester to the corresponding syllabus file
        switch (program) {
            case "ASH First Year":
                if (semester.equals("Semester 1 (Group A)") || semester.equals("Semester 1 (Group B)")) {
                    return baseDirectory + "ASH_FY_Semester_1.pdf";
                } else if (semester.equals("Semester 2 (Group A)") || semester.equals("Semester 2 (Group B)")) {
                    return baseDirectory + "ASH_FY_Semester_2.pdf";
                }
                break;
            case "CSE":
                if (semester.equals("Semester 3")) {
                    return baseDirectory + "CSE_Semester_3.pdf";
                } else if (semester.equals("Semester 4")) {
                    return baseDirectory + "CSE_Semester_4.pdf";
                } else if (semester.equals("Semester 5")) {
                    return baseDirectory + "CSE_Semester_5.pdf";
                }
                break;
            case "IT":
                if (semester.equals("Semester 3")) {
                    return baseDirectory + "IT_Semester_3.pdf";
                } else if (semester.equals("Semester 4")) {
                    return baseDirectory + "IT_Semester_4.pdf";
                } else if (semester.equals("Semester 5")) {
                    return baseDirectory + "IT_Semester_5.pdf";
                }
                break;
            case "ENTC":
                if (semester.equals("Semester 3")) {
                    return baseDirectory + "ENTC_Semester_3.pdf";
                } else if (semester.equals("Semester 4")) {
                    return baseDirectory + "ENTC_Semester_4.pdf";
                } else if (semester.equals("Semester 5")) {
                    return baseDirectory + "ENTC_Semester_5.pdf";
                }
                break;
            case "ELPO":
                if (semester.equals("Semester 3")) {
                    return baseDirectory + "ELPO_Semester_3.pdf";
                } else if (semester.equals("Semester 4")) {
                    return baseDirectory + "ELPO_Semester_4.pdf";
                } else if (semester.equals("Semester 5")) {
                    return baseDirectory + "ELPO_Semester_5.pdf";
                }
                break;
            case "MECH":
                if (semester.equals("Semester 3")) {
                    return baseDirectory + "MECH_Semester_3.pdf";
                } else if (semester.equals("Semester 4")) {
                    return baseDirectory + "MECH_Semester_4.pdf";
                } else if (semester.equals("Semester 5")) {
                    return baseDirectory + "MECH_Semester_5.pdf";
                }
                break;
            default:
                return null;
        }

        return null; // Return null if no matching syllabus found
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimetableApp());
    }
}
