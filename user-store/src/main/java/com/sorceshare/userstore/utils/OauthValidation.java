package com.sorceshare.userstore.utils;

import org.mindrot.jbcrypt.BCrypt;

public class OauthValidation {

    public static String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static  boolean checkPassword(String requestPassword, String userPassword) {
        return requestPassword.equals(userPassword);
    }

    public static  boolean isPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
