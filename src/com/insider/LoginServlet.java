package com.insider;

import java.util.Map;
import java.util.Map.Entry;

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

import org.json.JSONObject;

import com.google.gson.JsonObject;

import utils.Utils;
import db.mysql.User;

@Path("/login")
public class LoginServlet {
	//before using angular - diffrent @Consumes
	/*@POST 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json")
	public String login(@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		User user = Utils.authenticateUser(username, password);
		if ( user != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("username", username);
			session.setAttribute("user_id", user.getUser_id());
			session.setAttribute("first_name", user.getFirst_name());
			session.setAttribute("last_name", user.getLast_name());
			session.setMaxInactiveInterval(260000);
			return "{ status: 'success', message: 'User login! id: " + user.getUser_id() + "', id: " + user.getUser_id()  + "}";
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'username: " + username + " doesn't exist or password incorrect.' }";
		}
	}*/
	
	@POST 
	@Consumes("application/json")
	@Produces("application/json")
	public String login2(String loginObj, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		JSONObject jsonObj = new JSONObject(loginObj);
		String username = jsonObj.getString("username");
		String password = jsonObj.getString("password");
		User user = Utils.authenticateUser(username, password);
		if ( user != null) {
			HttpSession session = request.getSession(true);
			session.setAttribute("username", username);
			session.setAttribute("user_id", user.getUser_id());
			session.setAttribute("first_name", user.getFirst_name());
			session.setAttribute("last_name", user.getLast_name());
			session.setMaxInactiveInterval(260000);
			return "{ \"status\": \"success\", \"message\": \"User login! id: " + user.getUser_id() + "\", \"id\": " + user.getUser_id()  + "}";
		} else {
			response.setStatus(401);
			return "{ \"status\": \"failure\", \"message\": \"username doesn't exist or password incorrect.\" }";
		}

	}
}