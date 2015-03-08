package com.insider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import utils.Utils;

@Path("/signup")
public class SignupServlet {
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json")
	public String signup(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("firstName") String firstName,
			@FormParam("lastName") String lastName,
			@FormParam("email") String email,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		if (Utils.isUsernameExist(username) == true) {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"username: " + username + " already exists.\" }");
			return "{ \"status\": \"failure\", \"message\": \"username: " + username + " already exists.\" }";
		} else {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(password);
			long key = Utils.addNewUser(firstName, lastName, username, email, hashedPassword);
			return "{ status: 'success', message: 'User created successfully! id: " + key + "', id: " + key  + "}";
		}
	}
}
