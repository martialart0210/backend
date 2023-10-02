package com.m2l.meta.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.m2l.meta.dto.UserAuthDetected;
import com.m2l.meta.dto.UserDto;
import com.m2l.meta.entity.User;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAuthUtils {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserService userService;


    public UserAuthDetected getUserInfoFromReq() throws MamException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserAuthDetected userAuthDetected = new UserAuthDetected();
        userAuthDetected.setUsername(userName);
        userAuthDetected.setRoles(roles);

        return userAuthDetected;
    }

    public UserDto getUserInfo(String username) throws Exception {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userService.getUserByUsername(username),userDto);
        userDto.setPassword(null);
        return userDto;
    }

    public UserDto getCurrentUserInfo() throws Exception {
        UserAuthDetected userAuthDetected = this.getUserInfoFromReq();
        return this.getUserInfo(userAuthDetected.getUsername());
    }

    public void grantAuthenticateByAccessToken(String accessToken) throws Exception {
        DecodedJWT decodedJWT = tokenUtils.decodedAccessToken(accessToken);
        User user = userService.getUserByUsername(decodedJWT.getSubject());
        if (ObjectUtils.isEmpty(user)) {
            throw new Exception();
        }
        String[] roles = decodedJWT.getClaim(CommonConstants.Authentication.ROLES).asArray(String.class);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
                null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

//    public void grantAuthentication(User user) throws Exception {
//        DecodedJWT decodedJWT = tokenUtils.decodedAccessToken(accessToken);
//        User user = userService.getUserByUsername(decodedJWT.getSubject());
//        if (ObjectUtils.isEmpty(user)) {
//            throw new Exception();
//        }
//        String[] roles = decodedJWT.getClaim(CommonConstants.Authentication.ROLES).asArray(String.class);
//        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
//        for (String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(),
//                null, authorities);
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//    }


}
