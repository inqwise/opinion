package com.inqwise.opinion.payments.dao.interfaces;

import java.util.List;

public interface IBeginPaymentArgs extends IBasePaymentArgs {
	String getToken();
	List<Long> getChargeIds();
}
