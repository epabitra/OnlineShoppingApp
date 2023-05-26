package com.pabitra.online_shopping.service.impl;

import com.pabitra.online_shopping.controller.PhoneValidateController;
import com.pabitra.online_shopping.service.PhoneValidationService;
import com.pabitra.online_shopping.utility.MyOTP;

public class PhoneValidationServiceImpl implements PhoneValidationService{

	@Override
	public void sentOTP(String phone) {
		String otp = MyOTP.generateOTP();
		PhoneValidateController.otp = otp;
		MyOTP.sendOTP(otp, phone);
	}

	@Override
	public boolean validateOTP(String otp) {
		boolean flag = false;
		if(PhoneValidateController.otp.equals(otp)) flag=true;
		return flag;
	}

}
