package utils;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import db.mysql.ItemJDBCTemplate;
import db.redis.RedisUtilsTemplate;

public class Utils {
	
	private static ApplicationContext context;
	static {
		try {
			context = new ClassPathXmlApplicationContext("Beans.xml");
		} catch (BeansException e) {
			System.err.println("Severe Error: Can't load Beans.xml - fuck the system!");
		}
	}
	
	public static void addRank(int user_id, int rank, int item_id ){
		
	}

	//TODO
	public static List<SearchResult> getSearchResults(String query){
		//TODO
		return null;
	}
	
	public static Long addItem(byte category_id, String title, String year, String description, String other_data){
		ItemJDBCTemplate itemJDBCTemplate = (ItemJDBCTemplate) context.getBean("itemJDBCTemplate");
		Long item_id = itemJDBCTemplate.create(category_id, title, year, description, other_data);
		
		//TODO
		//add item to search list - redis
	
		return item_id;
	}
	
	public static ItemRes getItem(int item_id, int user_id){
		RedisUtilsTemplate redisUtilsTemplate = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		ItemJDBCTemplate itemJDBCTemplate = (ItemJDBCTemplate) context.getBean("itemJDBCTemplate");
		
		ItemRes res = new ItemRes();
		res.setItemDetails(itemJDBCTemplate.getItem(item_id));
		res.setItemRanks(redisUtilsTemplate.getItemRanks(item_id, user_id));
		return res;
	}
}
