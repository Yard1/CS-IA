/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TimetableGeneratorCS_IA.tableModels;

import TimetableGeneratorCS_IA.TimetableGenerator;
import TimetableGeneratorCS_IA.data.Subject;
import TimetableGeneratorCS_IA.data.Student;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author uczen
 */
public class StudentsTableModel extends AbstractTableModel {

    private TimetableGenerator TTGenerator;
    private String[] columnNames;

    /**
     *
     * @param TTGenerator
     * @param columnNames
     */
    public StudentsTableModel(TimetableGenerator TTGenerator, String[] columnNames) {
        this.TTGenerator = TTGenerator;
        this.columnNames = columnNames;
    }

    /**
     *
     * @return
     */
    @Override
    public int getRowCount() {
        return TTGenerator.getStudents().size();
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
        if (row > TTGenerator.getStudents().size()) {
            return null;
        }
        Student tempStudent = TTGenerator.getStudents().get(row);
        switch (col) {
            case 0:
                return tempStudent.getName();
            case 1:
                return tempStudent.getSurname();
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                if (col - 2 < tempStudent.getSubjectsList().size() && tempStudent.getSubjectsList().size() > 0) {
                    return tempStudent.getSubjectsList().get(col - 2);
                } else {
                    return null;
                }
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
        if (row > TTGenerator.getStudents().size()) {
            return;
        }
        Student tempStudent = TTGenerator.getStudents().get(row);
        if (o == null) {
            return;
        }
        String data = o.toString();
        switch (col) {
            case 0:
                tempStudent.setName(data);
                fireTableRowsUpdated(row, row);
                return;
            case 1:
                tempStudent.setSurname(data);
                fireTableRowsUpdated(row, row);
                return;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                if (data.equalsIgnoreCase("None")) {
                    Subject tempSubject = (Subject) getValueAt(row, col);
                    tempStudent.removeSubject(tempSubject);
                    fireTableRowsUpdated(row, row);
                    return;
                }
                Subject tempSubject = TTGenerator.getSubjects().stream().filter(x -> x.getSubjectName().equalsIgnoreCase(data)).findFirst().orElse(null);
                if (tempSubject == null) {
                    return;
                }
                if (!tempStudent.containsSubject(tempSubject)) {
                    tempStudent.removeSubjectAtIndex(col - 2);
                    tempStudent.addSubject(tempSubject);
                    fireTableRowsUpdated(row, row);
                } else {
                    JOptionPane.showMessageDialog(null, "Can't add subject!  Subject already assigned to student.", "Can't add subject!", JOptionPane.ERROR_MESSAGE);
                }

            default:
        }
    }

}
