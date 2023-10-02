package com.m2l.meta.config.oauth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.m2l.meta.config.AppProperties;
import com.m2l.meta.utils.CommonConstants;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TokenProvider {

    @Value("${app.login.jwtPrefix}")
    private String PREFIX_TOKEN;
    @Value("${app.login.jwtSecretKey.accessToken}")
    private String SECRET_KEY_ACCESS_TOKEN;
    @Value("${app.login.jwtSecretKey.refreshToken}")
    private String SECRET_KEY_REFRESH_TOKEN;
    @Value("${app.login.jwtExpiration.accessToken}")
    private String JWT_EXPIRATION_ACCESS_TOKEN;
    @Value("${app.login.jwtExpiration.refreshToken}")
    private String JWT_EXPIRATION_REFRESH_TOKEN;

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private AppProperties appProperties;

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createAccessToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<String> roleList = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Date expiryDate = new Date(System.currentTimeMillis() + Integer.parseInt(JWT_EXPIRATION_ACCESS_TOKEN) * 1000L);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY_ACCESS_TOKEN.getBytes());

        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(expiryDate)
                .withClaim(CommonConstants.Authentication.ROLES, roleList).sign(algorithm);
    }

    public String createRefreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<String> roleList = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Date expiryDate = new Date(System.currentTimeMillis() + Integer.parseInt(JWT_EXPIRATION_REFRESH_TOKEN) * 1000L);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY_REFRESH_TOKEN.getBytes());

        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withExpiresAt(expiryDate)
                .withClaim(CommonConstants.Authentication.ROLES, roleList).sign(algorithm);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY_ACCESS_TOKEN)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY_ACCESS_TOKEN).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

}
