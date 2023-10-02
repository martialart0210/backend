package com.mar.meta.service.impl;

import com.mar.meta.dto.UserDto;
import com.mar.meta.entity.RoleEntity;
import com.mar.meta.entity.User;
import com.mar.meta.exceptions.MamException;
import com.mar.meta.repository.RoleRepository;
import com.mar.meta.repository.UserRepository;
import com.mar.meta.service.UserService;
import com.mar.meta.utils.CommonConstants;
import com.mar.meta.utils.TokenUtils;
import com.mar.meta.utils.UserAuthUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

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
        Optional<User> user = userRepository.findUsersByUsernameOrEmailOrPhoneOrKakaoIdOrGoogleIdOrNaverIdOrFacebookId
                (username, username, username, username, username, username, username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        if (user.get().getIsBanned()) {
            throw new UsernameNotFoundException("User has been banned");
        }
        List<RoleEntity> userRoles = roleRepository.getRolesUser(user.get().getId());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        userRoles.forEach(userRole -> authorities.add(new SimpleGrantedAuthority(userRole.getRoleName())));
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
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
        BeanUtils.copyProperties(userRepository.save(user), userDto);
        return userDto;
    }
}
