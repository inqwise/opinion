package com.inqwise.opinion.opinion.common.analizeResults;

import java.util.List;


public interface IOptionsContainer<TOption> {
	void addOption(TOption option);
	List<TOption> getOptions();
}