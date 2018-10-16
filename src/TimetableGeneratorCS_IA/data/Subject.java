/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA.data;

import java.util.ArrayList;

/**
 *
 * @author uczen
 */
public class Subject {

    private String subjectName;
    private Teacher teacher;
    private int hours = 0;
    private ArrayList<Student> students = new ArrayList<>();

    /**
     *
     */
    public Subject() {
        this.subjectName = "";
    }

    /**
     *
     * @param subjectName
     */
    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     *
     * @param subjectName
     * @param teacher
     */
    public Subject(String subjectName, Teacher teacher) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        if (teacher != null) {
            teacher.setSubject(this);
        }
    }

    /**
     *
     * @param subjectName
     * @param teacher
     * @param hours
     */
    public Subject(String subjectName, Teacher teacher, int hours) {
        this.subjectName = subjectName;
        this.teacher = teacher;
        if (teacher != null) {
            teacher.setSubject(this);
        }
        this.hours = hours;
    }

    /**
     *
     * @param subjectName
     * @param hours
     */
    public Subject(String subjectName, int hours) {
        this.subjectName = subjectName;
        this.hours = hours;
    }

    /**
     * @return the subjectName
     */
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * @param subjectName the subjectName to set
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    /**
     * @return the teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * @param teacher the teacher to set
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        if (teacher != null && teacher.getSubject() != this) {
            teacher.setSubject(this);
        }
    }

    /**
     * @return the hours
     */
    public int getHours() {
        return hours;
    }

    /**
     * @param hours the hours to set
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * @return the students
     */
//    public Student[] getStudents() {
//        if (students.size() < 1) {
//            return null;
//        }
//        return students.toArray(new Student[students.size()]);
//    }
    public ArrayList<Student> getStudents() {
        return students;
    }

    /**
     * @param s
     */
    public void addStudent(Student s) {
        if (!students.contains(s)) {
            students.add(s);
        }
        if (s != null && !s.containsSubject(this)) {
            s.addSubject(this);
        }
    }

    /**
     * @param s
     */
    public void removeStudent(Student s) {
        if (students.contains(s)) {
            students.remove(s);
        }
        if (s != null && s.containsSubject(this)) {
            s.removeSubject(this);
        }
    }

    /**
     * @param s
     * @return
     */
    public Boolean containsStudent(Student s) {
        return students.contains(s);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return subjectName;
    }

}
