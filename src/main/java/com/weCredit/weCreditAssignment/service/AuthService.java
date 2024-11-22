package com.weCredit.weCreditAssignment.service;

import com.weCredit.weCreditAssignment.dto.LoginDto;
import com.weCredit.weCreditAssignment.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    String registerUser(UserDto userDto, HttpServletRequest request);

    String login(LoginDto loginDto, HttpServletRequest request);

    String sendOrResendOtp(String mobileNo);

    String getLoginUser();

    String logout();

}
