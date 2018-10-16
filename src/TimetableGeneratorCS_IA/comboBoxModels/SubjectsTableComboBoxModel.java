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
public class SubjectsTableComboBoxModel extends DefaultComboBoxModel {

    TimetableGenerator TTGenerator;

    public SubjectsTableComboBoxModel(TimetableGenerator TTGenerator) {
        this.TTGenerator = TTGenerator;
    }

    @Override
    public int getSize() {
        return TTGenerator.teachers.size() + 1;
    }

    @Override
    public Object getElementAt(int i) {
        if (i < getSize()-1) {
            return TTGenerator.teachers.get(i); //To change body of generated methods, choose Tools | Templates.
        }
        return "None";
    }

}
