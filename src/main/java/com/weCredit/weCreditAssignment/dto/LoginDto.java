package com.weCredit.weCreditAssignment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginDto {
    @NotNull
    @Pattern(regexp = "^\\d{10}$")
    private String mobileNumber;
    @Min(6)
    @Max(6)
    private String otp;
}
