package com.weCredit.weCreditAssignment.controller;

import com.weCredit.weCreditAssignment.dto.UserDto;
import com.weCredit.weCreditAssignment.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto,
                                               HttpServletRequest request) {
        return ResponseEntity.ok(authService.registerUser(userDto, request));
    }

}
