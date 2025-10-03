# Academic Timetable Generator

A desktop application built with **Java Swing** that allows users to dynamically generate academic timetables for various engineering programs and semesters. The application loads course data from external CSV files and can link to syllabus documents for each program.

![Java Badge](https://img.shields.io/badge/Language-Java-blue?style=for-the-badge&logo=java)
![License Badge](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)

---

## 📋 Features

-   **User-Friendly GUI**: A simple, step-by-step graphical interface to guide the user.
-   **Dynamic Subject Loading**: Theory and lab subjects are loaded from `theorySubjects.csv` and `labSubjects.csv`, making the data easy to update without changing the code.
-   **Multi-Program Support**: Supports various academic programs (e.g., CSE, IT, MECH) and semesters.
-   **Customizable Schedule**: Users can select the specific days of the week to include in the timetable.
-   **Syllabus Integration**: A "Show Syllabus" feature that opens the relevant PDF document for the selected program and semester.
-   **Export Option**: Includes a placeholder for exporting the generated timetable (functionality not yet implemented).

---

## ⚙️ Tech Stack

-   **Language:** Java
-   **UI Framework:** Java Swing, Java AWT

---

## 🚀 Getting Started

Follow these instructions to get the project set up and running on your local machine.

### Prerequisites

You need to have the Java Development Kit (JDK) installed on your system. Version 8 or higher is recommended.

-   [Download JDK](https://www.oracle.com/java/technologies/downloads/)

### Project Structure

For the application to work correctly, you must have the following file and folder structure in your project's root directory:

Directory structure:
└── TimetableApp/
    ├── Docs/
    │   ├── Report/
    │   └── Report.pdf.pdf
    ├── labSubjects.csv
    ├── README.md
    ├── syllabus_pdfs/
    │   ├── ASH_FY_Semester_1.pdf
    │   ├── ASH_FY_Semester_2.pdf
    │   ├── CSE_Semester_3.pdf
    │   ├── CSE_Semester_4.pdf
    │   ├── CSE_Semester_5.pdf
    │   ├── docs/
    │   │   ├── ASH_FY_Semester_1.docx
    │   │   ├── ASH_FY_Semester_2.docx
    │   │   ├── CSE_Semester_3.docx
    │   │   ├── draft.docx
    │   │   ├── IT_Semester_3.docx
    │   │   └── Subjects.docx
    │   ├── ELPO_Semester_3.pdf
    │   ├── ELPO_Semester_4.pdf
    │   ├── ELPO_Semester_5.pdf
    │   ├── ENTC_Semester_3.pdf
    │   ├── ENTC_Semester_4.pdf
    │   ├── ENTC_Semester_5.pdf
    │   ├── IT_Semester_3.pdf
    │   ├── IT_Semester_4.pdf
    │   ├── IT_Semester_5.pdf
    │   ├── MECH_Semester_3.pdf
    │   ├── MECH_Semester_4.pdf
    │   └── MECH_Semester_5.pdf
    ├── temp/
    │   ├── commons-io-1.3.2.jar
    │   ├── commons-io-2.11.0.jar
    │   ├── commons-io-2.5.jar
    │   ├── commons-io-2.7.jar
    │   ├── LabSubjects.xlsx
    │   ├── lib/
    │   │   ├── commons-collections4-4.4.jar
    │   │   ├── commons-collections4-4.5.0-M1.jar
    │   │   ├── commons-collections4-4.5.0-M2.jar
    │   │   ├── commons-compress-1.27.1.jar
    │   │   ├── commons-io-2.17.0.jar
    │   │   ├── log4j-api-2.18.0.jar
    │   │   ├── log4j-core-2.18.0.jar
    │   │   ├── poi-5.3.0.jar
    │   │   ├── poi-ooxml-5.3.0.jar
    │   │   ├── poi-ooxml-schemas-4.1.2.jar
    │   │   └── xmlbeans-5.2.2.jar
    │   └── TheorySubjects.xlsx
    ├── theorySubjects.csv
    ├── TimetableApp.class
    └── TimetableApp.java.java


### Setup

1.  **Clone the repository (or download the files):**
    ```sh
    git clone <your-repository-url>
    cd <your-repository-directory>
    ```

2.  **Create the Data Files:**
    -   Create a file named `theorySubjects.csv`.
    -   Create a file named `labSubjects.csv`.
    -   The format for both files must be: `Program,Semester,SubjectName`

    **Example `theorySubjects.csv`:**
    ```csv
    CSE,Semester 3,Data Structures
    CSE,Semester 3,Digital Logic Design
    CSE,Semester 3,Discrete Mathematics
    IT,Semester 3,Object Oriented Programming
    IT,Semester 3,Computer Networks
    ```

    **Example `labSubjects.csv`:**
    ```csv
    CSE,Semester 3,Data Structures Lab
    CSE,Semester 3,Python Programming Lab
    IT,Semester 3,OOP Lab
    IT,Semester 3,Database Management Lab
    ```

3.  **Add Syllabus PDFs:**
    -   Create a folder named `syllabus_pdfs`.
    -   Place the corresponding syllabus PDF files inside this folder. The file names must match the ones specified in the `getSyllabusFilePath` method in the code (e.g., `CSE_Semester_3.pdf`).

### Compilation and Execution

1.  **Compile the Java code:**
    Open a terminal or command prompt in the project's root directory and run:
    ```sh
    javac TimetableApp.java
    ```

2.  **Run the application:**
    ```sh
    java TimetableApp
    ```

---

## 📖 How to Use

1.  Launch the application by running the `java TimetableApp` command.
2.  **Select Program**: Choose the academic program from the dropdown list and click "Next".
3.  **Select Semester**:
    -   Choose the semester type (Autumn/Spring).
    -   Select the specific semester from the dropdown that appears.
    -   Click "Next".
4.  **Select Days**:
    -   Check the boxes for the days the timetable should include (e.g., Monday, Tuesday).
    -   Enter the semester duration (e.g., "August to December").
    -   Click "Next".
5.  **Generate Timetable**:
    -   Click the **"Generate Timetable"** button to see the schedule.
    -   Click the **"Show Syllabus"** button to open the PDF syllabus for the selected program.

---

## 📄 License

This project is licensed under the MIT License. See the `LICENSE` file for details.