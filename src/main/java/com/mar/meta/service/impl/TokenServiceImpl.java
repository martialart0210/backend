package com.mar.meta.service.impl;

import com.mar.meta.service.TokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * The Class TokenServiceImpl.
 *
 * @author <a href="mailto:phuoc@dxplus.io">phuoc</a>
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";
    private static final String REFRESH_TOKEN_KEY_PREFIX = "refreshToken:";
    private final RedisTemplate<String, String> redisTemplate;

    public TokenServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     *
     * @param token
     * @param expiresInMinutes
     */
    @Override
    public void addToBlacklist(String token, long expiresInMinutes) {
        String key = BLACKLIST_KEY_PREFIX + token;
        redisTemplate.opsForValue().set(key, token);
        redisTemplate.expire(key, expiresInMinutes, TimeUnit.MINUTES);
    }

    /**
     *
     * @param token
     * @return boolean
     */
    @Override
    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_KEY_PREFIX + token;
        return redisTemplate.hasKey(key);
    }

    /**
     *
     * @param userId
     * @param refreshToken
     * @param expiresInMinutes
     */
    @Override
    public void saveRefreshToken(String userId, String refreshToken, long expiresInMinutes) {
        String key = REFRESH_TOKEN_KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(key, refreshToken);
        redisTemplate.expire(key, expiresInMinutes, TimeUnit.MINUTES);
    }

    /**
     *
     * @param userId
     * @return String
     */
    @Override
    public String getRefreshToken(String userId) {
        String key = REFRESH_TOKEN_KEY_PREFIX + userId;
        return redisTemplate.opsForValue().get(key);
    }

    /**
     *
     * @param userId
     */
    @Override
    public void deleteRefreshToken(String username) {
        String key = REFRESH_TOKEN_KEY_PREFIX + username;
        redisTemplate.delete(key);
    }

}
