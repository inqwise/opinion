package com.inqwise.opinion.infrastructure.systemFramework;

import org.junit.jupiter.api.Test;

import com.inqwise.opinion.infrastructure.common.EmailProviderException;
import com.inqwise.opinion.infrastructure.systemFramework.EmailProvider.ContentType;

public class EmailProviderTest {
	@Test
	void testSendEmail() throws EmailProviderException {
		EmailProvider emailProvider = new EmailProvider(EmailProviderOptions.builder()
				.withUsername("")
				.withPassword("")
				.withIsAuthRequired("true")
				.withHost("email-smtp.us-east-1.amazonaws.com")
				.withPort("465")
				.build());
		emailProvider.send("noreply@inqwise.com", "tera.g@inqwise.com", "testMessage", "something", ContentType.PlainText);
		emailProvider.close();
	}
}
