package com.insider;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import utils.ItemRes;
import utils.Utils;

import com.google.gson.Gson;

@Path("/item")
public class ItemServlet {

	@GET @Path("/id/{item_id}")
	@Produces("application/json")
	public String getItem(@PathParam("item_id") int item_id){
		Gson gson = new Gson();
		ItemRes res = Utils.getItem(item_id, 0);
		return gson.toJson(res);
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
