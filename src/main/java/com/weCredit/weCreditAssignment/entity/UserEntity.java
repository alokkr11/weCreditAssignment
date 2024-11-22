package com.weCredit.weCreditAssignment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean isVerified;
    private String lastDeviceFingerprint; // Stores the last known device fingerprint
    private String otp;
    private LocalDateTime otpExpiryTime;

}
