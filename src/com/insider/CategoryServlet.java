package com.insider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import utils.Utils;

@Path("/category")
public class CategoryServlet {
	@POST 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json")
	public String addCategory(@FormParam("category_id") byte category_id,
			@FormParam("category_name") String category_name,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			Utils.addCategory(category_id, category_name);
			return "{ status: 'success', message: 'Category added' }";
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}
}
