package db.redis;

import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

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
}
