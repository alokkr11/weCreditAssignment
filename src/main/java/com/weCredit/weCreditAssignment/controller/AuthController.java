package com.weCredit.weCreditAssignment.controller;

import com.weCredit.weCreditAssignment.dto.LoginDto;
import com.weCredit.weCreditAssignment.dto.UserDto;
import com.weCredit.weCreditAssignment.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto,
                                               HttpServletRequest request) {
        return ResponseEntity.ok(authService.registerUser(userDto, request));
    }

    @GetMapping("/get-otp")
    public ResponseEntity<String> getOtp(@RequestParam String mobileNumber) {
        return ResponseEntity.ok(authService.sendOrResendOtp(mobileNumber));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDto loginDto,
                                            HttpServletRequest request) {
        return ResponseEntity.ok(authService.login(loginDto, request));
    }


}
