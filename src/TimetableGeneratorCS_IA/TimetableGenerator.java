/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA;

import TimetableGeneratorCS_IA.data.Subject;
import TimetableGeneratorCS_IA.data.Teacher;
import TimetableGeneratorCS_IA.data.Student;
import TimetableGeneratorCS_IA.data.SubjectGroup;
import static TimetableGeneratorCS_IA.HelperFunctions.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 *
 * @author uczen
 */
public class TimetableGenerator {

    /**
     *
     */
    public SubjectGroup[][] timetable;

    /**
     *
     */
    public ArrayList<Student> students = new ArrayList<>();

    /**
     *
     */
    public ArrayList<Subject> subjects = new ArrayList<>();

    /**
     *
     */
    public ArrayList<Teacher> teachers = new ArrayList<>();

    protected Subject nullSubject = null;

    /**
     *
     * @throws IOException
     * @throws InvalidFormatException
     * @throws NoSuchSubjectException
     */
    public TimetableGenerator() throws IOException, InvalidFormatException, NoSuchSubjectException {
        generateTimetable(true);
    }

    /**
     *
     * @param reloadFiles
     * @throws IOException
     * @throws InvalidFormatException
     * @throws NoSuchSubjectException
     */
    public void generateTimetable(Boolean reloadFiles) throws IOException, InvalidFormatException, NoSuchSubjectException {
        if (reloadFiles) {
            teachers.clear();
            getTeachersFromFile();

            subjects.clear();
            getSubjectsFromFile();

            students.clear();
            getStudentsFromFile();
        }
        timetable = getTimetableFromFile();
    }

