package db.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.Box.Filler;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

public class RedisUtilsTemplate implements RedisDAO {
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	public List<String> getUserRanks(int user_id){
		List<String> res = new ArrayList<String>();
		List<String> itemsList = getUserRanksItemID(user_id);
		for(String item_id: itemsList){
			res.add(getUserRankID(user_id, item_id));
		}

		return res;
	}

	@Override
	public void setItemName(int item_id, String item_name) {
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		valOps.set(item_id + ":item_name", item_name);
	}

	@Override
	public String getUserRankID(int user_id, int item_id){
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		return valOps.get(user_id + ":userRankID:" + item_id);
	}

	@Override
	public Set<String> getTitleRanks(int item_id){
		ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
		return zsetOps.reverseRange(item_id + ":titleRanks", 0, -1);
	}

	@Override
	public void addUserFollowing(int user_id, int userFollow_id){
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		setOps.add(user_id + ":userFollowing", String.valueOf(userFollow_id));
	}

	@Override
	public List<String> getUserFollowing(int user_id){
		List<String> res = new ArrayList<String>();
		Set<String> followingSet = getUserFollowingSet(user_id);
		for(String following_id: followingSet){
			res.add("{'user_id': " + following_id + ", 'username': '" + getUsername(Integer.parseInt(following_id)) + "' }");
		}
		
		return res;
	}

	@Override
	public void addRankToItem(int user_id, int item_id, int category_id, int rank){
		addRankToItem(user_id, item_id, category_id, rank, 0);
	}

	@Override
	public void addRankToItem(int user_id, int item_id, int category_id, int rank, int review_id) {
		List<String> keysForTotals = constructKeysForTotals(item_id);
		List<String> userFollowers = getUserFollowers(user_id);
		List<String> keysForFollowing;
		boolean isRanked;

		//New rank
		if (getUserRankID(user_id, item_id) == null) {
			addUserRank(user_id, item_id);
			addUserRankID(user_id, item_id, rank, review_id);
			isRanked = false;
		}
		//Existing rank
		else { 
			rank = updateUserRankID(user_id, item_id, rank, review_id) - rank;
			isRanked = true;
		}
		addTitleRank(item_id, user_id);
		addRankToItemTotalHelper(keysForTotals, rank, isRanked);
		for (String follower : userFollowers) {
			keysForFollowing = constructKeysForFollowing(follower, item_id);
			addRankToItemTotalHelper(keysForFollowing, rank, isRanked);
		}
	}

	@Override
	public void addUserFollowers(int user_id, int userFollow_id){
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		listOps.leftPush(user_id+":userFollowers", String.valueOf(userFollow_id));
	}

	@Override
	public List<String> getUserFollowers(int user_id){
		List<String> res = new ArrayList<String>();
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		List<String> followersList = listOps.range(user_id + ":userFollowers", 0, -1);
		for(String follower_id: followersList){
			res.add("{'user_id': " + follower_id + ", 'username': '" + getUsername(Integer.parseInt(follower_id)) + "' }");
		}
		return res;
	}

	@Override
	public ItemRanks getItemRanks(int item_id, int user_id){
		ItemRanks res = new ItemRanks();
		String totalCount = getItemCount(item_id); 
		String followingCount = getFollowingItemCount(user_id, item_id);
		String totalAvg = getItemAvg(item_id);
		String followingAvg =getFollowingItemAvg(user_id, item_id);
		res.setFollowingAvg(followingAvg == null ? 0 : Float.parseFloat(followingAvg));
		res.setFollowingCount(followingCount == null ? 0 : Integer.parseInt(followingCount));
		res.setTotalAvg(totalAvg == null ? 0 : Float.parseFloat(totalAvg));
		res.setTotalCount(totalCount == null ? 0 : Integer.parseInt(totalCount));
		res.setUserRank(getUserRankID(user_id, item_id));

		return res;
	}

	@Override
	public List<String> getFollowingItemRanks(int item_id, int user_id){
		List<String> res = new ArrayList<String>();
		Set<String> followingSet = getUserFollowingSet(user_id);
		Set<String> all = getTitleRanks(item_id);
		all.retainAll(followingSet);
		for(String followingId : all ){
			res.add(getUserRankID(Integer.parseInt(followingId), item_id));
		}

		return res;
	}
	
	@Override
	public void addItemName(int item_id , String item_name){
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		valOps.set(item_id + ":itemName", item_name);
	}

	@Override
	public void addUsername(int user_id , String username){
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		valOps.set(user_id + ":username", username);
	}
	
	@Override
	public String getUsername(int user_id){
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		return valOps.get(user_id + ":username");
	}
	
	@Override
	public UserDetails getUserDetails(int user_id, int my_user_id){
		UserDetails res = new UserDetails();
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		Long userFollowersCount = listOps.size(user_id + ":userFollowers");
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		Long userFollowingCount =  setOps.size(user_id + ":userFollowing");
		
		res.setIsFollowing(isUserFollowing(my_user_id, user_id));
		res.setUserFollowersCount(userFollowersCount == null ? 0 : userFollowersCount.intValue());
		res.setUserFollowingCount(userFollowingCount == null ? 0 : userFollowingCount.intValue());
		
		return res;
 	}
	
	@Override
	public void setUsersRankHistToNewFollower(int userFollower_id, int user_id){
		List<String> userRanks = getUserRanks(user_id);
		List<String> keysForFollowing;
		for(String rank : userRanks){
			JSONObject jsonObj = new JSONObject(rank);
			int item_rank = jsonObj.getInt("rank");
			int item_id = jsonObj.getInt("item_id");
			keysForFollowing = constructKeysForFollowing(userFollower_id, item_id);
			addRankToItemTotalHelper(keysForFollowing, item_rank, false);
		}
	}
	
