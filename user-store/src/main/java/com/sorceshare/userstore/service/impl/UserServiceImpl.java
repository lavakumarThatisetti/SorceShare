package com.sorceshare.userstore.service.impl;

import com.sorceshare.userstore.model.UpdateUserPassword;
import com.sorceshare.userstore.model.UserCreate;
import com.sorceshare.userstore.model.User;
import com.sorceshare.userstore.repository.RoleRepository;
import com.sorceshare.userstore.repository.UserRepository;
import com.sorceshare.userstore.security.jwt.JwtUtils;
import com.sorceshare.userstore.service.UserService;
import com.sorceshare.userstore.utils.OauthValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder encoder;
    private JwtUtils jwtUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder encoder,
                           JwtUtils jwtUtils){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }
    @Override
    public UserCreate createUser(User user) {

        UserCreate userCreate = null;
        try {
            //Save User
            User createdUser = userRepository.save(user);
            userCreate = buildUserCreateObject(createdUser);

        }catch (DataAccessException ex){
            System.out.println("Exception "+ ex.getMessage());
        }
        return userCreate;
    }

    @Override
    public UserCreate updateUser(long userId, User user) {
        UserCreate userCreate = null;
        try {
            //Get Password from DB
            Optional<User> userBase = userRepository.findById(userId);
            if(userBase.isPresent()){
                User updateUser = constructUser(user);
                //Setting Original Values
                updateUser.setId(userId);
                updateUser.setPassword(userBase.get().getPassword());
                updateUser.setCreatedAt(userBase.get().getCreatedAt());
                updateUser.setVersion(userBase.get().getVersion());
                updateUser.setRoles(userBase.get().getRoles());
                updateUser.setActive(userBase.get().isActive());
                //Save the updated User
                User createdUser = userRepository.save(updateUser);
                userCreate = buildUserCreateObject(createdUser);
            }
        }catch (DataAccessException ex){
            System.out.println("Exception "+ ex.getMessage());
        }
        return userCreate;
    }

    @Override
    public UserCreate updateUserPassword(long userId,UpdateUserPassword updateUserPassword) {
        UserCreate userCreate = null;
        if(OauthValidation.isPasswordMatch(updateUserPassword.getNewPassword(),updateUserPassword.getConfirmPassword())){
            Optional<User> userBase = userRepository.findById(userId);
            if(userBase.isPresent() &&
                    encoder.matches(updateUserPassword.getOldPassword(),userBase.get().getPassword())){
                userBase.get().setPassword(
                        encoder.encode(updateUserPassword.getNewPassword())
                );
                User createdUser = userRepository.save(userBase.get());
                userCreate = buildUserCreateObject(createdUser);
            }else{
                System.out.println("Wrong Password");
            }
        }else {
            System.out.println("Password MissMatch");
        }
        return userCreate;
    }

    @Override
    public UserCreate getUser(long userId) {
        Optional<User> userBase = userRepository.findById(userId);
        if(userBase.isPresent()){
            System.out.println(userBase.get().getEmail());
            return buildUserCreateObject(userBase.get());
        }
        return null;
    }

    @Override
    public String deactivateUser(long userId) {
       int status = userRepository.disableActiveProfile(false,userId);
        return status>0? "Successfully Deleted":"Failed to Delete";
    }

    private UserCreate buildUserCreateObject(User createdUser){
        UserCreate userCreate;
        userCreate = UserCreate.builder()
                .id(createdUser.getId())
                .firstName(createdUser.getFirstName())
                .lastName(createdUser.getLastName())
                .userName(createdUser.getUserName())
                .emailId(createdUser.getEmail())
                .dob(createdUser.getDob())
                .gender(createdUser.getGender())
                .phoneNumber(createdUser.getPhoneNo())
                .profession(createdUser.getProfession())
                .build();
        return userCreate;
    }

    private User constructUser(User updateUser){
        return User.builder()
                .firstName(updateUser.getFirstName())
                .lastName(updateUser.getLastName())
                .userName(updateUser.getUserName())
                .email(updateUser.getEmail())
                .dob(updateUser.getDob())
                .gender(updateUser.getGender())
                .phoneNo(updateUser.getPhoneNo())
                .profession(updateUser.getProfession())
                .build();
    }
}
