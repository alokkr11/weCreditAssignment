package com.weCredit.weCreditAssignment.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.weCredit.weCreditAssignment.Config.TwilioConfig;
import com.weCredit.weCreditAssignment.dto.LoginDto;
import com.weCredit.weCreditAssignment.dto.UserDto;
import com.weCredit.weCreditAssignment.entity.DeviceFingerprint;
import com.weCredit.weCreditAssignment.entity.UserEntity;
import com.weCredit.weCreditAssignment.repository.DeviceFingerPrintRepository;
import com.weCredit.weCreditAssignment.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceFingerPrintRepository deviceFingerprintRepository;

    @Autowired
    private TwilioConfig twilioConfig;



    @Override
    public String registerUser(UserDto userDto, HttpServletRequest request) {
        Optional<UserEntity> existingUser = userRepository.findByMobileNumber(userDto.getMobileNumber());
        if (existingUser.isPresent()) {
            return "User already exists!";
        }

        UserEntity u = new UserEntity();

        u.setName(userDto.getName());
        u.setMobileNumber(userDto.getMobileNumber());

        String deviceFingerPrint = generateFingerprint(request);
        u.setLastDeviceFingerprint(deviceFingerPrint);

        userRepository.save(u);

        saveDeviceFingerprint(u.getId(), deviceFingerPrint);
        return "User Registered "+ u.toString();
    }

    @Override
    public String login(LoginDto loginDto, HttpServletRequest request) {
        UserEntity user = userRepository.findByMobileNumber(loginDto.getMobileNumber())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(user.isLoggedIn()){
            return "User already Logged in";
        }

        // Validate OTP
        if (!loginDto.getOtp().equals(user.getOtp()) || LocalDateTime.now().isAfter(user.getOtpExpiryTime())) {
            if(LocalDateTime.now().isAfter(user.getOtpExpiryTime())){
                user.setOtp(null);
                userRepository.save(user);
            }
            return "Invalid or expired OTP";
        }

        // Generate fingerprint
        String fingerprint = generateFingerprint(request);

        // Validate device fingerprint
        if (!fingerprint.equals(user.getLastDeviceFingerprint())) {
            saveDeviceFingerprint(user.getId(), fingerprint);
            user.setLastDeviceFingerprint(fingerprint);
            user.setOtp(null);
            user.setLoggedIn(true);
            userRepository.save(user);
            return "Login successful. Logged in from a new device.";
        }

        return "Login successful. Logged in from an existing device.";
    }

    @Override
    public String sendOrResendOtp(String mobileNo) {

        // Check if the user exists
        UserEntity user = userRepository.findByMobileNumber(mobileNo)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate a new OTP
        String otp = generateOTP();

        // Set OTP and expiry time
        user.setOtp(otp);
        user.setOtpExpiryTime(LocalDateTime.now().plusMinutes(5));

        // Save the updated user entity (PATCH equivalent in JPA)
        userRepository.save(user);

        // Mock OTP sending (replace with actual SMS/Email logic)
        sendOtpViaSms(mobileNo, otp);

        return "OTP sent to " + mobileNo;

    }

    private void sendOtpViaSms(String mobileNo, String otp) {

        String otpMessage = "Your WeCredit login OTP is: "+otp;

        Message message = Message
                .creator(new PhoneNumber("+91"+mobileNo),new PhoneNumber(twilioConfig.getTrialNumber()),otpMessage)
                .create();

    }

    @Override
    public String getLoginUser() {
        return null;
    }

    @Override
    public String logout() {
        return null;
    }

    // Generate a fingerprint from request metadata
    private String generateFingerprint(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        String clientIp = request.getRemoteAddr();

        // Combine metadata to generate the fingerprint
        String rawFingerprint = userAgent + "|" + clientIp;
        return Integer.toHexString(rawFingerprint.hashCode());
    }

    // Save the fingerprint to the database
    private void saveDeviceFingerprint(Long userId, String fingerprint) {
        if (!deviceFingerprintRepository.existsByUserIdAndDeviceFingerprint(userId, fingerprint)) {
            DeviceFingerprint deviceFingerprint = new DeviceFingerprint();
            deviceFingerprint.setUserId(userId);
            deviceFingerprint.setDeviceFingerprint(fingerprint);
            deviceFingerprintRepository.save(deviceFingerprint);
        }
    }

    //generate 6 digit otp
    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }

}
