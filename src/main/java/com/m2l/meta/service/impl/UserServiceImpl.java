package com.m2l.meta.service.impl;

import com.m2l.meta.dto.UserDto;
import com.m2l.meta.entity.RoleEntity;
import com.m2l.meta.entity.User;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.repository.RoleRepository;
import com.m2l.meta.repository.UserRepository;
import com.m2l.meta.service.UserService;
import com.m2l.meta.utils.CommonConstants;
import com.m2l.meta.utils.TokenUtils;
import com.m2l.meta.utils.UserAuthUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private HttpServletRequest request;

    private UserAuthUtils userAuthUtils;

    private static final String SEPARATOR = "<>";

    @Autowired
    public UserServiceImpl(@Lazy UserAuthUtils userAuthUtils) {
        this.userAuthUtils = userAuthUtils;
    }

    /**
     * Load user by username
     *
     * @param username
     * @return userdetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUsersByUsername(username);
        User user;
        user = userOptional.orElse(new User());
        if (userOptional.isEmpty()) {
            user = registerNewUser(username);
        }
        if (user.getIsBanned()) {
            throw new UsernameNotFoundException("User has been banned");
        }
        List<RoleEntity> userRoles = roleRepository.getRolesUser(user.getId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(userRole -> authorities.add(new SimpleGrantedAuthority(userRole.getRoleName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User registerNewUser(String providerId) {
        User user = new User();
        user.setPassword(encoder.encode("metaverse123@2023"));
        user.setIsBanned(false);
        user.setUuid(UUID.randomUUID().toString().replace("-",""));
        user.setProviderId(providerId);
        user.setUsername(providerId);
        return userRepository.save(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUsersByUsername(username).orElse( new User());
    }

    @Override
    public UserDto createNewUser(UserDto userDto) throws MamException {
        Optional<User> userOptional = userRepository.findUsersByUsernameOrEmailOrPhone
                        (userDto.getUsername(),
                        userDto.getEmail(),
                        userDto.getPhone());
        if (userOptional.isPresent()) {
            throw new MamException(CommonConstants.MessageError.ER001, new Object[]{userDto});
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setUuid(UUID.randomUUID().toString().replace("-",""));
        BeanUtils.copyProperties(userRepository.save(user), userDto);
        return userDto;
    }

    @Override
    public void deleteUser() throws Exception {
        Optional<User> userOptional = userRepository.findUsersByUsername(userAuthUtils.getCurrentUserInfo().getUsername());
        userOptional.ifPresent(user -> userRepository.delete(user));
    }

}
