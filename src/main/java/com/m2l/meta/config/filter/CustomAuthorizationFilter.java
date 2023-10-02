package com.m2l.meta.config.filter;

import com.m2l.meta.config.oauth.TokenProvider;
import com.m2l.meta.dto.ApiResponseDto;
import com.m2l.meta.dto.AuthenDto;
import com.m2l.meta.dto.UserDto;
import com.m2l.meta.service.TokenService;
import com.m2l.meta.service.UserService;
import com.m2l.meta.utils.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.stream.Stream;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    public String[] PUBLIC_URL;

    public String PREFIX_TOKEN;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserAuthUtils authUtils;

    @Autowired
    private MessageUtils messageUtils;

    @Autowired
    private BeanUtil beanUtil;


    public CustomAuthorizationFilter(String prefixToken, String[] publicUrl) {
        super();
        this.PREFIX_TOKEN = prefixToken;
        this.PUBLIC_URL = Stream.of(publicUrl).map(item -> item.replace("/**", "")).toList()
                .toArray(new String[publicUrl.length]);
    }

    private Boolean isPublicUrl(String path) {
        for (String string : PUBLIC_URL) {
            if (path.startsWith(string)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String accessToken = request.getHeader(CommonConstants.Authentication.ACCESS_TOKEN);
        String refreshToken = request.getHeader(CommonConstants.Authentication.REFRESH_TOKEN);

//        if (tokenService.isBlacklisted(refreshToken)){
//            response.sendError(HttpStatus.FORBIDDEN.value());
//        }


        if (userService == null || authUtils == null || tokenUtils == null || messageUtils == null || beanUtil == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils
                    .getWebApplicationContext(servletContext);
            assert webApplicationContext != null;
            userService = webApplicationContext.getBean(UserService.class);
            authUtils = webApplicationContext.getBean(UserAuthUtils.class);
            tokenUtils = webApplicationContext.getBean(TokenUtils.class);
            messageUtils = webApplicationContext.getBean(MessageUtils.class);
            beanUtil = webApplicationContext.getBean(BeanUtil.class);
        }

        if (!(isPublicUrl(request.getServletPath()))) {
            if (refreshToken != null &&  tokenUtils.isAccessTokenExpired(accessToken) &&
                    !tokenUtils.isRefreshTokenExpired(refreshToken)
                    && request.getHeader(CommonConstants.Authentication.PREFIX_TOKEN).startsWith(PREFIX_TOKEN)) {
                try {
                    User user = (User) userService.loadUserByUsername(tokenUtils.decodedRefreshToken(refreshToken).getSubject());
                    if (ObjectUtils.isEmpty(user)) {
                        throw new Exception();
                    }
                    String newAccessToken = tokenUtils.generateAccessToken(request, user);
                    authUtils.grantAuthenticateByAccessToken(newAccessToken);
                    UserDto userDto;
                    try {
                        userDto = authUtils.getUserInfo(user.getUsername());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    AuthenDto authenDto = new AuthenDto(newAccessToken,refreshToken,"Bearer",userDto);
                    ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(CommonConstants.MessageSuccess.SC007)
                            .message(messageUtils.getMessage(CommonConstants.MessageSuccess.SC007, null)).data(authenDto)
                            .status(CommonConstants.ApiStatus.STATUS_OK).build();
                    response.getOutputStream().write(beanUtil.objectToJsonString(apiResponseDto).getBytes());
                } catch (Exception exception) {
                    response.setHeader("error", exception.getMessage());
                    response.sendError(HttpStatus.FORBIDDEN.value());
                }
            }
            if (accessToken != null && request.getHeader(CommonConstants.Authentication.PREFIX_TOKEN) != null
                    && request.getHeader(CommonConstants.Authentication.PREFIX_TOKEN).startsWith(PREFIX_TOKEN)
                    && !tokenUtils.isAccessTokenExpired(accessToken)) {
                try {
                    authUtils.grantAuthenticateByAccessToken(accessToken);
                } catch (Exception exception) {
                    response.setHeader("error", exception.getMessage());
                    response.sendError(HttpStatus.FORBIDDEN.value());
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    private void addTokenToHeader(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setHeader(CommonConstants.Authentication.REFRESH_TOKEN, refreshToken);
        response.setHeader(CommonConstants.Authentication.ACCESS_TOKEN, accessToken);
        response.setHeader("Access-Control-Expose-Headers",
                CommonConstants.Authentication.REFRESH_TOKEN + "," + CommonConstants.Authentication.ACCESS_TOKEN
                        + ", x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
                        + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    private void sessionCheck(String servletPath, String username, String sessionId) {

    }
}
