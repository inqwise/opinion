package com.inqwise.opinion.common.sheet;

public class StartSheetIndexData {

	public StartSheetIndexData(int index, int numerator) {
		setIndex(index);
		setNumerator(numerator);
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}

	public int getNumerator() {
		return numerator;
	}

	public void setNumerator(int numerator) {
		this.numerator = numerator;		
	}

	private int index;
	private int numerator;

}
