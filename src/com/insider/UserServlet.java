package com.insider;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import utils.UserRes;
import utils.Utils;

import com.google.gson.Gson;

@Path("/user")
public class UserServlet {

	@POST @Path("/follow/{userFollowingId}")
	@Produces("application/json")
	public String followUser(@PathParam("userFollowingId") int userFollowingId,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(session != null){
			int user_id = (Integer)session.getAttribute("user_id");
			 Utils.addUserFollowing(user_id, userFollowingId);
			return "{ status: 'success', message: 'you are now following " + userFollowingId + "' }";
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}

	@GET @Path("/following")
	@Produces("application/json")
	public String getFollowing(@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(session != null){
			int user_id = (Integer)session.getAttribute("user_id");
			 List<String> res = Utils.getUserFollowing(user_id);
			return  res.toString();
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}

	@GET @Path("/followers")
	@Produces("application/json")
	public String getFollowers(@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		HttpSession session = request.getSession(false);
		if(session != null){
			int user_id = (Integer)session.getAttribute("user_id");
			 List<String> res = Utils.getUserFollowers(user_id);
			return  res.toString();
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}
	
	@GET @Path("/{user_id}")
	@Produces("application/json")
	public String getUser(@PathParam("user_id") int user_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		Gson gson = new Gson();
		HttpSession session = request.getSession(false);
		if(session != null){
			int my_user_id = (Integer)session.getAttribute("user_id");
			 UserRes res = Utils.getUser(my_user_id, user_id);
			return  gson.toJson(res);
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}
}