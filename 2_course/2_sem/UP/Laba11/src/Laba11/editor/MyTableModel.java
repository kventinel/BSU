package Laba11.editor;

import Laba11.data.AbstractPass;
import Laba11.data.ExamPass;
import Laba11.data.Student;
import Laba11.data.TestPass;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.List;

public class MyTableModel implements TableModel {
    private List<AbstractPass> list;
    private JTable table;

    public MyTableModel(Student student,JTable table) {
        this.table=table;
        list=student.getList();
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(columnIndex==3) return Boolean.class;
        if(columnIndex==2) return Integer.class;
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex == 3) {
            return list.get(rowIndex) instanceof TestPass;
        }
        return columnIndex != 2 || list.get(rowIndex) instanceof ExamPass;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AbstractPass pass=list.get(rowIndex);
        if(columnIndex==0) return pass.getName();
        if(columnIndex==1) return pass.getExaminator();
        if(columnIndex==3) return pass.isPassed();
        if(columnIndex==2){
            if(pass instanceof ExamPass){
                return ((ExamPass)pass).getMark();
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        AbstractPass pass=list.get(rowIndex);
                if(columnIndex==0) pass.setName((String)aValue);
        else    if(columnIndex==1) pass.setExaminator((String)aValue);
        else    if(columnIndex==2) ((ExamPass)pass).setMark((Integer)aValue);
        else    if(columnIndex==3) ((TestPass)pass).setPassed((Boolean)aValue);


        table.repaint();
    }

    @Override
    public void addTableModelListener(TableModelListener l) {

    }

    @Override
    public void removeTableModelListener(TableModelListener l) {

    }

}
