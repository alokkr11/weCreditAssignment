package com.weCredit.weCreditAssignment.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserDto {
    @NotNull(message = "Name cannot be null")
    private String name;
    @Email(message = "Invalid email format")
    private String email;
    @Pattern(regexp = "^\\d{10}$")
    private String mobileNumber;
}
