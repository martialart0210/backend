package com.m2l.meta.service;

import com.m2l.meta.dto.UserDto;
import com.m2l.meta.exceptions.MamException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.m2l.meta.entity.User;

public interface UserService extends UserDetailsService {
    User registerNewUser(String providerId);

    User getUserByUsername(String username);
    UserDto createNewUser(UserDto userDto) throws MamException;
    default void updateAuthenticationType(String username, String oauth2ClientName) {
        System.out.println("Updated user's authentication type to " );
    }

    void deleteUser() throws Exception;
}
