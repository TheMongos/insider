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

import com.google.gson.Gson;

@Path("/user")
public class UserServlet {

	@GET @Path("/following/{userFollowingId}")
	@Produces("application/json")
	public String getItem(@PathParam("userFollowingId") int userFollowingId,
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


}
