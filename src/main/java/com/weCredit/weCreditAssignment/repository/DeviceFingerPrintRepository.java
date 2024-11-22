package com.weCredit.weCreditAssignment.repository;

import com.weCredit.weCreditAssignment.entity.DeviceFingerprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceFingerPrintRepository extends JpaRepository<DeviceFingerprint, Long> {
    boolean existsByUserIdAndDeviceFingerprint(Long userId, String fingerprint);
}
