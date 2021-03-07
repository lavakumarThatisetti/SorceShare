package com.sorceshare.userstore.payload.request;
import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    @NotBlank
    @Size(max = 20)
    private String firstName;

    @Size(max = 20)
    private String lastName;

    @NotNull
    @Size(min=5, max = 30)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 15)
    private String phoneNo;

    @NotBlank
    private String dob;

    @NotBlank
    private String gender;

    @NotBlank
    private String profession;

    @NotBlank
    @Size(max = 150)
    private String password;

}
