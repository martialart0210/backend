package com.m2l.meta.config.oauth;

import com.m2l.meta.config.AppProperties;
import com.m2l.meta.dto.ApiResponseDto;
import com.m2l.meta.dto.AuthenDto;
import com.m2l.meta.dto.UserDto;
import com.m2l.meta.entity.User;
import com.m2l.meta.exception.BadRequestException;
import com.m2l.meta.repository.UserRepository;
import com.m2l.meta.utils.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static com.m2l.meta.config.oauth.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;


@Component
public class OAuthLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    MessageUtils messageUtils;

    @Autowired
    private BeanUtil beanUtil;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserAuthUtils authUtils;

    AppProperties appProperties;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String accessToken = determineAccessToken(request, response, authentication);
        String refreshToken = determineRefreshToken(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to ");
            return;
        }
        UserDto userDto = new UserDto();
        try {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            userDto = authUtils.getUserInfo(userPrincipal.getId().toString());
            User user = userRepository.findByEmailAndProvider(userPrincipal.getUsername(), userPrincipal.getProvider()).orElseThrow();
            user.setLastAccess(LocalDateTime.now());
            userRepository.save(user);
        } catch (Exception e) {
            System.out.println("USER ERROR");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try {
            AuthenDto authenDto = new AuthenDto(accessToken, refreshToken, "Bearer", userDto);
            ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(CommonConstants.MessageSuccess.SC007)
                    .message(messageUtils.getMessage(CommonConstants.MessageSuccess.SC007, null)).data(authenDto)
                    .status(CommonConstants.ApiStatus.STATUS_OK).build();
            response.getWriter().write(beanUtil.objectToJsonString(apiResponseDto));
            response.setContentType("application/json");
        } catch (IOException e) {
            System.out.println("TOKEN ERROR");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        response.setHeader("Access-Control-Expose-Headers",
                CommonConstants.Authentication.ACCESS_TOKEN + "," + CommonConstants.Authentication.REFRESH_TOKEN
                        + ", x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
                        + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        System.out.println("SUCCESS");
        System.out.println(">>>>>>>>>>>>>>>> ACCESS-TOKEN : " + accessToken);
        response.sendRedirect("https://unity.vietprojectgroup.com/login?"+CommonConstants.Authentication.ACCESS_TOKEN+"="+accessToken+"&"+CommonConstants.Authentication.REFRESH_TOKEN+"="+refreshToken);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    protected String determineAccessToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String token = tokenProvider.createAccessToken(authentication);

        return token;
    }

    protected String determineRefreshToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String token = tokenProvider.createAccessToken(authentication);

        return token;
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }


}
