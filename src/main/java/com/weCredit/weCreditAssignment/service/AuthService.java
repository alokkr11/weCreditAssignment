package com.weCredit.weCreditAssignment.service;

import com.weCredit.weCreditAssignment.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    String registerUser(UserDto userDto, HttpServletRequest request);

    String login(String phoneNo);

    String sendOrResendOtp(String phoneNo);

    String getLoginUser();

    String logout();

}
