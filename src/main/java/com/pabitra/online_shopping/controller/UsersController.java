package com.pabitra.online_shopping.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Predicate;

import com.pabitra.online_shopping.entity.UsersEntity;
import com.pabitra.online_shopping.service.UsersService;
import com.pabitra.online_shopping.utility.Application;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/user")
public class UsersController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Predicate Functional Interface Calling by using Lambda Expression
		Predicate<String> predicate = s->s.equals("register");
		/*
		 * In the above for practice purpose i used Predicate so
		 * by using if block i execute the resister services and
		 * by using else block i execute the login service logics.
		 * 
		 */
		
		// Register Service Calling 
		if(predicate.test(req.getParameter("hb"))) {
			UsersEntity entity = new UsersEntity();
			entity.setFirstName(req.getParameter("firstName"));
			entity.setLastName(req.getParameter("lastName"));
			entity.setEmail(req.getParameter("email"));
			entity.setPhone(req.getParameter("phone"));
			entity.setPassword(req.getParameter("password"));
			String rePassword = req.getParameter("rePassword");

			// Registration Validate
			if(isBlank(entity, rePassword)) {
				PrintWriter pw = resp.getWriter();
				pw.println("blank");
				return;
			}else if(!validatePassword(entity.getPassword(), rePassword)) {
				PrintWriter pw = resp.getWriter();
				pw.println("mismatch");
				return;
			}
			
			UsersService usersService = Application.getUsersService();
			int result = usersService.register(entity);
			if(result == 1) {
				PrintWriter pw = resp.getWriter();
				pw.println("success");
			}else if(result == -2) {
				PrintWriter pw = resp.getWriter();
				pw.println("duplicate");
			}
		}else{
		// Login Service Calling
			String phone = req.getParameter("phone");
			String password = req.getParameter("password");
			
			UsersService usersService = Application.getUsersService();
			UsersEntity entity = usersService.login(phone, password);
			
			String userType = null;
			
			try {
				if(entity.getRole_id() == 1) {
					userType = "admin";
				}else if(entity.getRole_id() == 2){
					userType = "user";
				}
			}catch(NullPointerException e) {
				userType = "invalid";
			}
			
			if(userType.equals("admin")) {
				Cookie user = new Cookie("user", phone);
				Cookie role = new Cookie("role", "admin");
				Cookie userId = new Cookie("user_id", String.valueOf(entity.getUserId()));
				resp.addCookie(userId);
				resp.addCookie(user);
				resp.addCookie(role);
				resp.sendRedirect("view-product.jsp");
			}else if(userType.equals("user")) {
				Cookie user = new Cookie("user", phone);
				Cookie role = new Cookie("role", "user");
				Cookie userId = new Cookie("user_id", String.valueOf(entity.getUserId()));
				resp.addCookie(userId);
				resp.addCookie(user);
				resp.addCookie(role);
				resp.sendRedirect("view-product.jsp");
			}else if(userType.equals("invalid")){
				req.setAttribute("phone", phone);
				req.setAttribute("password", password);
				req.setAttribute("errorMsg", "Invalid Username or Password !!!");
				
				req.getRequestDispatcher("login.jsp?errorMsg=Invalid Username or Password !!!").forward(req, resp);
			}
		}
	}
	
	// Blank Validation
	public boolean isBlank(UsersEntity entity, String rePassword) {
		if(entity.getFirstName().equals("")) return true;
		if(entity.getLastName().equals("")) return true;
		if(entity.getEmail().equals("")) return true;
		if(entity.getPhone().equals("")) return true;
		if(entity.getPassword().equals("")) return true;
		if(rePassword.equals("")) return true;
		return false;
	}
	
	// Password Validation
	public boolean validatePassword(String password, String rePassword) {
		if(password.equals(rePassword)) {
			return true;
		}else {
			return false;
		}
	}
}
