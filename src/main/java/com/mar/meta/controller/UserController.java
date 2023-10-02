package com.mar.meta.controller;

import com.mar.meta.dto.UserDto;
import com.mar.meta.exceptions.MamException;
import com.mar.meta.service.UserService;
import com.mar.meta.utils.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
@Tag(name = "user-api")
public class UserController extends BaseController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "User Register")
    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody UserDto userDto) {
        try {
            UserDto returnData = userService.createNewUser(userDto);
            return success(CommonConstants.MessageSuccess.SC002, returnData, null);
        } catch (MamException e) {
            return failed(e.getMsgCode(), e.getParam());
        }
    }

}
