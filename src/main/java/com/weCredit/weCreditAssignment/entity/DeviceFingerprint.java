package com.weCredit.weCreditAssignment.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class DeviceFingerprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to the User (unidirectional relationship)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    private String deviceFingerprint;


}
