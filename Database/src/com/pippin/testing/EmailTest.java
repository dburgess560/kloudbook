package com.pippin.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.validator.EmailValidator;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmailTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Test
	public void checkValid() {
		String[] validEmails = { "test@yahoo.com", "test-100@yahoo.com",
				"test.100@yahoo.com", "test111@test.com", "test-100@test.net",
				"test.100@test.com.au", "test@1.com", "test@gmail.com.com",
				"test+100@gmail.com", "test-100@yahoo-test.com" };
		for (String email : validEmails) {
			// assertTrue(new Email(email).isValid());
			assertTrue(EmailValidator.getInstance().isValid(email));
		}

	}

	@SuppressWarnings("deprecation")
	@Test
	public void checkInvalid() {
		String[] invalidEmails = { "test", "test@.com.my", "test123@gmail.a",
				"test123@.com", "test123@.com.com",
				"test()*@gmail.com", "test@%*.com",
				 "test@test@gmail.com", "test@@@gmail.com.1a" };
		for (String email : invalidEmails) {
			assertFalse(EmailValidator.getInstance().isValid(email));
		}
	}

}
