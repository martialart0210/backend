package com.m2l.meta.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.m2l.meta.entity.User;
import com.m2l.meta.repository.UserRepository;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class UserLogoutHandler implements LogoutHandler {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenUtils tokenUtils;

    public UserLogoutHandler() {
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (!StringUtils.isEmpty(request.getHeader(CommonConstants.Authentication.ACCESS_TOKEN))) {
            String accessToken = request.getHeader(CommonConstants.Authentication.ACCESS_TOKEN);
            DecodedJWT decodedJWT = tokenUtils.decodedAccessToken(accessToken);
            Optional<User> userOptional = userRepository.findUsersByUsername(decodedJWT.getSubject());
            if (userOptional.isPresent()){
                User user = userOptional.get();
                user.setConnectionStatus(false);
                user.setOffTime(LocalDateTime.now());
                userRepository.save(user);
            }
        }
    }
}