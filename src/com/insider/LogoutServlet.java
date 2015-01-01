package com.insider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

@Path("/logout")
public class LogoutServlet {
	@POST
	@Produces("application/json")
	public String logout(@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			session.invalidate();
			return "{ status: 'success', message: ' User logout success'}";
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'User was not logged in' }";
		}
	}
}
