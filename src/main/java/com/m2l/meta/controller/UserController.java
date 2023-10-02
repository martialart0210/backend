package com.m2l.meta.controller;

import com.m2l.meta.dto.UserDto;
import com.m2l.meta.exceptions.MamException;
import com.m2l.meta.service.UserService;
import com.m2l.meta.utils.CommonConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "Delete User")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        try {
            userService.deleteUser();
            return success(CommonConstants.MessageSuccess.SC004, null, null);
        } catch (Exception e) {
            return failed(CommonConstants.MessageError.ER033, null);
        }
    }

}
