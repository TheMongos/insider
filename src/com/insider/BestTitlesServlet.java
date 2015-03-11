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


@Path("/best")
public class BestTitlesServlet {
	@GET @Path("/{category_id}")
	@Produces("application/json")
	public String searchUser(@PathParam("category_id") int category_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			List<String> res = Utils.getBestTitles(category_id);
			return res.toString();
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}
	
	
	@GET @Path("/following/{category_id}")
	@Produces("application/json")
	public String searchItem(@PathParam("category_id") int category_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if(session != null){
			int user_id = (Integer) session.getAttribute("user_id");
			List<String> res = Utils.getBestTitlesForUser(category_id, user_id);
			return res.toString();
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}
	
//	@GET @Path("/{category_id}/{user_id}")
//	@Produces("application/json")
//	public String searchItem(@PathParam("category_id") int category_id,
//			@PathParam("user_id") int user_id,
//			@Context HttpServletRequest request,
//			@Context HttpServletResponse response) {
//		HttpSession session = request.getSession(false);
//		if(session != null){
//			List<String> res = Utils.getBestTitlesForUser(category_id, user_id);
//			return res.toString();
//		} else {
//			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
//			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
//		}
//	}
}