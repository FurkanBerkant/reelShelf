package com.berkant.reelshelf.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
public class UserDTO {
    @Email
    @NotBlank
    private String email;
    private String password;
}
