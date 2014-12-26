package db.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;

public class RedisTest {
	private RedisTemplate<String, String> redisTemplate;

	public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void simpleValueOperations() {
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		listOps.leftPush("David", "Hagever");
		String val = listOps.index("David", 0);
		System.out.println("key value found = " + val);
	}

	public List<String> getUserRanks(int user_id) {
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		List<String> res = listOps.range(user_id + ":userRanks", 0, -1);
		return res;
	}

	//elad functions

	public void addUserRank(int user_id, int item_id){
		ListOperations<String, String> listOps = redisTemplate.opsForList();
		listOps.leftPush(user_id+":userRanks", String.valueOf(item_id));
	}

	public void addUserRankID(int user_id, int item_id, int rank, String item_name, int review_id){
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		String val;
		if(review_id != 0){
			val = "{'rank':" + rank + ",'item_id':" + item_id + ",'name':'" + item_name + "','review_id':" + review_id + "}";
		} else {
			val = "{'rank':" + rank + ",'item_id':" + item_id + ",'name':'" + item_name + "}";
		}

		valOps.set(user_id + ":userRankID:" + item_id, val);
	}

	public String getUserRankID(int user_id, int item_id){
		ValueOperations<String, String> valOps = redisTemplate.opsForValue();
		return valOps.get(user_id + ":userRankID:" + item_id);
	}

	public void addTitleRank(int item_id, int user_id){
		ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
		zsetOps.add(item_id + ":titleRanks", String.valueOf(user_id), System.currentTimeMillis());
	}

	public Set<String> getTitleRanks(int item_id){
		ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
		return zsetOps.reverseRange(item_id + ":titleRanks", 0, -1);
	}

	public void addUserFollowing(int user_id, int userFollow_id){
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		setOps.add(user_id + ":userFollowing", String.valueOf(userFollow_id));
	}

	public Set<String> getUserFollowing(int user_id) {
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		return setOps.members(user_id + ":userFollowing");
	}


	public void addRankToItemTotal(int item_id, int category_id, int rank) {

		SessionCallback<List<String>> callback = new SessionCallback<List<String>>() {
			@Override
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public List<String> execute(RedisOperations operations) throws DataAccessException {
				List<String> keys = new ArrayList<String>();
				keys.add(item_id + ":itemTotalRank");
				keys.add(item_id + ":itemCountRank");
				operations.watch(keys);
				// Read
				// May UNWATCH if updates are already done
				String totalStr = (String)operations.opsForValue().get(keys.get(0));
				Integer total = totalStr == null ? 0 : Integer.parseInt(totalStr);
				String countStr = (String)operations.opsForValue().get(keys.get(1));
				Integer count = countStr == null ? 0 : Integer.parseInt(countStr);
				total += rank;
				count += 1;
				float avg = (float) total / count;
				operations.multi();
				operations.opsForValue().set(keys.get(0), total.toString());
				operations.opsForValue().set(keys.get(1), count.toString());
				operations.opsForValue().set(item_id + ":itemAvgRank", String.valueOf(avg));
				// Write operations
				return operations.exec();
			}
		};

		redisTemplate.execute(callback);
	}



}
