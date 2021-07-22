package com.contactbook.controller;

import com.contactbook.model.AuthenticationModel;
import com.contactbook.model.JWTLoginSuccessResponse;
import com.contactbook.model.User;
import com.contactbook.security.JwtTokenProvider;
import com.contactbook.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody User user, BindingResult result){
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationModel authenticationModel, BindingResult result){
        String jwt = userService.tokenGenerator(authenticationModel);
        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }
}
