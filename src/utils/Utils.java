package utils;

import java.util.List;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import db.mysql.CategoryJDBCTemplate;
import db.mysql.ItemJDBCTemplate;
import db.mysql.User;
import db.mysql.UserJDBCTemplate;
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

	//TODO
	public static List<SearchResult> getSearchResults(String query){
		//TODO
		return null;
	}
	
	public static Long addItem(byte category_id, String title, String year, String description, String other_data){
		ItemJDBCTemplate itemJDBCTemplate = (ItemJDBCTemplate) context.getBean("itemJDBCTemplate");
		RedisUtilsTemplate redisUtilsTemplate = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		Long item_id = itemJDBCTemplate.create(category_id, title, year, description, other_data);
		redisUtilsTemplate.addItemName(item_id.intValue(), title);
		
		
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
	
	public static boolean isUsernameExist(String username) {
		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
		return userJDBCTemplate.isUserExist(username);
		//return (count == 0) ? false : true;
	}
	
	public static long addNewUser(String firstName, String lastName, String username, String email, String password) {
		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
		RedisUtilsTemplate rd = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		Long user_id = userJDBCTemplate.create(firstName, lastName, username, email, password);
		rd.addUsername(user_id.intValue(), username);
		return user_id;
	}
	
	public static User authenticateUser(String username, String password){
		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
		User user = userJDBCTemplate.getUserByUsername(username);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		if (passwordEncoder.matches(password, user.getPassword())){
			return user;
		} else {
			return null;
		}
	}
	
	public static List<String> getFollowingItemRanks(int item_id, int user_id){
		RedisUtilsTemplate rd = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		return rd.getFollowingItemRanks(item_id, user_id);
	}
	
	public static void addUserFollowing(int user_id, int userFollow_id){
		RedisUtilsTemplate rd = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		rd.addUserFollowing(user_id, userFollow_id);
		rd.addUserFollowers(userFollow_id, user_id);
		rd.setUsersRankHistToNewFollower(userFollow_id, user_id);
	}

	public static void addCategory(byte category_id, String category_name) {
		CategoryJDBCTemplate categoryJDBCTemplate = (CategoryJDBCTemplate) context.getBean("categoryJDBCTemplate");
		categoryJDBCTemplate.create(category_id, category_name);
	}
	
	public static void addRankToItem(int user_id, int item_id, int category_id, int rank){
		RedisUtilsTemplate rd = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		rd.addRankToItem(user_id, item_id, category_id, rank);
	}
	
	public static List<String> getUserFollowing(int user_id){
		RedisUtilsTemplate rd = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		return rd.getUserFollowing(user_id);
	}
	
	public static List<String> getUserFollowers(int user_id){
		RedisUtilsTemplate rd = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		return rd.getUserFollowers(user_id);
	}
	
	public static List<String> getUserRanks(int user_id){
		RedisUtilsTemplate rd = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		return rd.getUserRanks(user_id);
	}
	
	
	public static UserRes getUser(int my_user_id, int user_id){
		RedisUtilsTemplate redisUtilsTemplate = (RedisUtilsTemplate)context.getBean("redisUtilsTemplate");
		UserJDBCTemplate userJDBCTemplate = (UserJDBCTemplate) context.getBean("userJDBCTemplate");
		
		UserRes res = new UserRes();
		res.setUser(userJDBCTemplate.getUser(user_id));
		res.setUserDetails(redisUtilsTemplate.getUserDetails(user_id, my_user_id));
		System.out.println(res.getUserDetails().getIsFollowing());
		return res;
	}
	
}
