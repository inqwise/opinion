package com.inqwise.opinion.facade.front;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.inqwise.opinion.infrastructure.common.IOperationResult;
import com.inqwise.opinion.infrastructure.systemFramework.ApplicationLog;
import com.inqwise.opinion.library.common.IProduct;
import com.inqwise.opinion.library.common.accounts.IAccount;
import com.inqwise.opinion.library.common.errorHandle.BaseOperationResult;
import com.inqwise.opinion.library.common.errorHandle.ErrorCode;
import com.inqwise.opinion.library.common.errorHandle.OperationResult;
import com.inqwise.opinion.library.common.users.IUser;
import com.inqwise.opinion.library.managers.ProductsManager;
import com.inqwise.opinion.library.managers.UsersManager;
import com.inqwise.opinion.common.IPostmasterContext;
import com.inqwise.opinion.http.HttpClientSession;

public abstract class Entry {

	//static ApplicationLog logger = ApplicationLog.getLogger(Entry.class);
	
	private IPostmasterContext context;

	public Entry(IPostmasterContext context) {
		setContext(context);
	}

	public void setContext(IPostmasterContext context) {
		this.context = context;
	}

	public IPostmasterContext getContext() {
		return context;
	}
	protected OperationResult<Long> getUserId() throws IOException {
		return context.getUserId();
	}

	protected boolean IsSignedIn() throws NullPointerException, ExecutionException{
		return context.IsSignedIn();
	}
	
	protected IOperationResult validateSignIn() throws NullPointerException, ExecutionException{
		return context.validateSignIn();
	}
	
	protected IOperationResult validateAccount(long accountId) throws IOException{
		return validateAccount(accountId);
	}
	
	@Deprecated
	protected IAccount getDefaultAccount() throws IOException, NullPointerException, ExecutionException {
		return context.getDefaultAccount();
	}
	
	protected List<IAccount> getLoggedInUserAllowedAccounts() throws IOException, NullPointerException, ExecutionException {
		return context.getLoggedInUserAllowedAccounts();
	}

	protected IProduct getCurrentProduct() {
		return context.getCurrentProduct();
	}

	protected OperationResult<IUser> getLoggedInUser() throws IOException, NullPointerException, ExecutionException {
		return context.getLoggedInUser();
	}
	
	protected OperationResult<IAccount> getAccount(){
		return context.getAccount();
	}
}
