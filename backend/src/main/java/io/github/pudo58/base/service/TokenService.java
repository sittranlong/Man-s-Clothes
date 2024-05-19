package io.github.pudo58.base.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@SuppressWarnings("all")
public class TokenService {
    private static final String REDIS_KEY_PREFIX = "jwt-token:";
    private RedisTemplate<String, Object> redisTemplate;
    private final ListOperations<String, String> listOperations;
    @Value("${jwt.expiration}")
    private int expiration;

    public TokenService(RedisTemplate<String, Object> redisTemplate, ListOperations<String, String> listOperations) {
        this.redisTemplate = redisTemplate;
        this.listOperations = listOperations;
    }

    public void saveToken(String username, String token) {
        String key = REDIS_KEY_PREFIX + username;
        listOperations.leftPush(key, token);
        redisTemplate.expire(key, expiration, TimeUnit.DAYS);
    }

    public List<String> getTokens(String username) {
        String key = REDIS_KEY_PREFIX + username;
        return listOperations.range(key, 0, -1);
    }

    public void deleteTokens(String username) {
        String key = REDIS_KEY_PREFIX + username;
        redisTemplate.delete(key);
    }
}
