package com.insider;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import utils.Utils;

@Path("/rank")
public class RankServlet {

	@GET @Path("/following/{item_id}")
	@Produces("application/json")
	public String getFollowingRanks(@PathParam("item_id") int item_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(session != null){
			int user_id = (Integer)session.getAttribute("user_id");
			List<String> res = Utils.getFollowingItemRanks(item_id, user_id);
			//already in json format!
			return res.toString();
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}

	
	@POST @Path("/{category_id}/{item_id}/{rank}")
	@Produces("application/json") 
	public String postRank(@PathParam("category_id") int category_id,
			@PathParam("item_id") int item_id,
			@PathParam("rank") int rank,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(session != null){
			int user_id = (Integer)session.getAttribute("user_id");
			Utils.addRankToItem(user_id, item_id, category_id, rank);
			return "{ \"status\": \"success\", \"message\": \"Rank added\" }";
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}
	
	@GET @Path("/{user_id}")
	@Produces("application/json")
	public String getUserRanks(@PathParam("user_id") int user_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(session != null){
			List<String> res = Utils.getUserRanks(user_id);
			//already in json format!
			return res.toString();
		} else {
			Utils.sendError(response ,401, "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }");
			return "{ \"status\": \"failure\", \"message\": \"user not logged in.\" }";
		}
	}
	
}
