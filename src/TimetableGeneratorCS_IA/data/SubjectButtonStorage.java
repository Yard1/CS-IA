/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA.data;

import javax.swing.JButton;

/**
 *
 * @author uczen
 */
public class SubjectButtonStorage extends JButton {

    private Subject subject = null;
    private int chargesLeft = 0;

    /**
     *
     */
    public SubjectButtonStorage() {
        super();
    }

    /**
     *
     * @param name
     */
    public SubjectButtonStorage(String name) {
        super(name);
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
        setChargesLeft(subject.getHours());
    }

    /**
     * @return the chargesLeft
     */
    public int getChargesLeft() {
        return chargesLeft;
    }

    /**
     * @param chargesLeft the chargesLeft to set
     */
    public void setChargesLeft(int chargesLeft) {
        if (chargesLeft <= subject.getHours() && chargesLeft >= 0) {
            this.chargesLeft = chargesLeft;
        }
        this.setText(subject.getSubjectName() + " x" + chargesLeft);
    }

}
