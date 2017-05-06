package com.epam.apartment.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.epam.apartment.model.User;

public class UserNotAuthorisatedInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (user == null) {
			response.sendRedirect(request.getServletContext().getContextPath() + "/");
			return false;
		}
		return true;
	}
}
