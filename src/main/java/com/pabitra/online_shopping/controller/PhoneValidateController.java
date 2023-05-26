package com.pabitra.online_shopping.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.pabitra.online_shopping.service.PhoneValidationService;
import com.pabitra.online_shopping.service.UsersService;
import com.pabitra.online_shopping.utility.Application;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/phoneValidate")
public class PhoneValidateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static String otp;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("action").equals("sendOTP")) {
			PhoneValidationService phoneService = Application.getPhoneValidationService();
			PrintWriter pw = resp.getWriter();
			
			UsersService userService = Application.getUsersService();
			if(userService.isDuplicateUser(req.getParameter("phone"))) {
				pw.println("duplicate");
				return;
			}
			
			phoneService.sentOTP(req.getParameter("phone"));
			pw.println("success");
		}else if(req.getParameter("action").equals("validateOTP")){
			PhoneValidationService phoneService = Application.getPhoneValidationService();
			boolean flag = phoneService.validateOTP(req.getParameter("otp"));
			PrintWriter pw = resp.getWriter();
			if(flag) {
				pw.println("success");
			}else {
				pw.println("failed");
			}
			
		}
	}
}
