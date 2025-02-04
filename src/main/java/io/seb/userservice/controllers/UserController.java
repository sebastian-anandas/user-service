package io.seb.userservice.controllers;

import io.seb.userservice.dtos.*;
import io.seb.userservice.dtos.ResponseStatus;
import io.seb.userservice.models.Token;
import io.seb.userservice.models.User;
import io.seb.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {

        Token token = userService.login(
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(token);

        return responseDto;
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto requestDto) {

        if (requestDto == null) {
            throw new IllegalArgumentException("SignUpRequestDto cannot be null");
        }

        User user = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        SignUpResponseDto responseDto = new SignUpResponseDto();
        responseDto.setUser(user);
        responseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return responseDto;
    }

    @GetMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") String token) {

        User user = userService.validateToken(
                token
        );

        return UserDto.fromUser(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto) {

        ResponseEntity<Void> responseEntity = null;

        try {
            userService.logout(requestDto.getToken());
            responseEntity = new ResponseEntity<>(
                    HttpStatus.OK
            );
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(
                    HttpStatus.UNAUTHORIZED
            );
        }

        return responseEntity;
    }


}
