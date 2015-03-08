package com.insider;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import utils.Utils;


@Path("/search")
public class SearchServlet {
	@GET @Path("/user/{query}")
	@Produces("application/json")
	public String searchUser(@PathParam("query") String query,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			List<String> res = Utils.searchUsers(query);
			return res.toString();
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}
	
	
	@GET @Path("/item/{query}")
	@Produces("application/json")
	public String searchItem(@PathParam("query") String query,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			List<String> res = Utils.searchItems(query);
			return res.toString();
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}
}