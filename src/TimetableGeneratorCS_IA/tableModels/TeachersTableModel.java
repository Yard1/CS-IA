/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA.tableModels;

import TimetableGeneratorCS_IA.data.Teacher;
import TimetableGeneratorCS_IA.TimetableGenerator;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author uczen
 */
public class TeachersTableModel extends AbstractTableModel {

    private TimetableGenerator TTGenerator;
    private String[] columnNames;

    /**
     *
     * @param TTGenerator
     * @param columnNames
     */
    public TeachersTableModel(TimetableGenerator TTGenerator, String[] columnNames) {
        this.TTGenerator = TTGenerator;
        this.columnNames = columnNames;
    }

    /**
     *
     * @return
     */
    @Override
    public int getRowCount() {
        return TTGenerator.getTeachers().size();
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
        if (row > TTGenerator.getTeachers().size()) {
            return null;
        }
        Teacher tempTeacher = TTGenerator.getTeachers().get(row);
        switch (col) {
            case 0:
                return tempTeacher.getName();
            case 1:
                return tempTeacher.getSurname();
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
        if (row > TTGenerator.getTeachers().size()) {
            return;
        }
        Teacher tempTeacher = TTGenerator.getTeachers().get(row);
        String data = o.toString();

        switch (col) {
            case 0:
                if (!(data + " " + tempTeacher.getSurname()).equalsIgnoreCase("None")) {
                    tempTeacher.setName(data);
                } else {
                    JOptionPane.showMessageDialog(null, "Can't change teacher's name! Teacher's name cannot use a restricted keyword (None)", "Can't change teacher's name!", JOptionPane.ERROR_MESSAGE);
                }
                return;
            case 1:
                if (!(tempTeacher.getName() + " " + data).equalsIgnoreCase("None")) {
                    tempTeacher.setSurname(data);
                } else {
                    JOptionPane.showMessageDialog(null, "Can't change teacher's name! Teacher's name cannot use a restricted keyword (None)", "Can't change teacher's name!", JOptionPane.ERROR_MESSAGE);
                }           
                return;
            default:
                return;
        }
    }

}
