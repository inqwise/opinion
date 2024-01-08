package com.inqwise.opinion.common.analizeResults;

import java.util.List;


public interface IControlsContainer<TControl> {
	void addControl(TControl control);
	List<TControl> getControls();
}