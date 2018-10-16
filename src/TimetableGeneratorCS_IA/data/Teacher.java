/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA.data;

/**
 *
 * @author uczen
 */
public class Teacher extends Person {

    private Subject subject;

    /**
     *
     */
    public Teacher() {
    }

    /**
     *
     * @param name
     * @param surname
     */
    public Teacher(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    /**
     * @return the subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
        if (subject != null && subject.getTeacher() != this) {
            subject.setTeacher(this);
        }
    }

}
