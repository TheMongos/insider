package db.redis;

import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;

public interface RedisDAO {

	/**
	 * This is the method to be used to initialize database resources ie.
	 * connection.
	 */
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate);
	
	/**
	 * This is the method to be used to list down all the ranks of the user.
	 */
	public List<String> getUserRanks(int user_id);
	
	/**
	 * This is the method to be used to set item_id the item name.
	 */
	public void setItemName(int item_id, String item_name);
	
	/**
	 * This is the method to be used to get the user rank for item.
	 */
	public String getUserRankID(int user_id, int item_id);

	/**
	 * This is the method to be used to set all the users that rank the item.
	 */
	public Set<String> getTitleRanks(int item_id);
	
	/**
	 * This is the method to be used to add user following list - a new follow (userFollow_id).
	 */
	public void addUserFollowing(int user_id, int userFollow_id);
	
	/**
	 * This is the method to be used to get the user following set.
	 */
	public Set<String> getUserFollowing(int user_id);
	
	/**
	 * This is the method to be used to add user followers list - a new follower (userFollow_id).
	 */
	public void addUserFollowers(int user_id, int userFollow_id);
	
	/**
	 * This is the method to be used to get the user followers list.
	 */
	public List<String> getUserFollowers(int user_id);
	/**
	 * This is the method to add or update user rank to an item - without a review.
	 */
	public void addRankToItem(int user_id, int item_id, int category_id, int rank);
	
	/**
	 * This is the method to add or update user rank to an item - with a review.
	 * if review id doesn't exit, review_id = 0.
	 */
	public void addRankToItem(int user_id, int item_id, int category_id, int rank, int review_id);
	
	
}
