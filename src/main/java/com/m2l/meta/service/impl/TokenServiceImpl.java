package com.m2l.meta.service.impl;

import com.m2l.meta.service.TokenService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * The Class TokenServiceImpl.
 *
 * @author <a href="mailto:phuoc@dxplus.io">phuoc</a>
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";

    private static final String LOG_IN_SESSION = "session";
    private static final String REFRESH_TOKEN_KEY_PREFIX = "refreshToken:";
    private final RedisTemplate<String, String> redisTemplate;

    public TokenServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * @param token
     * @param expiresInMinutes
     */
    @Override
    public void addToBlacklist(String token, long expiresInMinutes) {
        String key = BLACKLIST_KEY_PREFIX + token;
        redisTemplate.opsForValue().set(key, token);
        redisTemplate.expire(key, expiresInMinutes, TimeUnit.MINUTES);
    }

    @Override
    public void addSession(String sessionID, String username) {
        redisTemplate.opsForValue().set(username, sessionID);
    }

    @Override
    public boolean isSessionExisted(String username, String sessionID) {
        return (Boolean.TRUE.equals(redisTemplate.hasKey(username)) && !Objects.equals(redisTemplate.opsForValue().get(username), sessionID));
    }

    @Override
    public String getLogInSession(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @Override
    public void clearSession(String username) {
        redisTemplate.delete(username);
    }

    @Override
    public void replaceSession(String username, String sessionId) {
        this.clearSession(username);
        this.addSession(sessionId, username);
    }

    /**
     * @param token
     * @return boolean
     */
    @Override
    public boolean isBlacklisted(String token) {
        String key = BLACKLIST_KEY_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
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
     * @param userId
     * @return String
     */
    @Override
    public String getRefreshToken(String userId) {
        String key = REFRESH_TOKEN_KEY_PREFIX + userId;
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * @param userId
     */
    @Override
    public void deleteRefreshToken(String username) {
        String key = REFRESH_TOKEN_KEY_PREFIX + username;
        redisTemplate.delete(key);
    }

}
