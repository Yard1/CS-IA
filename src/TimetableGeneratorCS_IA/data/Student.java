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
public class Student extends Person {

    /**
     *
     */
    protected ArrayList<Subject> subjects;

    /**
     *
     */
    public Student() {
        this.subjects = new ArrayList<>();
    }

    /**
     *
     * @param name
     * @param surname
     * @param className
     */
    public Student(String name, String surname) {
        this.subjects = new ArrayList<>();
        this.name = name;
        this.surname = surname;
    }

    /**
     *
     * @return
     */
    public Subject[] getSubjects() {
        return subjects.toArray(new Subject[subjects.size()]);
    }

    /**
     *
     * @return
     */
    public ArrayList<Subject> getSubjectsList() {
        return subjects;
    }

    /**
     *
     * @param newSubject
     */
    public void addSubject(Subject newSubject) {
        if (!subjects.contains(newSubject)) {
            subjects.add(newSubject);
        }
        if (!newSubject.containsStudent(this)) {
            newSubject.addStudent(this);
        }
    }

    /**
     *
     * @param subject
     */
    public void removeSubject(Subject subject) {
        if (subjects.contains(subject)) {
            subjects.remove(subject);
        }
        if (subject != null && subject.containsStudent(this)) {
            subject.removeStudent(this);
        }
    }

    /**
     *
     * @param subject
     */
    public void removeSubjectAtIndex(int idx) {
        if (idx < 0) {
            return;
        }
        Subject subject = null;
        if (subjects.size() > idx) {
            subject = subjects.get(idx);
            subjects.remove(idx);
        }
        if (subject != null && subject.containsStudent(this)) {
            subject.removeStudent(this);
        }
    }

    /**
     *
     */
    public void clearSubjects() {
        for (Subject s : subjects) {
            s.removeStudent(this);
        }
        subjects.clear();
    }

    /**
     *
     * @return
     */
    public String toString() {
        String returnString = name + " " + surname + ", ";
        for (Subject tempSubject : subjects) {
            returnString += tempSubject.getSubjectName() + ", ";
        }
        return returnString;
    }

    /**
     * @param s
     * @return
     */
    public Boolean containsSubject(Subject s) {
        return subjects.contains(s);
    }
}
