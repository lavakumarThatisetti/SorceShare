package com.sorceshare.userstore.rest.controller;

import com.sorceshare.userstore.model.*;
import com.sorceshare.userstore.payload.request.LoginRequest;
import com.sorceshare.userstore.payload.request.SignUpRequest;
import com.sorceshare.userstore.payload.response.JwtResponse;
import com.sorceshare.userstore.payload.response.MessageResponse;
import com.sorceshare.userstore.repository.RoleRepository;
import com.sorceshare.userstore.repository.UserRepository;
import com.sorceshare.userstore.rest.JsonMapper.JsonDomainMapper;
import com.sorceshare.userstore.security.jwt.JwtUtils;
import com.sorceshare.userstore.security.services.UserDetailsImpl;
import com.sorceshare.userstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/auth")
public class UserStoreController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;


    private UserService userDetailsService;

    @Autowired
    public UserStoreController(UserService userDetailsService){
        this.userDetailsService = userDetailsService;

    }


    @PostMapping(produces = "application/json",value = "/signIn")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping(produces = "application/json",value = "/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User newUser = JsonDomainMapper.constructSignupRequestToUserRequest(signUpRequest,encoder);

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);

        newUser.setRoles(roles);
        UserCreate createdUser = userDetailsService.createUser(newUser);

        return ResponseEntity.ok(createdUser);
    }

    @PutMapping(produces = "application/json", value = "/updateUser/{userId}")
    public ResponseEntity<UserCreate> updateUser(@NotBlank @PathVariable long userId,
                                                 @Valid @RequestBody User userBase, BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception(bindingResult.getFieldErrors().iterator().next().getDefaultMessage());
            }
        }catch (Exception e) {
            ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(userDetailsService.updateUser(userId, userBase));
    }

    @PutMapping(produces = "application/json", value = "/updatePassword/{userId}")
    public ResponseEntity<UserCreate> updatePassword(@NotBlank @PathVariable long userId,
                                                     @Valid @RequestBody UpdateUserPassword updateUserPassword, BindingResult bindingResult){
        try {
            if (bindingResult.hasErrors()) {
                throw new Exception(bindingResult.getFieldErrors().iterator().next().getDefaultMessage());
            }
        }catch (Exception e) {
            ResponseEntity.ok(e.getMessage());
        }
        return ResponseEntity.ok(userDetailsService.updateUserPassword(userId,updateUserPassword));
    }


    @GetMapping(produces = "application/json", value = "/getUser/{userId}")
    public ResponseEntity<UserCreate> getUser(@NotBlank @PathVariable long userId){

        return ResponseEntity.ok(userDetailsService.getUser(userId));
    }


    @PutMapping(produces = "application/json", value = "/deactivateUser/{userId}")
    public ResponseEntity<String> deactiveUser(@NotBlank @PathVariable long userId){

        return ResponseEntity.ok(userDetailsService.deactivateUser(userId));
    }
}
