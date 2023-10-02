package com.mar.meta.service;

import com.mar.meta.dto.UserDto;
import com.mar.meta.exceptions.MamException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.mar.meta.entity.User;

public interface UserService extends UserDetailsService {
    User getUserByUsername(String username);

    UserDto createNewUser(UserDto userDto) throws MamException;

}
