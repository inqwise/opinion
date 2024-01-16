package com.inqwise.opinion.library.common;

public class AccountOperationModel {

	private AccountOperationModel(Builder builder) {
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(AccountOperationModel accountOperationModel) {
		return new Builder(accountOperationModel);
	}

	public static final class Builder {
		private Builder() {
		}

		private Builder(AccountOperationModel accountOperationModel) {
		}

		public AccountOperationModel build() {
			return new AccountOperationModel(this);
		}
	}

}
