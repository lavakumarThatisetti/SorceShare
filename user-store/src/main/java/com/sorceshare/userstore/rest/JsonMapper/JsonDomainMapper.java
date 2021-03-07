package com.sorceshare.userstore.rest.JsonMapper;

import com.sorceshare.userstore.model.User;
import com.sorceshare.userstore.payload.request.SignUpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;


public class JsonDomainMapper {

    public static User constructSignupRequestToUserRequest(SignUpRequest signUpRequest,PasswordEncoder encoder){
        return User.builder()
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .userName(signUpRequest.getUserName())
                .email(signUpRequest.getEmail())
                .phoneNo(signUpRequest.getPhoneNo())
                .gender(signUpRequest.getGender())
                .dob(signUpRequest.getDob())
                .profession(signUpRequest.getProfession())
                .password(encoder.encode(signUpRequest.getPassword()))
                .active(true)
                .build();
    }

}

