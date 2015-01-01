package com.insider;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import utils.ItemRes;
import utils.Utils;

import com.google.gson.Gson;

@Path("/")
public class RoutesServlet {
	@GET
	@Produces("text/plain")
	public String index() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
		//Gson gson = new Gson();
//		//redis check
//	
//		//RedisUtilsTemplate rd = (RedisUtilsTemplate)context.getBean("redisTest");
////		rd.addUserRankID(2, 2, 3, "some movie", 0);
////		rd.addUserRankID(2, 3, 4, "some movie2", 15);
//		
//		//String res = rd.getUserRankID(2, 3);
//
//		
////		rd.addTitleRank(3, 5);
////		Set<String> res = rd.getTitleRanks(3);
//		
//		
////		rd.addUserFollowing(2, 3);
////		rd.addUserFollowing(2, 5);
////		Set<String> followingSet = rd.getUserFollowing(2);
//		
////		res.retainAll(followingSet);
//		
//		
////		rd.addUserFollowers(3, 4);
////		rd.addUserFollowers(3, 2);
////		
////		List<String> res = rd.getUserFollowers(3);
//		
//		
//		
//		// item check
//
////				ItemJDBCTemplate itemJDBCTemplate = (ItemJDBCTemplate) context.getBean("itemJDBCTemplate");
////		
////				System.out.println("add item");
////				byte category_id = 1;
////				String title = "brother";
////				String year = "2002";
////				String description = "happy movie";
////				String other_data = ":) :) :)";
////				//Long res = itemJDBCTemplate.create(category_id, title, year, description,other_data);
////				int item_id = Utils.addItem(category_id, title, year, description, other_data).intValue();
//				ItemRes res = Utils.getItem(5, 0);
//				
//		//
//		//		itemJDBCTemplate.updateDescription(1, "new new description");
//		//		//itemJDBCTemplate.delete(2); 
//		//		
//		//		System.out.println("get items");
//		//
//		//		//Item item = itemJDBCTemplate.getItem(3); 
//		//		List <Item> items = itemJDBCTemplate.getItems();
//		//		 for(Item item: items){
//		//		System.out.println("getItem_id: " + item.getItem_id());
//		//		System.out.println("getTitle: " + item.getTitle());
//		//		System.out.println("getYear: " + item.getYear());
//		//		System.out.println("getDescription: " + item.getDescription());
//		//		System.out.println("getOther_data: " + item.getOther_data());
//		//		System.out.println("getCategory_id: " + item.getCategory_id());
//		//		System.out.println("getCreated_ts: " + item.getCreated_ts());
//		//		System.out.println("getUpdated_ts: " + item.getUpdated_ts()); 
//		//		 }
//
//
//
//
//		// category check
//
//		//CategoryJDBCTemplate categoryJDBCTemplate = (CategoryJDBCTemplate) context.getBean("categoryJDBCTemplate");
//		//System.out.println("add category");
//		//byte category_id = 2;
//		//String category_name = "test";
//
//		//categoryJDBCTemplate.create(category_id, category_name);
//		//categoryJDBCTemplate.delete(category_id);
//		//Category category = categoryJDBCTemplate.getCategory((byte)1);
//
//		//System.out.println("category_id: " + category.getCategory_id());
//		//System.out.println("category_name: " + category.getCategory_name());
//
//
//
//
//
//		// user check
//
////				UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
////		
////						System.out.println("add user");
////						String first_name = "David";
////						String last_name = "Tzoor";
////						String username = "TheMan";
////						String email = "david@gmail.com";
////						String password = "david1234";
////						
////					Long res =	userJDBCTemplate.create(first_name, last_name, username, email, password);
//
//		//		userJDBCTemplate.delete(1);
//
//		//		userJDBCTemplate.updateFirstName(2, "Elad");
//		//		userJDBCTemplate.updateLastName(2, "Birnboim");
//		//		userJDBCTemplate.updateEmail(2, "elad13579@walla.co.il");
//		//		userJDBCTemplate.updatePassword(2, "elad1234");
//		//		User user = userJDBCTemplate.getUser(2);
//		//
//		//		System.out.println("getUser_id: " + user.getUser_id());
//		//		System.out.println("getFirst_name: " + user.getFirst_name());
//		//		System.out.println("getLast_name: " + user.getLast_name());
//		//		System.out.println("getPassword: " + user.getPassword());
//		//		System.out.println("getEmail: " + user.getEmail());
//		//		System.out.println("getUsername: " + user.getUsername());
//		//		System.out.println("getCreated_ts: " + user.getCreated_ts());
//		//		System.out.println("getUpdated_ts: " + user.getUpdated_ts()); 
//
//
//
//		// review check
//
////		ReviewJDBCTemplate reviewJDBCTemplate = (ReviewJDBCTemplate) context.getBean("reviewJDBCTemplate");
////
////						int user_id = 3;
////						int item_id = 5;
////						String review_text ="yoyoyo";
////				
////						Long res = reviewJDBCTemplate.create(user_id, item_id, review_text);
//
//		//		Review review = reviewJDBCTemplate.getReview(1);
//
//
////		List<Review> reviews = reviewJDBCTemplate.getUserReviews(3);
////		for(Review review : reviews)
////		{
////			System.out.println();
////			System.out.println("getReview_id: " + review.getReview_id());
////			System.out.println("getItem_id: " + review.getItem_id());
////			System.out.println("getUser_id: " + review.getUser_id());
////			System.out.println("getRank: " + review.getRank());
////			System.out.println("getReview_text: " + review.getReview_text());
////			System.out.println("getCreated_ts: " + review.getCreated_ts());
////			System.out.println("getUpdated_ts: " + review.getUpdated_ts());
////			
////			System.out.println();
////		}
//
//		//		reviewJDBCTemplate.delete(2);
//
		return "hello insider";
//		return gson.toJson(res);
		//return res.toString() +  " " + followingSet.toString();
		//return res.toString();
	}
}
