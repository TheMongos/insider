package db.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
	
	private String getUserRankID(int user_id, String item_id){
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
	public Set<String> getUserFollowing(int user_id) {
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		return setOps.members(user_id + ":userFollowing");
	}

	@Override
	public void addRankToItem(int user_id, int item_id, int category_id, int rank){
		addRankToItem(user_id, item_id, category_id, rank, 0);
	}
	
	@Override
	public void addRankToItem(int user_id, int item_id, int category_id, int rank, int review_id) {
		List<String> keysForTotals = constructKeysForTotals(item_id);
		List<String> keysForFollowing = constructKeysForFollowing(user_id, item_id);
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
		addRankToItemTotalHelper(keysForFollowing, rank, isRanked);
	}

	@Override
	public void addUserFollowers(int user_id, int userFollow_id){
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		listOps.leftPush(user_id+":userFollowers", String.valueOf(userFollow_id));
	}
	
	@Override
	public List<String> getUserFollowers(int user_id){
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		List<String> res = listOps.range(user_id + ":userFollowers", 0, -1);
		return res;
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
		String item_name = valOps.get(item_id + ":item_name");
		
		if(review_id != 0){
			val = "{'rank':" + rank + ",'item_id':" + item_id + ",'name':'" + item_name + "','review_id':" + review_id + "}";
		} else {
			val = "{'rank':" + rank + ",'item_id':" + item_id + ",'name':'" + item_name + "}";
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
}
