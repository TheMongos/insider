package com.insider;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import db.mysql.Category;
import db.mysql.CategoryJDBCTemplate;
import db.mysql.Item;
import db.mysql.ItemJDBCTemplate;
import db.mysql.Review;
import db.mysql.ReviewJDBCTemplate;
import db.mysql.User;
import db.mysql.UserJDBCTemplate;

@Path("/")
public class RoutesServlet {
	@GET
	@Produces("text/plain")
	public String index() {
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		// item check

		//		ItemJDBCTemplate itemJDBCTemplate = (ItemJDBCTemplate) context.getBean("itemJDBCTemplate");
		//
		//		System.out.println("add item");
		//		byte category_id = 1;
		//		String title = "Aladin";
		//		String year = "1990";
		//		String description = "allladin";
		//		String other_data = "aladin in other";
		//		itemJDBCTemplate.create(category_id, title, year, description,other_data);
		//
		//		itemJDBCTemplate.updateDescription(1, "new new description");
		//		//itemJDBCTemplate.delete(2); 
		//		
		//		System.out.println("get items");
		//
		//		//Item item = itemJDBCTemplate.getItem(3); 
		//		List <Item> items = itemJDBCTemplate.getItems();
		//		 for(Item item: items){
		//		System.out.println("getItem_id: " + item.getItem_id());
		//		System.out.println("getTitle: " + item.getTitle());
		//		System.out.println("getYear: " + item.getYear());
		//		System.out.println("getDescription: " + item.getDescription());
		//		System.out.println("getOther_data: " + item.getOther_data());
		//		System.out.println("getCategory_id: " + item.getCategory_id());
		//		System.out.println("getCreated_ts: " + item.getCreated_ts());
		//		System.out.println("getUpdated_ts: " + item.getUpdated_ts()); 
		//		 }




		// category check

		//CategoryJDBCTemplate categoryJDBCTemplate = (CategoryJDBCTemplate) context.getBean("categoryJDBCTemplate");
		//System.out.println("add category");
		//byte category_id = 2;
		//String category_name = "test";

		//categoryJDBCTemplate.create(category_id, category_name);
		//categoryJDBCTemplate.delete(category_id);
		//Category category = categoryJDBCTemplate.getCategory((byte)1);

		//System.out.println("category_id: " + category.getCategory_id());
		//System.out.println("category_name: " + category.getCategory_name());





		// user check

		//		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
		//
		//				System.out.println("add user");
		//				String first_name = "David";
		//				String last_name = "Tzoor";
		//				String username = "TheMan";
		//				String email = "david@gmail.com";
		//				String password = "david1234";
		//				
		//				userJDBCTemplate.create(first_name, last_name, username, email, password);

		//		userJDBCTemplate.delete(1);

		//		userJDBCTemplate.updateFirstName(2, "Elad");
		//		userJDBCTemplate.updateLastName(2, "Birnboim");
		//		userJDBCTemplate.updateEmail(2, "elad13579@walla.co.il");
		//		userJDBCTemplate.updatePassword(2, "elad1234");
		//		User user = userJDBCTemplate.getUser(2);
		//
		//		System.out.println("getUser_id: " + user.getUser_id());
		//		System.out.println("getFirst_name: " + user.getFirst_name());
		//		System.out.println("getLast_name: " + user.getLast_name());
		//		System.out.println("getPassword: " + user.getPassword());
		//		System.out.println("getEmail: " + user.getEmail());
		//		System.out.println("getUsername: " + user.getUsername());
		//		System.out.println("getCreated_ts: " + user.getCreated_ts());
		//		System.out.println("getUpdated_ts: " + user.getUpdated_ts()); 



		// review check

//		ReviewJDBCTemplate reviewJDBCTemplate = (ReviewJDBCTemplate) context.getBean("reviewJDBCTemplate");

		//				int user_id = 3;
		//				int item_id = 1;
		//				byte rank = 3;
		//				String review_text ="ooooooooo";
		//		
		//				reviewJDBCTemplate.create(user_id, item_id, rank, review_text);

		//		Review review = reviewJDBCTemplate.getReview(1);


//		List<Review> reviews = reviewJDBCTemplate.getUserReviews(3);
//		for(Review review : reviews)
//		{
//			System.out.println();
//			System.out.println("getReview_id: " + review.getReview_id());
//			System.out.println("getItem_id: " + review.getItem_id());
//			System.out.println("getUser_id: " + review.getUser_id());
//			System.out.println("getRank: " + review.getRank());
//			System.out.println("getReview_text: " + review.getReview_text());
//			System.out.println("getCreated_ts: " + review.getCreated_ts());
//			System.out.println("getUpdated_ts: " + review.getUpdated_ts());
//			
//			System.out.println();
//		}

		//		reviewJDBCTemplate.delete(2);


		return "insider app";
	}
}
