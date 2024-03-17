package com.inqwise.opinion.infrastructure.systemFramework;

public class EmailProviderOptions {
	private String username;
	private String password;
	private String isAuthRequired;
	private String host;
	private String port;
	private String auditEmail;

	private EmailProviderOptions(Builder builder) {
		this.username = builder.username;
		this.password = builder.password;
		this.isAuthRequired = builder.isAuthRequired;
		this.host = builder.host;
		this.port = builder.port;
		this.auditEmail = builder.auditEmail;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public String getIsAuthenticationRequired() {
		return isAuthRequired;
	}
	
	public String getSmtpServer() {
		return host;
	}
	
	public String getSmtpPort() {
		return port;
	}
	
	public String getAuditEmail() {
		return auditEmail;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Builder builderFrom(EmailProviderOptions emailProviderOptions) {
		return new Builder(emailProviderOptions);
	}

	public static final class Builder {
		private String username;
		private String password;
		private String isAuthRequired;
		private String host;
		private String port;
		private String auditEmail;

		private Builder() {
		}

		private Builder(EmailProviderOptions emailProviderOptions) {
			this.username = emailProviderOptions.username;
			this.password = emailProviderOptions.password;
			this.isAuthRequired = emailProviderOptions.isAuthRequired;
			this.host = emailProviderOptions.host;
			this.port = emailProviderOptions.port;
			this.auditEmail = emailProviderOptions.auditEmail;
		}

		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}

		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder withIsAuthRequired(String isAuthRequired) {
			this.isAuthRequired = isAuthRequired;
			return this;
		}

		public Builder withHost(String host) {
			this.host = host;
			return this;
		}

		public Builder withPort(String port) {
			this.port = port;
			return this;
		}

		public Builder withAuditEmail(String auditEmail) {
			this.auditEmail = auditEmail;
			return this;
		}

		public EmailProviderOptions build() {
			return new EmailProviderOptions(this);
		}
	}

}