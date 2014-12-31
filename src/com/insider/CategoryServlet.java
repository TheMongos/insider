package com.insider;

import java.util.List;

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

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import utils.Utils;
import db.mysql.User;

@Path("/category")
public class CategoryServlet {
	@POST 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json")
	public String signup(@FormParam("category_id") byte category_id,
			@FormParam("category_name") String category_name,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			Utils.addCategory(category_id, category_name);
			return "{ status: 'success', message: 'Category added' }";
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}
}
