package com.inqwise.opinion.infrastructure.dao;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8822748991750577059L;

	String[] columnNames;

	List<Object[]> data;

	public TableModel(String... columnNames){
		this.columnNames = columnNames;
		data = new ArrayList<Object[]>();
	} 
	
	public void addRow(Object... values){
		data.add(java.util.Arrays.copyOf(values, columnNames.length));
	}
	
	public int getColumnCount(){
		return columnNames.length;
	}
	
	public int getRowCount(){
		return data.size();
	}
	
	public String getColumnName(int col){
		return columnNames[col];
	}
	
	public Object getValueAt(int row, int col){
		return data.get(row)[col];
	}
	
	public boolean isCellEditable(){
		return false;
	}
}
