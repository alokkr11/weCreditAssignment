package com.weCredit.weCreditAssignment.service;

import com.weCredit.weCreditAssignment.dto.UserDto;
import com.weCredit.weCreditAssignment.entity.DeviceFingerprint;
import com.weCredit.weCreditAssignment.entity.UserEntity;
import com.weCredit.weCreditAssignment.repository.DeviceFingerPrintRepository;
import com.weCredit.weCreditAssignment.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeviceFingerPrintRepository deviceFingerprintRepository;

    @Override
    public String registerUser(UserDto userDto, HttpServletRequest request) {
        Optional<UserEntity> existingUser = userRepository.findByMobileNumber(userDto.getMobileNumber());
        if (existingUser.isPresent()) {
            return "User already exists!";
        }

        UserEntity u = new UserEntity();

        u.setName(userDto.getName());
        u.setEmail(userDto.getEmail());
        u.setMobileNumber(userDto.getMobileNumber());

        String deviceFingerPrint = generateFingerprint(request);
        u.setLastDeviceFingerprint(deviceFingerPrint);

        userRepository.save(u);

        saveDeviceFingerprint(u.getId(), deviceFingerPrint);
        return "User Registered "+ u.toString();
    }

    @Override
    public String login(String phoneNo) {
        return null;
    }

    @Override
    public String sendOrResendOtp(String phoneNo) {
        return null;
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
