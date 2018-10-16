/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA;

import TimetableGeneratorCS_IA.data.Subject;
import TimetableGeneratorCS_IA.data.Teacher;
import TimetableGeneratorCS_IA.data.Student;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author uczen
 */
public class ExcelTableGenerator {

    private TimetableGenerator TTGenerator;
    private String[][] studentsStringMatrix;
    private String[][] teachersStringMatrix;
    private String[][] subjectssStringMatrix;   
    private ArrayList<Student> students;
    private ArrayList<Teacher> teachers;
    private ArrayList<Subject> subjects;

    /**
     *
     * @param TTGenerator
     * @throws IOException
     * @throws InvalidFormatException
     */
    public ExcelTableGenerator(TimetableGenerator TTGenerator) throws IOException, InvalidFormatException {
        this.TTGenerator = TTGenerator;
    }

    private void convertStudentsToTable() throws IOException, InvalidFormatException {
        System.out.println(students.size());
        studentsStringMatrix = new String[students.size()][9];
        for (int i = 0; i < students.size(); i++) {
            studentsStringMatrix[i][0] = students.get(i).getName();
            studentsStringMatrix[i][1] = students.get(i).getSurname();
            Subject[] tempSubjectAr = students.get(i).getSubjects();
            for (int j = 0; j < tempSubjectAr.length; j++) {
                studentsStringMatrix[i][2 + j] = tempSubjectAr[j].getSubjectName();
            }
        }
    }

    private void convertSubjectsToTable() throws IOException, InvalidFormatException {
        subjectssStringMatrix = new String[subjects.size()][3];
        Teacher tempTeacher;
        for (int i = 0; i < subjects.size(); i++) {
            subjectssStringMatrix[i][0] = subjects.get(i).getSubjectName();
            tempTeacher = subjects.get(i).getTeacher();
            if (tempTeacher != null) {
                subjectssStringMatrix[i][1] = tempTeacher.getName() + " " + tempTeacher.getSurname();
            } else {
                subjectssStringMatrix[i][1] = "";
            }
            subjectssStringMatrix[i][2] = "" + subjects.get(i).getHours();
        }
    }

      private void convertTeachersToTable() throws IOException, InvalidFormatException {
        System.out.println(teachers.size());
        teachersStringMatrix = new String[teachers.size()][2];
        for (int i = 0; i < teachers.size(); i++) {
            teachersStringMatrix[i][0] = teachers.get(i).getName();
            teachersStringMatrix[i][1] = teachers.get(i).getSurname();
        }
    }  
    
    /**
     *
     * @param fileName
     * @param columnNames
     * @param matrix
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static void saveStringMatrixToExcel(String fileName, String[] columnNames, String[][] matrix) throws IOException, InvalidFormatException {
        Files.deleteIfExists(FileSystems.getDefault().getPath(fileName));
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow((short) 0);
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(bold(wb));
        }
        for (int i = 1; i < matrix.length + 1; i++) {
            row = sheet.createRow(i);
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i - 1][j] != null) {
                    if (matrix[i - 1][j].matches("[0-9]+")) {
                        row.createCell(j).setCellValue(Integer.parseInt(matrix[i - 1][j]));
                    } else {
                        row.createCell(j).setCellValue(matrix[i - 1][j]);
                    }
                }
            }
        }
        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            wb.write(fileOut);
        }
    }

    private static CellStyle bold(Workbook wb) {
        Font font = wb.createFont();
        font.setBold(true);

        CellStyle style = wb.createCellStyle();
        style.setFont(font);

        return style;
    }

    /**
     * @return the studentsStringMatrix
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public String[][] getStudentsStringMatrix() throws IOException, InvalidFormatException {
        students = TTGenerator.getStudents();         
        convertStudentsToTable();                
        return studentsStringMatrix;
    }
    
    /**
     * @return the studentsStringMatrix
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public String[][] getTeachersStringMatrix() throws IOException, InvalidFormatException {
        teachers = TTGenerator.getTeachers();         
        convertTeachersToTable();                
        return teachersStringMatrix;
    }    

    /**
     * @return the subjectssStringMatrix
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public String[][] getSubjectsStringMatrix() throws IOException, InvalidFormatException {
        subjects = TTGenerator.getSubjects();
        convertSubjectsToTable();        
        return subjectssStringMatrix;
    }
}
