package com.m2l.meta.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.m2l.meta.config.oauth.OAuth2UserInfo;
import com.m2l.meta.config.oauth.OAuth2UserInfoFactory;
import com.m2l.meta.dto.ApiResponseDto;
import com.m2l.meta.dto.AuthenDto;
import com.m2l.meta.dto.LogInDto;
import com.m2l.meta.dto.UserDto;
import com.m2l.meta.enum_class.AuthProvider;
import com.m2l.meta.repository.UserRepository;
import com.m2l.meta.service.FirebaseMessagingService;
import com.m2l.meta.service.TokenService;
import com.m2l.meta.service.UserService;
import com.m2l.meta.utils.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@PropertySource("classpath:application.properties")
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LOG_OUT = "Log_out";
    public static final String PROVIDER = "provider";
    public static final String BEARER = "Bearer";
    public static final String APPLICATION_JSON = "application/json";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String CODE = "code";
    public static final String TOKEN = "token";
    public static final String COMMON_HEADER = ", x-xsrf-token, Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, "
            + "Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers";
    public static String currentUsername = "";

    private final AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthUtils authUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private BeanUtil beanUtil;

    @Autowired
    MessageUtils messageUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    SessionRegistry sessionRegistry;

    @Autowired
    TokenService tokenService;

    @Autowired
    FirebaseMessagingService firebaseMessagingService;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManage) {
        this.authenticationManager = authenticationManage;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String requestData = "";
        implementBean(request);
        try {
            requestData = request.getReader().lines().collect(Collectors.joining());
        } catch (IOException ex) {

        }
        LogInDto loginInfor = null;
        String username = null;
        String password = null;
        if (!StringUtils.isEmpty(request.getParameter("code"))) {
            String provider = request.getParameter("provider");
            OAuth2UserInfo userInfo = decodeToken(request);
            username =  userInfo.getId();
            password = "metaverse123@2023";
        } else {
            try {
                loginInfor = new Gson().fromJson(requestData, LogInDto.class);
                username = loginInfor.getUsername();
                password = loginInfor.getPassword();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        currentUsername = username;
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // set content type as json
        response.setContentType("application/json");
        String userName = currentUsername;
        implementBean(request);
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(CommonConstants.MessageError.ER017)
                .message(messageUtils.getMessage(CommonConstants.MessageError.ER017, null)).data(null)
                .status(CommonConstants.ApiStatus.STATUS_ERROR).build();
        try {
            response.getWriter().write(beanUtil.objectToJsonString(apiResponseDto));
            response.setContentType("application/json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        implementBean(request);
        String accessToken = tokenUtils.generateAccessToken(request, (User) authResult.getPrincipal());
        String refreshToken = tokenUtils.generateRefreshToken(request, (User) authResult.getPrincipal());
        UserDto userDto = new UserDto();
        try {
            userDto = authUtils.getUserInfo(((User) authResult.getPrincipal()).getUsername());
            com.m2l.meta.entity.User user = userRepository.findUsersByUsername(userDto.getUsername()).orElseThrow();
            String sessionId = request.getSession().getId();
//            if (tokenService.isSessionExisted(((User) authResult.getPrincipal()).getUsername(), sessionId)) {
////                Send force log out to old device
//                String firebaseRegisToken = request.getParameter(TOKEN);
//                firebaseMessagingService.sendNotificationToBatch(Collections.singletonList(user.getDeviceToken()), LOG_OUT,sessionId);
//            }
//            tokenService.addSession(sessionId, user.getUsername());
            user.setConnectionStatus(true);
            user.setLastAccess(LocalDateTime.now());
            if (ObjectUtils.isEmpty(user.getProvider())) {
                OAuth2UserInfo oAuth2UserInfo = decodeToken(request);
                String provider = request.getParameter(PROVIDER);
                user.setEmail(oAuth2UserInfo.getEmail());
                user.setName(oAuth2UserInfo.getName());
                user.setImageUrl(oAuth2UserInfo.getImageUrl());
                user.setProvider(AuthProvider.valueOf(provider));
                userDto = authUtils.getUserInfo(((User) authResult.getPrincipal()).getUsername());
            }
            user = userRepository.save(user);
            userDto.setAuthProvider(user.getProvider());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        AuthenDto authenDto = new AuthenDto(accessToken, refreshToken, BEARER, userDto);
        ApiResponseDto apiResponseDto = ApiResponseDto.builder().code(CommonConstants.MessageSuccess.SC007)
                .message(messageUtils.getMessage(CommonConstants.MessageSuccess.SC007, null)).data(authenDto)
                .status(CommonConstants.ApiStatus.STATUS_OK).build();
        try {
            response.getWriter().write(beanUtil.objectToJsonString(apiResponseDto));
            response.setContentType(APPLICATION_JSON);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS,
                CommonConstants.Authentication.ACCESS_TOKEN + "," + CommonConstants.Authentication.REFRESH_TOKEN
                        + COMMON_HEADER);
    }

    private static OAuth2UserInfo decodeToken(HttpServletRequest request) {
        String tokenId = request.getParameter(CODE);
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(tokenId));
        System.out.println("JWT Body : " + body);
        Type mapType = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, Object> son = new Gson().fromJson(body, mapType);
        String provider = request.getParameter(PROVIDER);
        return OAuth2UserInfoFactory.getOAuth2UserInfo(provider, son);
    }

    private void implementBean(HttpServletRequest request) {
        if (tokenUtils == null || objectMapper == null || authUtils == null || messageUtils == null || beanUtil == null
                || userRepository == null) {
            ServletContext servletContext = request.getServletContext();
            WebApplicationContext webApplicationContext = WebApplicationContextUtils
                    .getWebApplicationContext(servletContext);
            assert webApplicationContext != null;
            authUtils = webApplicationContext.getBean(UserAuthUtils.class);
            objectMapper = webApplicationContext.getBean(ObjectMapper.class);
            tokenUtils = webApplicationContext.getBean(TokenUtils.class);
            messageUtils = webApplicationContext.getBean(MessageUtils.class);
            beanUtil = webApplicationContext.getBean((BeanUtil.class));
            userRepository = webApplicationContext.getBean(UserRepository.class);
            tokenService = webApplicationContext.getBean(TokenService.class);
            firebaseMessagingService = webApplicationContext.getBean(FirebaseMessagingService.class);
        }
    }

}
