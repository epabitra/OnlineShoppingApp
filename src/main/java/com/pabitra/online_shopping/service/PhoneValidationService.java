package com.pabitra.online_shopping.service;

public interface PhoneValidationService {
	public void sentOTP(String phone);
	public boolean validateOTP(String otp);
}
