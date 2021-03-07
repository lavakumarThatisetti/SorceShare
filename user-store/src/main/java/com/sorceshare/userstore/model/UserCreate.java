package com.sorceshare.userstore.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCreate {
    private static final long serialVersionUID = -1312828236169257851L;
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String emailId;
    private String phoneNumber;
    private String dob;
    private String gender;
    private String profession;
}
