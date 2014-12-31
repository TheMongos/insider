package com.insider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import utils.ItemRes;
import utils.Utils;

import com.google.gson.Gson;

@Path("/item")
public class ItemServlet {

	@GET @Path("/id/{item_id}")
	@Produces("application/json")
	public String getItem(@PathParam("item_id") int item_id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){
		Gson gson = new Gson();
		HttpSession session = request.getSession(false);
		if(session != null){
			ItemRes res = Utils.getItem(item_id, (Integer)session.getAttribute("user_id"));
			return gson.toJson(res);
		} else {
			response.setStatus(401);
			return "{ status: 'failure', message: 'user not logged in.' }";
		}
	}

	@POST @Path("/{category_id}/{title}/{year}/{description}/{other_data}")
	@Consumes("application/x-www-form-urlencoded")
	@Produces("text/plain")
	public String postItem(@PathParam("category_id") byte category_id,
			@PathParam("title") String title,
			@PathParam("year") String year,
			@PathParam("description") String description,
			@PathParam("other_data") String other_data){
		System.out.println("im here");
		Gson gson = new Gson();
		Long res = Utils.addItem(category_id, title, year, description, other_data);
		return res.toString();
	}
}