    private SubjectGroup[][] getTimetableFromFile() throws IOException, InvalidFormatException {
        timetable = new SubjectGroup[11][5];
        for (SubjectGroup[] timetable1 : timetable) {
            for (int j = 0; j < timetable[0].length; j++) {
                timetable1[j] = new SubjectGroup();
            }
        }
        try (Workbook wb = WorkbookFactory.create(new File("Timetable.xlsx"))) {
            for (int i = 1; i < wb.getSheetAt(0).getLastRowNum() + 1 && i < 12; i++) {
                Row row = wb.getSheetAt(0).getRow(i);
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum() && j < 5; j++) {
                        if (row.getCell(j) != null) {
                            String[] tempSubjectNames = row.getCell(j).getRichStringCellValue().getString().split(", ");
                            for (String tempSubjectName : tempSubjectNames) {
                                if (tempSubjectName != null && !tempSubjectName.equalsIgnoreCase("null")) {
                                    Subject tempSubject = subjects.stream().filter(x -> x.getSubjectName().equalsIgnoreCase(tempSubjectName) && !x.getStudents().isEmpty()).findFirst().orElse(null);
                                    if (tempSubject != null && timetable[i - 1][j].checkSubject(tempSubject)) {
                                        timetable[i - 1][j].addSubject(tempSubject);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            ExcelTableGenerator.saveStringMatrixToExcel("Timetable.xlsx", MainFrame.timetableColumnNames, new String[11][5]);
        }
        return timetable;
    }

    /**
     *
     * @return @throws IOException
     * @throws InvalidFormatException
     */
    public SubjectGroup[][] getTimetable() throws IOException, InvalidFormatException {
        return timetable;
    }

    /**
     *
     * @return
     */
    public String[][] getStringTimetable() {
        String[][] tempAr = new String[11][5];
        for (int i = 0; i < timetable.length; i++) {
            for (int j = 0; j < timetable[0].length; j++) {
                if (timetable[i][j] == null) {
                    tempAr[i][j] = "";
                } else {
                    tempAr[i][j] = "";
                    if (timetable[i][j].getSubjects() != null) {
                        for (int k = 0; k < timetable[i][j].getSubjects().size(); k++) {
                            if (timetable[i][j].getSubjects().get(k) != null) {
                                String name = timetable[i][j].getSubjects().get(k).getSubjectName();
                                if (name.equalsIgnoreCase("null")) {
                                    name = "";
                                }
                                tempAr[i][j] += name;
                                if (k < timetable[i][j].getSubjects().size() - 1) {
                                    tempAr[i][j] += ", ";
                                }
                            }
                        }
                    }
                }
            }
        }
        return tempAr;
    }

    /**
     *
     * @param student
     */
    public void addStudent(Student student) {
        getStudents().add(student);
    }

    /**
     *
     * @return
     */
    public ArrayList<Subject> getTimetableSubjects() {
        ArrayList<Subject> subjectsAr = new ArrayList<>();
        for (Student s : students) {
            for (Subject subject : s.getSubjectsList()) {
                if (!subjectsAr.contains(subject)) {
                    subjectsAr.add(subject);
                }
            }
        }
        return subjectsAr;
    }

    private void getSubjectsFromFile() throws IOException, InvalidFormatException, NoSuchSubjectException {
        subjects.clear();
        try (Workbook wb = WorkbookFactory.create(new File("Subjects.xlsx"))) {
            Subject tempSubject = null;
            for (int i = 1; i < wb.getSheetAt(0).getLastRowNum() + 1; i++) {
                Row row = wb.getSheetAt(0).getRow(i);
                if (row.getCell(0).getRichStringCellValue().getString().isEmpty()) {
                    generateLog("generateSubjects", "Empty subject name at row" + i);
                } else if (row.getCell(0).getRichStringCellValue().getString().equalsIgnoreCase("None") || row.getCell(0).getRichStringCellValue().getString().equalsIgnoreCase("null")) {
                    generateLog("generateSubjects", "Subject has restricted name (None or null)" + i);
                } else {
                    if (row.getCell(1) != null && !row.getCell(1).getRichStringCellValue().getString().isEmpty()) {
                        String[] tempStrArray = row.getCell(1).getRichStringCellValue().getString().split(" ", 2);
                        //HelperFunctions.generateLog("getSubjectsFromFile", "Teacher " + tempStrArray[0] + " " + tempStrArray[1]);
                        if (tempStrArray.length >= 2) {
                            Teacher tempTeacher = teachers.stream().filter(x -> x.getName().equalsIgnoreCase(tempStrArray[0]) && x.getSurname().equalsIgnoreCase(tempStrArray[1])).findFirst().orElse(null);
                            if (tempTeacher != null) {
                                HelperFunctions.generateLog("generateSubjects", "Teacher " + tempTeacher.getNameAndSurname());
                                tempSubject = new Subject(row.getCell(0).getRichStringCellValue().getString(), tempTeacher, (int) row.getCell(2).getNumericCellValue());
                                HelperFunctions.generateLog("generateSubjects", tempSubject.toString());

                                if (tempSubject != null && subjects.stream().filter(x -> x.getSubjectName().equalsIgnoreCase(row.getCell(0).getRichStringCellValue().getString())).findFirst().orElse(null) == null) {
                                    subjects.add(tempSubject);
                                }
                                continue;
                            }
                        }
                    }
                    tempSubject = new Subject(row.getCell(0).getRichStringCellValue().getString(), (int) row.getCell(2).getNumericCellValue());
                    if (tempSubject != null) {
                        subjects.add(tempSubject);
                    }
                }
            }
        } catch (IOException e) {
            ExcelTableGenerator.saveStringMatrixToExcel("Subjects.xlsx", MainFrame.subjectsColumnNames, new String[1][3]);
        }
    }

    private void getStudentsFromFile() throws IOException, InvalidFormatException {
        try (Workbook wb = WorkbookFactory.create(new File("Students.xlsx"))) {
            for (int i = 1; i < wb.getSheetAt(0).getLastRowNum() + 1; i++) {
                Row row = wb.getSheetAt(0).getRow(i);
                try {
                    Student tempStudent = new Student(row.getCell(0).getRichStringCellValue().getString(), row.getCell(1).getRichStringCellValue().getString());
                    for (int j = 2; j < 8; j++) {
                        if (row.getCell(j) != null && !row.getCell(j).getRichStringCellValue().getString().isEmpty()) {
                            String name = row.getCell(j).getRichStringCellValue().getString();
                            tempStudent.addSubject(subjects.stream().filter(x -> x.getSubjectName().equalsIgnoreCase(name)).findFirst().orElse(null));
                        }
                    }
                    getStudents().add(tempStudent);
                } catch (NullPointerException e) {
                }
            }
        } catch (IOException e) {
            ExcelTableGenerator.saveStringMatrixToExcel("Students.xlsx", MainFrame.studentsColumnNames, new String[1][8]);
        }
    }

    private void getTeachersFromFile() throws IOException, InvalidFormatException {

        try (Workbook wb = WorkbookFactory.create(new File("Teachers.xlsx"))) {
            for (int i = 1; i < wb.getSheetAt(0).getLastRowNum() + 1; i++) {
                Row row = wb.getSheetAt(0).getRow(i);
                try {
                    Teacher tempTeacher = new Teacher(row.getCell(0).getRichStringCellValue().getString(), row.getCell(1).getRichStringCellValue().getString());
                    HelperFunctions.generateLog("getTeachersFromFile", "Teacher " + tempTeacher.getNameAndSurname());
                    if (!tempTeacher.getNameAndSurname().equalsIgnoreCase("None")) {
                        getTeachers().add(tempTeacher);
                    } else {
                        generateLog("getTeachersFromFile", "Teacher has restricted name (None)");
                    }
                } catch (NullPointerException e) {
                    generateLog("getTeachersFromFile", "Teacher is null, skipping");
                }
            }
        } catch (IOException e) {
            ExcelTableGenerator.saveStringMatrixToExcel("Teachers.xlsx", MainFrame.teachersColumnNames, new String[1][2]);
        }
    }

    /**
     *
     * @param index
     * @param newName
     */
    public void setStudentName(int index, String newName) {
        students.get(index).setName(newName);
    }

    /**
     *
     * @param index
     * @param newSurname
     */
    public void setStudentSurname(int index, String newSurname) {
        students.get(index).setSurname(newSurname);
    }

    /**
     * @return the students
     */
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * @return the teachers
     */
    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    /**
     * @return the subjects
     */
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    /**
     *
     * @param tempSubject
     */
    public void addSubject(Subject tempSubject) {
        subjects.add(tempSubject);
    }

    /**
     *
     * @param tempTeacher
     */
    public void addTeacher(Teacher tempTeacher) {
        teachers.add(tempTeacher);
    }

    /**
     *
     * @param idx
     * @return
     */
    public Boolean removeStudent(int idx) {
        Student tempStudent = students.get(idx);
        Boolean flag = false;
        for (Subject tempSubject : tempStudent.getSubjects()) {
            tempSubject.removeStudent(tempStudent);
            flag = true;
        }
        students.remove(idx);
        return flag;
    }

    /**
     *
     * @param idx
     * @return
     */
    public Boolean removeSubject(int idx) {
        Subject tempSubject = subjects.get(idx);
        Boolean flag = false;
        for (Student tempStudent : students) {
            if (HelperFunctions.containsSubjectInArray(tempStudent.getSubjects(), tempSubject)) {
                tempStudent.removeSubject(tempSubject);
                flag = true;
            }
        }
        subjects.remove(idx);
        return flag;
    }

    /**
     *
     * @param idx
     */
    public void removeTeacher(int idx) {
        Teacher tempTeacher = teachers.get(idx);
        for (Subject tempSubject : subjects) {
            if (tempSubject.getTeacher() == tempTeacher) {
                tempSubject.setTeacher(null);
            }
        }
        teachers.remove(idx);
    }

    /**
     *
     * @param idx
     * @return
     */
    public Boolean checkIfTeacherUsed(int idx) {
        Teacher tempTeacher = teachers.get(idx);
        Boolean b = tempTeacher.getSubject() != null;
        HelperFunctions.generateLog("checkIfTeacherUsed", b.toString());
        return b;
    }

    /**
     *
     * @param idx
     * @return
     */
    public Boolean checkIfStudentUsed(int idx) {
        Student tempStudent = students.get(idx);
        return tempStudent.getSubjects().length > 0;
    }

    /**
     *
     * @param idx
     * @return
     */
    public Boolean checkIfSubjectUsed(int idx) {
        Subject tempSubject = subjects.get(idx);
        return tempSubject.getStudents() != null || tempSubject.getStudents().size() > 0;
    }

    /**
     *
     */
    public static class NoSuchSubjectException extends Exception {

        /**
         *
         */
        public NoSuchSubjectException() {
        }
    }
}
