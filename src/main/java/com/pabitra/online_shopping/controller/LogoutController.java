package com.pabitra.online_shopping.controller;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logout")
public class LogoutController extends HttpServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie user = new Cookie("user", "");
		Cookie user_id = new Cookie("user_id", "");
		Cookie role = new Cookie("role", "");
		resp.addCookie(role);
		resp.addCookie(user_id);
		resp.addCookie(user);
		PrintWriter pw = resp.getWriter();
		resp.sendRedirect("login.jsp");
	}
}
