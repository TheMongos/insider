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

import com.google.gson.Gson;

@Path("/rank")
public class RankServlet {

	@GET @Path("/following/{item_id}")
	@Produces("application/json")
	public String getItem(@PathParam("item_id") int item_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		Gson gson = new Gson();
		HttpSession session = request.getSession(false);
		if(session != null){
			int user_id = (Integer)session.getAttribute("user_id");
			List<String> res = Utils.getFollowingItemRanks(item_id, user_id);
			return gson.toJson(res);
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}

	
	@POST @Path("/{category_id}/{item_id}/{rank}")
	@Produces("application/json") 
	public String postRank(@PathParam("category_id") int category_id,
			@PathParam("item_id") int item_id,
			@PathParam("rank") byte rank,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		Gson gson = new Gson();
		HttpSession session = request.getSession(false);
		if(session != null){
			int user_id = (Integer)session.getAttribute("user_id");
			List<String> res = Utils.getFollowingItemRanks(item_id, user_id);
			return gson.toJson(res);
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}
}
