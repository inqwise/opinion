package com.inqwise.opinion.library.common.users;

import com.inqwise.opinion.library.common.ISession;
import com.inqwise.opinion.library.common.pay.ICharge;

public interface ICompleteLoginResult {

	ISession getSession();

	String getReturnUrl();

	ICharge getCharge();
	
	IUser getUser();
	
	int getCountLogins();
}
