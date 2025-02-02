package io.seb.userservice.controllers;

import io.seb.userservice.dtos.*;
import io.seb.userservice.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {

        return null;
    }

    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        return null;
    }

    public UserDto validateToken(ValidateTokenRequestDto requestDto) {
        return null;
    }

    public LoginResponseDto logout(LogoutRequestDto requestDto) {
        return null;
    }


}
