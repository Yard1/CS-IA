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
public class SubjectGroup {

    /**
     *
     */
    protected ArrayList<Subject> subjects;

    /**
     *
     */
    public SubjectGroup() {
        subjects = new ArrayList<>();
    }

    /**
     *
     * @param subject
     */
    public SubjectGroup(Subject subject) {
        subjects = new ArrayList<>();
        subjects.add(subject);
    }

    /**
     *
     * @param subject
     * @param subject2
     */
    public SubjectGroup(Subject subject, Subject subject2) {
        subjects = new ArrayList<>();
        subjects.add(subject);
        subjects.add(subject2);
    }

    /**
     *
     * @return
     */
    public ArrayList<Subject> getSubjects() {
        return subjects;
    }

    /**
     *
     * @param newSubject
     * @return
     */
    public Boolean checkSubject(Subject newSubject) {
        System.out.println("Checking subject");
        if (newSubject == null) {
            System.out.println("Subject is null!");
            return true;
        }
        for (Subject checkedSubject : subjects) {
            if (checkedSubject == null) {
                continue;
            }
            if (checkedSubject.equals(newSubject)) {
                System.out.println("Subject is already in SubjectGroup!");
                return false;
            }
            if (checkedSubject.getTeacher() != null && newSubject.getTeacher() != null && checkedSubject.getTeacher() == newSubject.getTeacher()) {
                System.out.println("Subject's teacher already has another Subject in SubjectGroup!");
                return false;
            }
            if (newSubject.getStudents() != null) {
                for (Student student : newSubject.getStudents()) {
                    if (student != null && checkedSubject.containsStudent(student) && subjects.size() > 1) {
                        System.out.println("Student already has other subject!");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param newSubject
     */
    public void addSubject(Subject newSubject) {
        subjects.add(newSubject);
    }

    /**
     *
     * @param idx
     */
    public void removeSubjectAtIndex(int idx) {
        if (subjects.size() > idx && idx > -1) {
            subjects.remove(idx);
            subjects.add(idx, null);
        }
    }

    /**
     *
     * @param idx
     */
    public void addSubjectAtIndex(Subject newSubject, int idx) {
        if(subjects.isEmpty()){
            subjects.add(newSubject);
            return;
        } 
        if (idx < subjects.size() && idx > -1) {
            subjects.remove(idx);
            subjects.add(idx, newSubject);
        }
    }

    /**
     *
     * @param subject
     */
    public void removeSubject(Subject subject) {
        int idx = subjects.indexOf(subject);
        subjects.remove(subject);
        subjects.add(idx, null);
    }

    /**
     *
     * @param idx
     * @return
     */
    public Subject getSubjectAtIndex(int idx) {
        if (idx < subjects.size()) {
            return subjects.get(idx);
        } else {
            return null;
        }
    }
}
