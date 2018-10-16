/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA.comboBoxModels;

import TimetableGeneratorCS_IA.TimetableGenerator;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author uczen
 */
public class StudentsTableComboBoxModel extends DefaultComboBoxModel {

    TimetableGenerator TTGenerator;

    public StudentsTableComboBoxModel(TimetableGenerator TTGenerator) {
        this.TTGenerator = TTGenerator;
    }

    @Override
    public int getSize() {
        return TTGenerator.subjects.size() + 1;
    }

    @Override
    public Object getElementAt(int i) {
        if (i < getSize()-1) {
            return TTGenerator.subjects.get(i); //To change body of generated methods, choose Tools | Templates.
        }
        return "None";
    }

}