	@Override
	public int getReviewId(int user_id, int item_id) {
		String userRank = getUserRankID(user_id, item_id);
		if (userRank != null) {
			JSONObject jsonObj = new JSONObject(userRank);
			if (jsonObj.has("review_id"))
				return jsonObj.getInt("review_id");
		}

		return 0;
	}
	
	@Override
	public void deleteReviewId(int user_id, int item_id) {
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		int rank = getUserRank(user_id, item_id);
		if (rank != 0) {
			addUserRankID(user_id, item_id, rank, 0);
		} 
	}
	
	
	private Set<String> getUserFollowingSet(int user_id) {
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		return setOps.members(user_id + ":userFollowing");
	}
	
	private Boolean isUserFollowing(int my_user_id, int user_id) {
		if ( my_user_id == user_id){
			return null;
		} else {
			SetOperations<String, String> setOps = redisTemplate.opsForSet();
			return setOps.isMember(my_user_id + ":userFollowing", String.valueOf(user_id));
		}
	}

	private String getUserRankID(int user_id, String item_id){
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		return valOps.get(user_id + ":userRankID:" + item_id);
	}

	private void addRankToItemTotalHelper(List<String> keys, int rank, boolean isRanked){
		SessionCallback<List<String>> callback = new SessionCallback<List<String>>() {
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public List<String> execute(RedisOperations operations) throws DataAccessException {
				keys.add(keys.get(0));
				keys.add(keys.get(1));
				operations.watch(keys);
				// Read
				// May UNWATCH if updates are already done
				String totalStr = (String)operations.opsForValue().get(keys.get(0));
				Integer total = totalStr == null ? 0 : Integer.parseInt(totalStr);
				String countStr = (String)operations.opsForValue().get(keys.get(1));
				Integer count = countStr == null ? 0 : Integer.parseInt(countStr);
				total += rank;
				count = isRanked ? count : count + 1;
				float avg = (float) total / count;
				operations.multi();
				// Write operations
				operations.opsForValue().set(keys.get(0), total.toString());
				operations.opsForValue().set(keys.get(1), count.toString());
				operations.opsForValue().set(keys.get(2), String.valueOf(avg));

				return operations.exec();
			}
		};

		redisTemplate.execute(callback);
	}

	private List<String> constructKeysForTotals(int item_id) {
		List<String> keys = new ArrayList<String>();
		keys.add(item_id + ":itemTotalRank");
		keys.add(item_id + ":itemCountRank");
		keys.add(item_id + ":itemAvgRank");
		return keys;
	}

	private List<String> constructKeysForFollowing(int user_id, int item_id) {
		List<String> keys = new ArrayList<String>();
		keys.add(user_id + ":userFollowingItemTotalRank:" + item_id);
		keys.add(user_id + ":userFollowingItemCountRank:" + item_id);
		keys.add(user_id + ":userFollowingItemAvgRank:" + item_id);
		return keys;
	}
	
	private List<String> constructKeysForFollowing(String user_id, int item_id) {
		List<String> keys = new ArrayList<String>();
		keys.add(user_id + ":userFollowingItemTotalRank:" + item_id);
		keys.add(user_id + ":userFollowingItemCountRank:" + item_id);
		keys.add(user_id + ":userFollowingItemAvgRank:" + item_id);
		return keys;
	}

	
	private int updateUserRankID(int user_id, int item_id, int rank, int review_id) {
		JSONObject jsonObj = new JSONObject(getUserRankID(user_id, item_id));
		int oldRank = jsonObj.getInt("rank");

		if (review_id == 0){
			try {
				review_id =jsonObj.getInt("review_id");
			} catch(JSONException e){
				review_id = 0;
			}
		}

		addUserRankID(user_id, item_id, rank, review_id);

		return rank - oldRank;
	}

	private void addTitleRank(int item_id, int user_id){
		ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
		zsetOps.add(item_id + ":titleRanks", String.valueOf(user_id), System.currentTimeMillis());
	}

	// if review id doesn't exit, review_id = 0
	private void addUserRankID(int user_id, int item_id, int rank, int review_id){
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		String val;
		String item_name = valOps.get(item_id + ":itemName");
		String username = valOps.get(user_id + ":username");

		if(review_id != 0){
			val = "{ 'username':'" + username + "', 'rank':" + rank + ",'item_id':" + item_id + ",'name':'" + item_name + "','review_id':" + review_id + "}";
		} else {
			val = "{ 'username':'" + username + "', 'rank':" + rank + ",'item_id':" + item_id + ",'name':'" + item_name + "'}";
		}

		valOps.set(user_id + ":userRankID:" + item_id, val);
	}

	private void addUserRank(int user_id, int item_id){
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		listOps.leftPush(user_id+":userRanks", String.valueOf(item_id));
	}

	private List<String> getUserRanksItemID(int user_id) {
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		List<String> res = listOps.range(user_id + ":userRanks", 0, -1);
		return res;
	}

	private String getItemAvg(int item_id) {
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		return valOps.get(item_id + ":itemAvgRank");
	}

	private String getFollowingItemAvg(int user_id, int item_id) {
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		return valOps.get(user_id + ":userFollowingItemAvgRank:" + item_id);
	}

	private String getItemCount(int item_id) {
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		return valOps.get(item_id + ":itemCountRank");
	}

	private String getFollowingItemCount(int user_id, int item_id) {
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		return valOps.get(user_id + ":userFollowingItemCountRank:" + item_id);
	}	

	private int getUserRank(int user_id, int item_id) {
		String userRank = getUserRankID(user_id, item_id);
		if (userRank != null) {
			JSONObject jsonObj = new JSONObject(userRank);
			return jsonObj.getInt("rank");
		}

		return 0;
	}
}
