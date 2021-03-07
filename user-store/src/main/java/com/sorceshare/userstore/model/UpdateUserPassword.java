package com.sorceshare.userstore.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserPassword {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
