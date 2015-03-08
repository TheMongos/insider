package com.insider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import utils.Utils;

@Path("/logout")
public class LogoutServlet {
	@POST
	@Produces("application/json")
	public String logout(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
			return "{ \"status\": \"success\", \"message\": \"User logout success\"}";
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}
}
