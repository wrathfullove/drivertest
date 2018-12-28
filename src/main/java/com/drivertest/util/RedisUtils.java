package com.drivertest.util;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * Created by Jaxw on 12/25/2018.
 */
public class RedisUtils {
    @Resource(name="redisTemplate")
    private RedisTemplate<String, String> rt;
    public void flushdb(){
        rt.execute(new RedisCallback<Object>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }
}
