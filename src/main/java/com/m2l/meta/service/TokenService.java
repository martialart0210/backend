package com.m2l.meta.service;

/**
 * The Class TokenService.
 *
 * @author <a href="mailto:phuoc@dxplus.io">phuoc</a>
 */
public interface TokenService {

    void addToBlacklist(String token, long expiresInMinutes);

    void addSession(String sessionID, String username);

    boolean isSessionExisted(String username, String sessionId);

    String getLogInSession(String username);

    void clearSession(String username);

    void replaceSession(String username, String sessionId);

    boolean isBlacklisted(String token);

    void saveRefreshToken(String userId, String refreshToken, long expiresInMinutes);

    String getRefreshToken(String userId);

    void deleteRefreshToken(String username);

}
