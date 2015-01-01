package com.insider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import utils.Utils;

import com.google.gson.Gson;

import db.mysql.Review;

@Path("/review")
public class ReviewServlet {

	@GET @Path("/{review_id}")
	@Produces("application/json")
	public String getReview(@PathParam("review_id") int review_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		HttpSession session = request.getSession(false);
		Gson gson = new Gson();
		if(session != null){
			Review res = Utils.getReview(review_id);
			return gson.toJson(res);
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}

	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/json") 
	public String postReview(@FormParam("category_id") int category_id,
			@FormParam("item_id") int item_id,
			@FormParam("rank") int rank,
			@FormParam("review_text") String review_text,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		Gson gson = new Gson();
		HttpSession session = request.getSession(false);
		if(session != null){
			System.out.println(category_id +" "+item_id +" "+rank +" "+review_text);
			int user_id = (Integer)session.getAttribute("user_id");
			Long res = Utils.addReview(user_id, category_id, item_id, rank, review_text);
			return "{ status: 'success', message: 'Review was created successfully! review id: " + res.toString() + "' }";
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}
	
	@DELETE @Path("/{review_id}")
	@Produces("application/json")
	public String deleteReview(@PathParam("review_id") int review_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		HttpSession session = request.getSession(false);
		Gson gson = new Gson();
		if(session != null){
			Review res = Utils.getReview(review_id);
			return gson.toJson(res);
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}
	
}
