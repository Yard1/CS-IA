/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA.tableModels;

import TimetableGeneratorCS_IA.data.Subject;
import TimetableGeneratorCS_IA.data.Teacher;
import TimetableGeneratorCS_IA.TimetableGenerator;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author uczen
 */
public class SubjectsTableModel extends AbstractTableModel {

    private TimetableGenerator TTGenerator;
    private String[] columnNames;

    /**
     *
     * @param TTGenerator
     * @param columnNames
     */
    public SubjectsTableModel(TimetableGenerator TTGenerator, String[] columnNames) {
        this.TTGenerator = TTGenerator;
        this.columnNames = columnNames;
    }

    /**
     *
     * @return
     */
    @Override
    public int getRowCount() {
        return TTGenerator.getSubjects().size();
    }

    /**
     *
     * @return
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     *
     * @param i
     * @return
     */
    @Override
    public String getColumnName(int i) {
        return columnNames[i];
    }

    /**
     *
     * @param i
     * @return
     */
    @Override
    public Class<?> getColumnClass(int i) {
        return String.class;
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return true;
    }

    /**
     *
     * @param row
     * @param col
     * @return
     */
    @Override
    public Object getValueAt(int row, int col) {
        if (row > TTGenerator.getSubjects().size()) {
            return null;
        }
        Subject tempSubject = TTGenerator.getSubjects().get(row);
        switch (col) {
            case 0:
                return tempSubject.getSubjectName();
            case 1:
                return tempSubject.getTeacher();
            case 2:
                return tempSubject.getHours();
            default:
                return null;
        }
    }

    /**
     *
     * @param o
     * @param row
     * @param col
     */
    @Override
    public void setValueAt(Object o, int row, int col) {
        if (row > TTGenerator.getSubjects().size()) {
            return;
        }
        Subject tempSubject = TTGenerator.getSubjects().get(row);
        Subject checkSubject;
        if(o == null)
            return;
        String data = o.toString();
        System.out.println(col + "");
        switch (col) {
            case 0:
                if (data.equalsIgnoreCase("None")) {
                    JOptionPane.showMessageDialog(null, "Can't change subject's name! Subject's name cannot use a restricted keyword (None)", "Can't change subject's name!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                checkSubject = TTGenerator.getSubjects().stream().filter(x -> x.getSubjectName().equalsIgnoreCase(data)).findFirst().orElse(null);
                if (checkSubject == null) {
                    tempSubject.setSubjectName(data);
                    fireTableRowsUpdated(row, row);
                } else {
                    JOptionPane.showMessageDialog(null, "Can't change subject's name! A subject with this name already exists.", "Can't change subject's name!", JOptionPane.ERROR_MESSAGE);
                }
                return;
            case 1:
                if (data.equalsIgnoreCase("None")) {
                    tempSubject.setTeacher(null);
                    return;
                }
                Teacher tempTeacher = TTGenerator.getTeachers().stream().filter(x -> x.getNameAndSurname().equalsIgnoreCase(data)).findFirst().orElse(null);
                if (tempTeacher == null) {
                    return;
                }
                tempSubject.setTeacher(tempTeacher);
                fireTableRowsUpdated(row, row);
                return;
            case 2:
                tempSubject.setHours(Integer.parseInt(data));
                fireTableRowsUpdated(row, row);
                return;
            default:
                return;
        }
    }

}
