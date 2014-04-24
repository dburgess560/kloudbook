//package com.pippin;
//
////import org.apache.commons.validator.routines.EmailValidator;
//
////http://commons.apache.org/proper/commons-validator/ to get the jar file
//public class Email {
//	String local = " ";
//
//	String domain = " ";
//
//	public Email(String email) {
//		String[] emailParts;
//		if ((emailParts = email.split("@")).length == 2) {
//			this.local = emailParts[0];
//			this.domain = emailParts[1];
//		}
//	}
//
//	public String getLocal() {
//		return local;
//	}
//
//	public void setLocal(String local) {
//		this.local = local;
//	}
//
//	public String getDomain() {
//		return domain;
//	}
//
//	public void setDomain(String domain) {
//		this.domain = domain;
//	}
//
//	public String toString() {
//		return local + "@" + domain;
//	}
//
//	/*
//	 * public boolean isValid() { return
//	 * EmailValidator.getInstance().isValid(toString()); }
//	 */
//
//}
